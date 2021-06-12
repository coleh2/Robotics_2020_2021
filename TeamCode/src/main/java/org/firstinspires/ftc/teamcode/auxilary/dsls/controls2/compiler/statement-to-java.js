var nonce = 0;

module.exports = function(statement) {
    if(!statement) {
        console.warn("WARNING: Falsy value fed to statementToJava");
        return "";
    }
    
    if(statement.type == "ifStatement") return ifStatementToJava(statement);
    else if(statement.type == "doesStatement") return doesStatementToJava(statement);
    else console.warn("WARNING: Unknown statement type " +statement)
}

function ifStatementToJava(ifStatement) {
    return "" +
`if(${comparisonToJava(ifStatement.condition)}) {
    ${doesStatementToJava(ifStatement.statement)}
} else {
    ${ifStatement.otherwise ? doesStatementToJava(ifStatement.otherwise) : "doNothing()"}
}`
}

function comparisonToJava(comparison) {
    switch(comparison.comparer.operator) {
        case "<":
            return `${getVariableIdAsScalar(comparison.left)}`
            + "<"
            + `${getVariableIdAsScalar(comparison.right)}`;
        case ">":
            return `${getVariableIdAsScalar(comparison.left)}`
            + ">"
            + `${getVariableIdAsScalar(comparison.right)}`;
        case "==":
            return `${getVariableIdAsScalar(comparison.left)}`
            + "=="
            + `${getVariableIdAsScalar(comparison.right)}`;
        case "~=":
            return `Math.floor(${getVariableIdAsScalar(comparison.left)})`
            + "=="
            + `Math.floor(${getVariableIdAsScalar(comparison.right)})`;
        case "~#<":
            return `Math.abs(${getVariableIdAsScalar(comparison.left)} - ${getVariableIdAsScalar(comparison.right)})`
            + "<="
            + `${getVariableIdAsScalar(comparison.comparer.threshold)}`;
    }
}

function doesStatementToJava(doesStatement) {
    var src = "";
    
    var scale = doesStatement.properties.scale;
    
    if(doesStatement.subject.value != 1 && doesStatement.subject.value != "god") {
        src += `if(${getVariableIdAsScalar(doesStatement.subject)} != 0) {\n`;
    }
    var block = "";
    if(doesStatement.properties.calculation) {
        block += resolveCalculatedStatement(doesStatement);
    } else {
        if(doesStatement.indirectObject.type == "vector") {
            block += doesStatement.indirectObject.values.map((x,i)=>`${getVariableId(doesStatement.directObject)}[${i}] = ${getVariableId(x)}${scale?" * " + getVariableIdAsScalar(scale) : ""};`).join("\n");
        } else {
            block += `${getVariableId(doesStatement.directObject)}[0] = `
            + `${getVariableId(doesStatement.indirectObject) + (doesStatement.indirectObject.type == "number" ? "" : "[0]")}${scale?" * " + getVariableIdAsScalar(scale) : ""};`
        }
    }
    
    
    if(doesStatement.subject.value != 1 && doesStatement.subject.value != "god") {
        src += indent(block);
        src += "\n}";
    } else {
        src += block;
    }
    
    return src;
}

function getCalculatedLength(doesStatement) {
    var length = doesStatement.indirectObject.values.length;
    
    if(doesStatement.properties.calculation == "omni") length == 4;
    
    return length;
}

function getScale(statement) {
    if(!statement.properties.scale) return 1;
    else return getVariableId(statement.properties.scale);
}

function getCalculationName(statement) {
    if(!statement.properties.calculation) return "";
    else return statement.properties.calculation.value;
}

function getVariableId(value) {
    if(value.type == "vector") return vectorToVariableId(value);
    if(value.type == "number") return value.value + "f";
    else return value.value;
}

function getVariableIdAsScalar(value) {
    if(value.type == "vector") return `arrayAverage(${getVariableId(value)})`;
    else if(value.typeType == "vector") return getVariableId(value) + "[0][0]";
    else if(value.type == "number") return getVariableId(value);
    else return getVariableId(value) + "[0]";
}

function vectorToVariableId(vector) {
    return `new float[][] {${vector.values.map(x=>getVariableId(x)).join(",")}}`;
}

function resolveCalculatedStatement(doesStatement) {
    var src = "";
    var scale = doesStatement.properties.scale;
    if(doesStatement.properties.calculation.value == "omni") {
        var tempVarname = generateTempVarname();
        src += `float[] ${tempVarname} = PaulMath.omniCalc(${doesStatement.indirectObject.values.map(x=>getVariableIdAsScalar(x) + (scale?" * " + getVariableIdAsScalar(scale) : ""))});\n`;
        src += `for(int i = 0; i < ${tempVarname}.length; i++) ${getVariableId(doesStatement.directObject)}[i][0] = ${tempVarname}[i];`
    } else {
        throw "Unknown calculation"
    }
    return src;
}

function generateTempVarname() {
    var id = nonce;
    nonce++;
    
    //ensure that it starts with lowercase
    var r = ((id % 26) + 10).toString(36);
    id = Math.floor(id / 26);
    
    while(id != 0) {
        r += base64Digit(id % 64);
        id = Math.floor(id / 64);
    }
    
    return "temp_" + r;
}

function indent(str, w) {
    w = w || 1;
    return str.split("\n").map(x=>("    ").repeat(w) + x).join("\n");
}
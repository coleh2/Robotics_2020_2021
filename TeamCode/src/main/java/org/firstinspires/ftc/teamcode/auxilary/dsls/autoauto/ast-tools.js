var nonce = "";
module.exports = function astToString(ast) {
    function process(child) {
        return indent(astToString(child));
    }

    switch(ast.type) {
        case "FrontMatter":
            return "";
        case "Program":
            nonce = genNonce();
            return `HashMap<String, Statepath> paths${nonce} = new HashMap<String, Statepath>();
            ${ast.statepaths.map(x=>process(x)).join("\n")}\n
            AutoautoProgram program${nonce} = new AutoautoProgram(paths${nonce}, ${process(ast.statepaths[0].label.variable)});
            runtime = new AutoautoRuntime(program${nonce}, driver, limbs, sense, imu);
            `;
        case "LabeledStatepath":
            return `paths${nonce}.put(${process(ast.label.variable)}, ${process(ast.statepath)});`;
        case "Statepath": 
            return `new Statepath(new State[] { ${ast.states.map(x=>process(x)).join(",\n")}\n}, "IF YOU SEE THIS ON THE PHONE, SOMETHING HAS GONE VERY WRONG AND YOU WILL NOT GO TO SPACE TODAY")`;
        case "State":
            return `new State(new Statement[] { ${ast.statement.map(x=> process(x) ).join(",\n")}\n})`;
        case "NextStatement":
            return `new NextStatement()`;
        case "FunctionCallStatement":
            return `new FunctionCallStatement(${process(ast.call)})`;
        case "AfterStatement":
            return `new AfterStatement(${process(ast.unitValue)}, ${process(ast.statement)})`;
        case "FunctionCall":
            return `new FunctionCall(${process(ast.func)}, ${process(ast.args)})`;
        case "Identifier":
            return JSON.stringify(ast.value);
        case "ArgumentList":
            return `new Value[] { ${ast.args.map(x=>process(x)).join(",\n")}\n}`;
        case "OperatorExpression":
            return `new ArithmeticValue(${process(ast.left)}, ${JSON.stringify(ast.operator)}, ${process(ast.right)})`;
        case "NumericValue":
            return `new NumericValue((float)${ast.v})`;
        case "StringLiteral":
            return `new StringLiteral(${JSON.stringify( '"' + ast.str + '"' )})`;
        case "ArrayLiteral":
            return `new ArrayLiteral(${process(ast.elems)})`
        case "UnitValue":
            return `new UnitValue((long)${ast.value.v}, ${process(ast.unit)})`
        case "GotoStatement":
            return `new GotoStatement(${process(ast.path)})`
        case "LetStatement":
            return `new LetStatement(${process(ast.variable)}, ${process(ast.value)})`;
        case "IfStatement":
            return `new IfStatement(${process(ast.conditional)}, ${process(ast.statement)})`;
        case "ComparisonOperator":
            return `new BooleanOperator(${process(ast.left)}, ${process(ast.right)}, ${JSON.stringify(ast.operator)})`;
        case "VariableReference":
            return `new VariableReference(${process(ast.variable)})`;
        default: 
        console.error("Unknown type " + ast.type);
        console.error(ast);
        
    }
}

function genNonce() {
    var r = "";
    for(var i = 0; i < 4; i++) r +=Math.random().toString(16).substring(2);
    return r;
}

function indent(str) {
    var lines = (str + "").split("\n");
    if(lines.length == 1) return str;

    for(var i = 0; i < lines.length; i++) {
        lines[i] = "    " + lines[i];
    }
    return (lines[0] == "" ? "" : "\n") + //add starting blank line ONLY if it doesn't have one already
        lines.join("\n") +
        (lines[lines.length - 1] == "" ? "" : "\n"); //add ending blank line ONLY if it doesn't have one already
}
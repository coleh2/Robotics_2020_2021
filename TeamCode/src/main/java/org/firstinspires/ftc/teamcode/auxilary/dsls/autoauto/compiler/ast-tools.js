var generalNumberToIncrementWhenSomethingNeedsToBeChanged = 0;
var stringDefinitions = {};
var locationSetters = [];
var depthMappedDefinitions = [];
var variables = [];

function initFileState() {
    generalNumberToIncrementWhenSomethingNeedsToBeChanged = 0;
    stringDefinitions = {};
    locationSetters = [];
    depthMappedDefinitions = [];
    variables = [];
}

var COLUMN_THRESHOLD = 120;
var JAVA_TYPE_REGEX = /^\w+(<(\w+)( *,(\w+))+>)?(\[\])?/;

module.exports = function astToString(ast, programNonce, statepath, stateNumber, depth) {
    if (programNonce === undefined) programNonce = genNonce();
    if(depth === undefined) depth = 0;

    var nonce = genNonce();

    function process(child, newStateNumber) {
        var r = astToString(child,
            programNonce,
            child.label ? child.label.value : statepath,
            newStateNumber || stateNumber,
            depth + 1
        );
        
        r.definitions = (dedent(r.definitions));
        return r;
    }

    var result = undefined;

    switch (ast.type) {
        case "FrontMatter":
            result = "";
            break;
        case "Program":
            var childDefs = ast.statepaths.map(x => process(x));
            var strings = `final String ${Object.entries(stringDefinitions).map(x=>`${x[1]}=${JSON.stringify(x[0])}`).join(",")};`;
            
            var programName = genNonce();
            
            var typedDefinitions = depthMappedDefinitions
                .sort((a,b) => b.depth - a.depth || a.definition - b.definition) //sort by depth; break ties by alphabetical order
                .map(x => ({
                    definition: x.definition.trim(),
                    type: JAVA_TYPE_REGEX.exec(x.definition.trim())[0] //discern and record the type
                }))
                .map((x,i,a) => { //if the type is the same as the previous, join the declarations with commas instead of semicolons
                    if(a[i-1] && a[i-1].type == x.type) x.definition = x.definition.replace(x.type, ""); 
                    if(a[i+1] && a[i+1].type == x.type) x.definition = x.definition.replace(/;$/, ",");
                    return x;
                })
                .map(x=>x.definition) //remove type data
                .join(""); //join into one string
                
            var creation = `
            ${typedDefinitions}
            HashMap<String, Statepath> ${nonce} = new HashMap<String, Statepath>();
            ${childDefs.map((x, i) => `${nonce}.put(${stringDefinitions[ast.statepaths[i].label.value]}, ${x.varname});`).join("\n")}
            AutoautoProgram ${programName} = new AutoautoProgram(${nonce}, ${stringDefinitions[ast.statepaths[0].label.value]});
            ${locationSetters.join("")}
            autoautoVariableNames = new VariableReference[] { ${variables.join(",")} };`;
            
            var runtimeSetup = `runtime = new AutoautoRuntime(${programName}, driver, limbs, sense, imu);`;
            
            result = strings + "\n";
            var creationStatements = creation.split(",");
            var line = "";
            for(var i = 0; i < creationStatements.length; i++) {
                creationStatements[i] = creationStatements[i].split(";").map(x=>x.trim()).join(";");
                
                if(creationStatements[i] != "") line += creationStatements[i];
                if(i + 1 < creationStatements.length) line += ",";
                
                if(line.length >= COLUMN_THRESHOLD) {
                    result += line + "\n";
                    line = "";
                }
            }
            result += line;
            
            result += "\n\n" + runtimeSetup;
            
            result += "\n\n";
            var jsonProgram = JSON.stringify(ast);
            
            for(var i = 0; i < jsonProgram.length; i += 32768) {
                result += "programJson.append(" + JSON.stringify(jsonProgram.substring(i, i + 1024)) + ");\n";
            }
            
            initFileState();
            break;
        case "LabeledStatepath":
            var childDefs = ast.statepath.states.map((x,i) => process(x, i));

            var label = process(ast.label);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `Statepath ${nonce} = new Statepath(new State[] { ${childDefs.map(x => x.varname).join(",")} }, ${label.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "Statepath":
            //covered by LabeledStatepath. Shouldn't be reached, ever.s
            throw "ILLEGAL STATE BAD TIME";
            break;
        case "State":
            var childDefs = ast.statement.map(x => process(x));
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `State ${nonce} = new State(new Statement[] { ${childDefs.map(x => x.varname).join(",")} });`
            });
            
            result = {
                varname: nonce
            };
            break;
        case "NextStatement":
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `NextStatement ${nonce} = new NextStatement();`
            });
        
            result = {
                varname: nonce
            }
            break;
        case "FunctionCallStatement":
            var call = process(ast.call);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `FunctionCallStatement ${nonce} = new FunctionCallStatement(${call.varname});`
            });
            
            result = {
                varname: nonce
            }
            break;
        case "AfterStatement":
            var unitValue = process(ast.unitValue);
            var statement = process(ast.statement);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `AfterStatement ${nonce} = new AfterStatement(${unitValue.varname}, ${statement.varname});`
            });

            result = {
                varname: nonce
            }
            break;
        case "FunctionCall":
            var func = process(ast.func);
            var args = process(ast.args);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `FunctionCall ${nonce} = new FunctionCall(${func.varname}, ${args.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "Identifier":
            //if this string isn't in the string definitions, then define it!
            if(!stringDefinitions[ast.value]) {
                stringDefinitions[ast.value] = nonce;
                result = {
                    varname: nonce,
                    noLocation: true,
                };
            } else {
                //if it HAS been defined, just reuse the same constant
                result = {
                    varname: stringDefinitions[ast.value],
                    noLocation: true,
                };
            }
            break;
        case "ArgumentList":
            var childDefs = ast.args.map(x => process(x));
            depthMappedDefinitions.push({
                depth: depth,
                definition: `AutoautoValue[] ${nonce} = new AutoautoValue[] {${childDefs.map(x => x.varname).join(", ")}};`
            });
            
            result = {
                varname: nonce,
                noLocation: true
            };
            break;
        case "OperatorExpression":
            var left = process(ast.left);

            var operatorName;
            if(stringDefinitions[ast.operator]) {
                operatorName = stringDefinitions[ast.operator]
            } else {
                operatorName = stringDefinitions[ast.operator] = genNonce();
            }
            

            var right = process(ast.right);

            depthMappedDefinitions.push({
                depth: depth,
                definition: `ArithmeticValue ${nonce} = new ArithmeticValue(${left.varname}, ${operatorName}, ${right.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "NumericValue":
            depthMappedDefinitions.push({
                depth: depth,
                definition: `NumericValue ${nonce} = new NumericValue((float)${ast.v});`
            });
            result = {
                varname: nonce
            };
            break;
        case "StringLiteral":
            var strNonce;
            //if the string literal wasn't defined in the string records, define it
            if(!stringDefinitions[ast.str]) {
                strNonce = stringDefinitions[ast.str] = genNonce();
            } else {
                //if it HAS been defined, just reuse the same constant
                strNonce = stringDefinitions[ast.str]
            }
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `AutoautoString ${nonce} = new AutoautoString(${strNonce});`
            });
            
            result = {
                varname: nonce
            }
            break;
        case "ArrayLiteral":
            var args = process(ast.elems);

            depthMappedDefinitions.push({
                depth: depth,
                definition: `${args.definitions}\nAutoautoArray ${nonce} = new AutoautoArray(${args.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "UnitValue":
            var unit = process(ast.unit);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `${unit.definitions}\nUnitValue ${nonce} = new UnitValue((long)${ast.value.v}, ${unit.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "GotoStatement":
            var path = process(ast.path);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `${path.definitions}\nGotoStatement ${nonce} = new GotoStatement(${path.varname});`
            });

            result = {
                varname: nonce
            }
            break;
        case "LetStatement":
            var variable = process(ast.variable);
            var value = process(ast.value);

            variables.push(variable.varname);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `LetStatement ${nonce} = new LetStatement(${variable.varname}, ${value.varname});`
            });

            result = {
                varname: nonce
            };
            break;
        case "IfStatement":
            var conditional = process(ast.conditional);
            var statement = process(ast.statement);

            depthMappedDefinitions.push({
                depth: depth,
                definition: `IfStatement ${nonce} = new IfStatement(${conditional.varname}, ${statement.varname});`
            });

            result = {
                varname: nonce
            }
            break;
        case "ComparisonOperator":
            var left = process(ast.left);
            var right = process(ast.right);
            
            var operatorName;
            if(stringDefinitions[ast.operator]) {
                operatorName = stringDefinitions[ast.operator]
            } else {
                operatorName = stringDefinitions[ast.operator] = genNonce();
            }
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `BooleanOperator ${nonce} = new BooleanOperator(${left.varname}, ${right.varname}, ${operatorName});`
            });

            result = {
                varname: nonce
            }
            break;
        case "VariableReference":
            var variable = process(ast.variable);
            
            depthMappedDefinitions.push({
                depth: depth,
                definition: `VariableReference ${nonce} = new VariableReference(${variable.varname});`
            });

            result = {
                varname: nonce,
            };
            break;
        default:
            console.error("Unknown type " + ast.type);
            console.error(ast);
    }

    if(!ast.location) {
        console.error("Type "+  ast.type + " doesn't have location");
        return result;
    }
    
    //if the statepath hasn't been added to the strings, add it too!
    var statepathNonce = genNonce();
    if(statepath && !stringDefinitions[statepath]) {
        stringDefinitions[statepath] = statepathNonce;
    } else {
        statepathNonce = stringDefinitions[statepath]
    }
    
    if (typeof result == "object" && result.definitions != "" && !result.noLocation) {
        locationSetters.push(`${nonce}.setLocation(L(` + 
            (statepathNonce || null) + "," + JSON.stringify(stateNumber || 0) + "," + 
            JSON.stringify(ast.location.start.line) + "," + JSON.stringify(ast.location.start.column) + "));");
    }
    return result;
}

function genNonce() {
    generalNumberToIncrementWhenSomethingNeedsToBeChanged++;
    
    var id = generalNumberToIncrementWhenSomethingNeedsToBeChanged;
    
    //ensure that it starts with lowercase
    var r = ((id % 26) + 10).toString(36);
    id = Math.floor(id / 26);
    
    while(id != 0) {
        r += base64Digit(id % 64);
        id = Math.floor(id / 64);
    }
    
    //screen for banned words. If one's found, regenerate
    var javaKeywords = ["abstract","assert","boolean","break","byte","case","catch","char","class","continue","default","do","double","else","enum","extends","final","finally","float","for","if","implements","import","instanceof","int","interface","long","native","new","null","package","private","protected","public","return","short","static","strictfp","super","switch","synchronized","this","throw","throws","transient","try","void","volatile","while","const","goto"];
    var usedVariables = ["driver", "limbs", "sense", "imu"]
    if(javaKeywords.includes(r) || usedVariables.includes(r)) return genNonce();

    return r;
}

function base64Digit(i) {
    if(i == 0) return "$";
    i -= 1;
    
    if(i < 10) return "" + i;
    i -= 10;
    
    if(i == 0) return "_";
    i -= 1;
    
    if(i < 26) return (i + 10).toString(36).toUpperCase();
    i -= 26;
    
    if(i < 26) return (i + 10).toString(36).toLowerCase();
    
    //if it's greater or equal to 64, it'll get here
    throw i + ">= 64";
}

function indent(str) {
    var lines = (str + "").split("\n");
    if (lines.length == 1) return str;

    for (var i = 0; i < lines.length; i++) {
        lines[i] = "    " + lines[i];
    }
    return (lines[0] == "" ? "" : "\n") + //add starting blank line ONLY if it doesn't have one already
        lines.join("\n") +
        (lines[lines.length - 1] == "" ? "" : "\n"); //add ending blank line ONLY if it doesn't have one already
}

function dedent(str) {
    return (str || "").split("\n").map(x => x.trim()).join("\n");
}
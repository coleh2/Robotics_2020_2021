autoautoFile = 
  _ f:frontMatter? _ s:labeledStatepath+ _ {
  	return {
      type: "Program",
      frontMatter: f,
      statepaths: s
    }
  }

frontMatter =
 DOLLAR_SIGN _ heads:(frontMatterKeyValue COMMA _)* tail:frontMatterKeyValue _ DOLLAR_SIGN {
   return {
     type: "FrontMatter",
     values: heads.map(x=>x[0]).concat([tail])
   }
 }

frontMatterKeyValue =
 k:IDENTIFIER COLON _ v:value _ {
   return {
     type: "FrontMatterKeyValue",
     key: k,
     value: v
   }
 }

commentOpportunity = _

labeledStatepath =
 _ l:STATEPATH_LABEL_ID COLON _ s:statepath _  {
   return { type: "LabeledStatepath", statepath: s, label: l }
 }


statepath =
  _ heads:(state SEMICOLON)* tail:state SEMICOLON? _ {
  	return {
      type: "Statepath",
      states: heads.map(x=>x[0]).concat([tail])
    }
  }

state =
  _ heads:(statement COMMA)* tail:statement COMMA? _ {
    return {
      type: "State",
      statement: heads.map(x=>x[0]).concat([tail])
    }
  }

statement =
  _  s:(afterStatement/gotoStatement/ifStatement/letStatement/nextStatement/skipStatement/functionCallStatement)  _
  
  { return s; }

afterStatement =
 AFTER _ u:unitValue _ s:statement { return { type: "AfterStatement", unitValue: u, statement: s } }

functionCallStatement =
 f:functionCall { return { type: "FunctionCallStatement", call: f } }

gotoStatement =
 GOTO _ p:IDENTIFIER { return { type: "GotoStatement", path: p } } 

ifStatement =
 IF _ OPEN_PAREN t:value CLOSE_PAREN s:statement { return { type: "IfStatement", conditional: t, statement: s } }

letStatement =
 LET _ v:variableReference _ EQUALS _ val:value { return { type: "LetStatement", variable: v, value: val } }  

nextStatement =
 NEXT { return { type: "NextStatement" }   } 

skipStatement =
 SKIP s:NUMERIC_VALUE  { return { type: "SkipStatement", skip: s }   } 

value =
 _  b:boolean _ { return b }

valueInParens =
 _ OPEN_PAREN v:value CLOSE_PAREN _ { return v; }

modulo = 
 l:baseExpression o:MODULUS r:baseExpression { 
    return { type: "OperatorExpression", operator: o, left: l, right: r }  
}
/ b:baseExpression  { return b; }

exponent = 
 l:modulo o:EXPONENTIATE r:modulo { 
    return { type: "OperatorExpression", operator: o, left: l, right: r }  
} 
/ m:modulo { return m; }

product = 
 l:exponent tail:(o:(MULTIPLY / DIVIDE) r:product { return [o, r]; })+ {
  var r = { type: "OperatorExpression", left: l };
  var tar = r;
  for(var i = 0; i < tail.length - 1; i++) {
    tar.operator = tail[i][0];
    var newTar = { type: "OperatorExpression", left: tail[i][1] };
    tar.right = newTar;
    tar = newTar;
  }
  tar.operator = tail[tail.length - 1][0];
  tar.right = tail[tail.length - 1][1];
  return r;
} 
/ p:exponent { return p; }
 
sum = l:product tail:(o:(PLUS / MINUS) r:product { return [o, r]; })+ {
  var r = { type: "OperatorExpression", left: l };
  var tar = r;
  for(var i = 0; i < tail.length - 1; i++) {
    tar.operator = tail[i][0];
    var newTar = { type: "OperatorExpression", left: tail[i][1] };
    tar.right = newTar;
    tar = newTar;
  }
  tar.operator = tail[tail.length - 1][0];
  tar.right = tail[tail.length - 1][1];
  return r;
} 
/ p:product { return p; }
arithmeticValue =
 s:sum {
   return s;
 }

baseExpression =
 _  x:(arrayLiteral / stringLiteral / unitValue / NUMERIC_VALUE / functionCall / variableReference / valueInParens) _ {
   return x;
 }

variableReference =
 i:IDENTIFIER { return { type: "VariableReference", variable: i }; }

arrayLiteral =
 OPEN_SQUARE_BRACKET a:argumentList? CLOSE_SQUARE_BRACKET { 
 return {
   type: "ArrayLiteral",
   elems: a || {type:"ArgumentList",args:[]}
 }
}

boolean =
 l:arithmeticValue o:comparisonOperator r:arithmeticValue { return { type: "ComparisonOperator", left: l, operator: o, right: r }; } 
 / TRUE { return { type: "BooleanLiteral", value: true }; }
 / FALSE { return { type: "BooleanLiteral", value: false }; }
 / r:arithmeticValue { return r; }

comparisonOperator =
 o:(COMPARE_LTE / COMPARE_LT / COMPARE_EQ / COMPARE_NEQ / COMPARE_GTE / COMPARE_GT) { return o; }

functionCall =
 f:IDENTIFIER _ OPEN_PAREN a:argumentList? CLOSE_PAREN { 
 return {
   type: "FunctionCall",
   func: f, args: a || {type:"ArgumentList",args:[]}
 }
}

stringLiteral =
 DOUBLE_QUOTE q:NON_QUOTE_CHARACTER* DOUBLE_QUOTE { return { type: "StringLiteral", str: q.join("") } }

unitValue =
 u:NUMERIC_VALUE_WITH_UNIT { return u; }

argumentList =
 heads:(value COMMA)* tail:value { 
 return { 
   type: "ArgumentList", 
   len: heads.length + 1,
   args: heads.map(x=> x[0]).concat([tail])
 }
}  

_ = [ \t\n\r]*
  

NON_QUOTE_CHARACTER = [^"]
DOUBLE_QUOTE = '"'

HASHTAG = "#"

  EOL = '\r'? '\n'
  COLON =
    ":"
SEMICOLON =
    ";"
COMMA =
    ","
AFTER =
    "after"
GOTO =
    "goto"
IF =
    "if"
OPEN_PAREN =
    "("
CLOSE_PAREN =
    ")"
LET =
    "let"
EQUALS =
    "="
NEXT =
    "next"
SKIP =
    "skip"
COMPARE_LTE =
    "<="
COMPARE_EQ =
    "=="
COMPARE_NEQ =
    "!="
COMPARE_GTE =
    ">="
COMPARE_GT =
    ">"
COMPARE_LT =
    "<"
TRUE =
    "true"
FALSE =
    "false"
PLUS =
    "+"
MINUS =
    "-"
DIVIDE =
    "/"
MULTIPLY =
    "*"
MODULUS =
    "%"
EXPONENTIATE =
    "^"
DOLLAR_SIGN =
    "$"
OPEN_SQUARE_BRACKET =
    "["
CLOSE_SQUARE_BRACKET =
    "]"
IDENTIFIER =
    l:LETTER+ 
    &{
    var name = l.join("");
    var reserved = ["if", "goto", "skip", "let", "next"];
    if(reserved.indexOf(name) == -1) return true;
    else return false;
    }
    { return { type: "Identifier", value: l.join("") } }
DIGIT = [0-9]
LETTER = [A-Za-z]
STATEPATH_LABEL_ID = HASHTAG i:IDENTIFIER { return i; }
NUMERIC_VALUE = m:MINUS? v:(
    h:DIGIT* '.' t:DIGIT+ { return h.join("") + "." + t.join("") }
    / 
    d:DIGIT+ { return d.join(""); }) 'f'?
{
    return { type: "NumericValue", v: parseFloat((m||"") + v ) }
}  
NUMERIC_VALUE_WITH_UNIT = n:NUMERIC_VALUE u:IDENTIFIER {
    return { type: "UnitValue", value: n, unit: u }
}

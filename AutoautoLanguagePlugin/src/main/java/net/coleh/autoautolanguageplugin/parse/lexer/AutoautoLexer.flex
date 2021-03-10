package net.coleh.autoautolanguageplugin.parse.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import com.intellij.psi.TokenType;
import net.coleh.autoautolanguageplugin.parse.AutoautoTypes;

%%

%{
  public AutoautoLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class AutoautoLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s
NUMERIC_VALUE=-?[0-9]*\.?[0-9]+
WHITESPACE_RANGE=\s+

%state IN_COMMENT
%state IN_STRING
%%
<YYINITIAL> {
  {WHITE_SPACE}              { return TokenType.WHITE_SPACE; }
  {WHITESPACE_RANGE}         { return AutoautoTypes.WHITESPACE_RANGE; }

  "#"                  { return AutoautoTypes.HASHTAG; }
  ":"                    { return AutoautoTypes.COLON; }
  ";"                { return AutoautoTypes.SEMICOLON; }
  ","                    { return AutoautoTypes.COMMA; }
  "after "                    { return AutoautoTypes.AFTER; }
  "goto "                     { return AutoautoTypes.GOTO; }
  "if "                       { return AutoautoTypes.IF; }
  "if"                       { return AutoautoTypes.IF; }
  "("               { return AutoautoTypes.OPEN_PAREN; }
  ")"              { return AutoautoTypes.CLOSE_PAREN; }
  "let "                      { return AutoautoTypes.LET; }
  "="                   { return AutoautoTypes.EQUALS; }
  "next"                     { return AutoautoTypes.NEXT; }
  "skip"                     { return AutoautoTypes.SKIP; }
  {NUMERIC_VALUE}            { return AutoautoTypes.NUMERIC_VALUE; }
  "\""                    { yybegin(IN_STRING); return AutoautoTypes.QUOTE; }
  "<"                     { return AutoautoTypes.COMPARE_LT; }
  "<="                    { return AutoautoTypes.COMPARE_LTE; }
  "=="                    { return AutoautoTypes.COMPARE_EQ; }
  "!="                    { return AutoautoTypes.COMPARE_NEQ; }
  ">="                    { return AutoautoTypes.COMPARE_GTE; }
  ">"                     { return AutoautoTypes.COMPARE_LT; }
  \w+                     { return AutoautoTypes.IDENTIFIER; }
  "/*"                       { yybegin(IN_COMMENT); return AutoautoTypes.COMMENT_BEGIN;}
}

<IN_STRING> {
    "\""                       { yybegin(YYINITIAL); return AutoautoTypes.QUOTE; }
     .+      { return AutoautoTypes.NON_QUOTE_CHARACTER; }
}

<IN_COMMENT> {
    "*/"              { yybegin(YYINITIAL);return AutoautoTypes.COMMENT_END;  }
     .+              { return AutoautoTypes.COMMENT_TEXT; }
}

[^] { return TokenType.BAD_CHARACTER; }
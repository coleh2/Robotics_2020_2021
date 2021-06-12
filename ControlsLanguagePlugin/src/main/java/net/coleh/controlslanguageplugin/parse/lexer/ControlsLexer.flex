package net.coleh.controlslanguageplugin.parse.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import com.intellij.psi.TokenType;
import net.coleh.controlslanguageplugin.parse.ControlsTypes;

%%

%{
  public ControlsLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class ControlsLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s
NUMERIC_VALUE=-?[0-9]*\.?[0-9]+
NUMERIC_VALUE_WITH_UNIT=-?[0-9]*\.?[0-9]+\w+
WHITESPACE_RANGE=\s+
IDENTIFIER=\w+

%state IN_COMMENT
%state IN_STRING
%state IN_LINE_COMMENT
%%
<YYINITIAL> {
  {WHITE_SPACE}              { return TokenType.WHITE_SPACE; }
  {WHITESPACE_RANGE}         { return ControlsTypes.WHITESPACE_RANGE; }

  "."        { return ControlsTypes.PERIOD; }
  "does"        { return ControlsTypes.DOES; }
  "sets"        { return ControlsTypes.SETS; }
  "SETS"        { return ControlsTypes.SETS; }
  "to"        { return ControlsTypes.TO; }
  "TO"        { return ControlsTypes.TO; }
  "are"        { return ControlsTypes.ARE; }
  "ARE"        { return ControlsTypes.ARE; }
  "named"        { return ControlsTypes.NAMED; }
  "NAMED"        { return ControlsTypes.NAMED; }
  "with"        { return ControlsTypes.WITH; }
  "WITH"        { return ControlsTypes.WITH; }
  "priority"        { return ControlsTypes.PRIORITY; }
  "PRIORITY"        { return ControlsTypes.PRIORITY; }
  ","        { return ControlsTypes.COMMA; }
  "highest"        { return ControlsTypes.HIGHEST; }
  "HIGHEST"        { return ControlsTypes.HIGHEST; }
  "higher"        { return ControlsTypes.HIGHER; }
  "HIGHER"        { return ControlsTypes.HIGHER; }
  "high"        { return ControlsTypes.HIGH; }
  "HIGH"        { return ControlsTypes.HIGH; }
  "normal"        { return ControlsTypes.NORMAL; }
  "NORMAL"        { return ControlsTypes.NORMAL; }
  "low"        { return ControlsTypes.LOW; }
  "LOW"        { return ControlsTypes.LOW; }
  "lower"        { return ControlsTypes.LOWER; }
  "LOWER"        { return ControlsTypes.LOWER; }
  "lowest"        { return ControlsTypes.LOWEST; }
  "LOWEST"        { return ControlsTypes.LOWEST; }
  "a default of"        { return ControlsTypes.A_DEFAULT_OF; }
  "A DEFAULT OF"        { return ControlsTypes.A_DEFAULT_OF; }
  "a scale of"        { return ControlsTypes.A_SCALE_OF; }
  "A SCALE OF"        { return ControlsTypes.A_SCALE_OF; }
  "-"        { return ControlsTypes.MINUS; }
  ';'        { return ControlsTypes.SEMICOLON; }
  "\n"        { return ControlsTypes.NEWLINE; }
  "and"        { return ControlsTypes.AND; }
  "AND"        { return ControlsTypes.AND; }
  "the"        { return ControlsTypes.THE; }
  "THE"        { return ControlsTypes.THE; }
  "a"        { return ControlsTypes.A; }
  "A"        { return ControlsTypes.A; }
  "first off"        { return ControlsTypes.FIRST_OFF; }
  "FIRST OFF"        { return ControlsTypes.FIRST_OFF; }
  "here's what will happen"        { return ControlsTypes.HERES_WHAT_WILL_HAPPEN; }
  "HERE'S WHAT WILL HAPPEN"        { return ControlsTypes.HERES_WHAT_WILL_HAPPEN; }
  ":"        { return ControlsTypes.COLON; }
  "if"        { return ControlsTypes.IF; }
  "IF"        { return ControlsTypes.IF; }
  "when"        { return ControlsTypes.WHEN; }
  "WHEN"        { return ControlsTypes.WHEN; }
  "is"        { return ControlsTypes.IS; }
  "IS"        { return ControlsTypes.IS; }
  "really"        { return ControlsTypes.REALLY; }
  "REALLY"        { return ControlsTypes.REALLY; }
  "less than"        { return ControlsTypes.LESS_THAN; }
  "LESS THAN"        { return ControlsTypes.LESS_THAN; }
  "more than"        { return ControlsTypes.MORE_THAN; }
  "MORE THAN"        { return ControlsTypes.MORE_THAN; }
  "the same as"        { return ControlsTypes.THE_SAME_AS; }
  "THE SAME AS"        { return ControlsTypes.THE_SAME_AS; }
  "actually"        { return ControlsTypes.ACTUALLY; }
  "ACTUALLY"        { return ControlsTypes.ACTUALLY; }
  "or"        { return ControlsTypes.OR; }
  "OR"        { return ControlsTypes.OR; }
  "else"        { return ControlsTypes.ELSE; }
  "ELSE"        { return ControlsTypes.ELSE; }
  "otherwise"        { return ControlsTypes.OTHERWISE; }
  "OTHERWISE"        { return ControlsTypes.OTHERWISE; }
  "but"        { return ControlsTypes.BUT; }
  "BUT"        { return ControlsTypes.BUT; }
  "truly"        { return ControlsTypes.TRULY; }
  "TRULY"        { return ControlsTypes.TRULY; }
  "then"        { return ControlsTypes.THEN; }
  "THEN"        { return ControlsTypes.THEN; }
  "when not"        { return ControlsTypes.WHEN_NOT; }
  "WHEN NOT"        { return ControlsTypes.WHEN_NOT; }
  "if not"        { return ControlsTypes.IF_NOT; }
  "IF NOT"        { return ControlsTypes.IF_NOT; }
  "i promise"        { return ControlsTypes.I_PROMISE; }
  "I PROMISE"        { return ControlsTypes.I_PROMISE; }
  "i think"        { return ControlsTypes.I_THINK; }
  "I THINK"        { return ControlsTypes.I_THINK; }
  "that"        { return ControlsTypes.THAT; }
  "THAT"        { return ControlsTypes.THAT; }
  "hates"        { return ControlsTypes.HATES; }
  "HATES"        { return ControlsTypes.HATES; }
  "an"        { return ControlsTypes.AN; }
  "AN"        { return ControlsTypes.AN; }
  "mimics"        { return ControlsTypes.MIMICS; }
  "MIMICS"        { return ControlsTypes.MIMICS; }
  "controls"        { return ControlsTypes.CONTROLS; }
  "CONTROLS"        { return ControlsTypes.CONTROLS; }
  "control"        { return ControlsTypes.CONTROL; }
  "CONTROL"        { return ControlsTypes.CONTROL; }
  "calculation"        { return ControlsTypes.CALCULATION; }
  "CALCULATION"        { return ControlsTypes.CALCULATION; }
  "set"        { return ControlsTypes.SET; }
  "SET"        { return ControlsTypes.SET; }
  "it"        { return ControlsTypes.IT; }
  "IT"        { return ControlsTypes.IT; }
  "might"        { return ControlsTypes.MIGHT; }
  "MIGHT"        { return ControlsTypes.MIGHT; }
  "hate"        { return ControlsTypes.HATE; }
  "HATE"        { return ControlsTypes.HATE; }
  "roughly"        { return ControlsTypes.ROUGHLY; }
  "ROUGHLY"        { return ControlsTypes.ROUGHLY; }
  "equal to"        { return ControlsTypes.EQUAL_TO; }
  "EQUAL TO"        { return ControlsTypes.EQUAL_TO; }
  "within"        { return ControlsTypes.WITHIN; }
  "WITHIN"        { return ControlsTypes.WITHIN; }
  "of"        { return ControlsTypes.OF; }
  "OF"        { return ControlsTypes.OF; }
  "%"        { return ControlsTypes.PERCENT_SIGN; }

  {IDENTIFIER}               { return ControlsTypes.IDENTIFIER; }
  {NUMERIC_VALUE}            { return ControlsTypes.NUMERIC_VALUE; }
  [^]                        { return ControlsTypes.UNKNOWN_CHARACTER; }
}

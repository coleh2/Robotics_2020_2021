// This is a generated file. Not intended for manual editing.
package net.coleh.controlslanguageplugin.parse;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.coleh.controlslanguageplugin.parse.impl.*;

public interface ControlsTypes {

  IElementType SOMETHING = new ControlsElementType("SOMETHING");

  IElementType A = new ControlsTokenType("A");
  IElementType ACTUALLY = new ControlsTokenType("ACTUALLY");
  IElementType AN = new ControlsTokenType("AN");
  IElementType AND = new ControlsTokenType("AND");
  IElementType ARE = new ControlsTokenType("ARE");
  IElementType A_DEFAULT_OF = new ControlsTokenType("A_DEFAULT_OF");
  IElementType A_SCALE_OF = new ControlsTokenType("A_SCALE_OF");
  IElementType BUT = new ControlsTokenType("BUT");
  IElementType CALCULATION = new ControlsTokenType("CALCULATION");
  IElementType COLON = new ControlsTokenType("COLON");
  IElementType COMMA = new ControlsTokenType("COMMA");
  IElementType CONTROL = new ControlsTokenType("CONTROL");
  IElementType CONTROLS = new ControlsTokenType("CONTROLS");
  IElementType DOES = new ControlsTokenType("DOES");
  IElementType ELSE = new ControlsTokenType("ELSE");
  IElementType EQUAL_TO = new ControlsTokenType("EQUAL_TO");
  IElementType FIRST_OFF = new ControlsTokenType("FIRST_OFF");
  IElementType HATE = new ControlsTokenType("HATE");
  IElementType HATES = new ControlsTokenType("HATES");
  IElementType HERES_WHAT_WILL_HAPPEN = new ControlsTokenType("HERES_WHAT_WILL_HAPPEN");
  IElementType HIGH = new ControlsTokenType("HIGH");
  IElementType HIGHER = new ControlsTokenType("HIGHER");
  IElementType HIGHEST = new ControlsTokenType("HIGHEST");
  IElementType IDENTIFIER = new ControlsTokenType("IDENTIFIER");
  IElementType IF = new ControlsTokenType("IF");
  IElementType IF_NOT = new ControlsTokenType("IF_NOT");
  IElementType IS = new ControlsTokenType("IS");
  IElementType IT = new ControlsTokenType("IT");
  IElementType I_PROMISE = new ControlsTokenType("I_PROMISE");
  IElementType I_THINK = new ControlsTokenType("I_THINK");
  IElementType LESS_THAN = new ControlsTokenType("LESS_THAN");
  IElementType LOW = new ControlsTokenType("LOW");
  IElementType LOWER = new ControlsTokenType("LOWER");
  IElementType LOWEST = new ControlsTokenType("LOWEST");
  IElementType MIGHT = new ControlsTokenType("MIGHT");
  IElementType MIMICS = new ControlsTokenType("MIMICS");
  IElementType MINUS = new ControlsTokenType("MINUS");
  IElementType MORE_THAN = new ControlsTokenType("MORE_THAN");
  IElementType NAMED = new ControlsTokenType("NAMED");
  IElementType NEWLINE = new ControlsTokenType("NEWLINE");
  IElementType NORMAL = new ControlsTokenType("NORMAL");
  IElementType NUMERIC_VALUE = new ControlsTokenType("NUMERIC_VALUE");
  IElementType OF = new ControlsTokenType("OF");
  IElementType OR = new ControlsTokenType("OR");
  IElementType OTHERWISE = new ControlsTokenType("OTHERWISE");
  IElementType PERCENT_SIGN = new ControlsTokenType("PERCENT_SIGN");
  IElementType PERIOD = new ControlsTokenType("PERIOD");
  IElementType PRIORITY = new ControlsTokenType("PRIORITY");
  IElementType REALLY = new ControlsTokenType("REALLY");
  IElementType ROUGHLY = new ControlsTokenType("ROUGHLY");
  IElementType SEMICOLON = new ControlsTokenType("SEMICOLON");
  IElementType SET = new ControlsTokenType("SET");
  IElementType SETS = new ControlsTokenType("SETS");
  IElementType THAT = new ControlsTokenType("THAT");
  IElementType THE = new ControlsTokenType("THE");
  IElementType THEN = new ControlsTokenType("THEN");
  IElementType THE_SAME_AS = new ControlsTokenType("THE_SAME_AS");
  IElementType TO = new ControlsTokenType("TO");
  IElementType TRULY = new ControlsTokenType("TRULY");
  IElementType UNKNOWN_CHARACTER = new ControlsTokenType("UNKNOWN_CHARACTER");
  IElementType WHEN = new ControlsTokenType("WHEN");
  IElementType WHEN_NOT = new ControlsTokenType("WHEN_NOT");
  IElementType WHITESPACE_RANGE = new ControlsTokenType("WHITESPACE_RANGE");
  IElementType WITH = new ControlsTokenType("WITH");
  IElementType WITHIN = new ControlsTokenType("WITHIN");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == SOMETHING) {
        return new ControlsSomethingImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

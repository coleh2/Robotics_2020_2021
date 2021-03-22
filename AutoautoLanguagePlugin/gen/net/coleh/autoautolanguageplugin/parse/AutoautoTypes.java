// This is a generated file. Not intended for manual editing.
package net.coleh.autoautolanguageplugin.parse;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.coleh.autoautolanguageplugin.parse.impl.*;

public interface AutoautoTypes {

  IElementType AFTER_STATEMENT = new AutoautoElementType("AFTER_STATEMENT");
  IElementType ARGUMENT_LIST = new AutoautoElementType("ARGUMENT_LIST");
  IElementType ARRAY_LITERAL = new AutoautoElementType("ARRAY_LITERAL");
  IElementType BLOCK_COMMENT = new AutoautoElementType("BLOCK_COMMENT");
  IElementType BOOLEAN = new AutoautoElementType("BOOLEAN");
  IElementType COMMENT = new AutoautoElementType("COMMENT");
  IElementType COMMENT_OPPORTUNITY = new AutoautoElementType("COMMENT_OPPORTUNITY");
  IElementType COMPARISON_OPERATOR = new AutoautoElementType("COMPARISON_OPERATOR");
  IElementType FUNCTION_CALL = new AutoautoElementType("FUNCTION_CALL");
  IElementType FUNCTION_CALL_STATEMENT = new AutoautoElementType("FUNCTION_CALL_STATEMENT");
  IElementType GOTO_STATEMENT = new AutoautoElementType("GOTO_STATEMENT");
  IElementType IF_STATEMENT = new AutoautoElementType("IF_STATEMENT");
  IElementType LABELED_STATEPATH = new AutoautoElementType("LABELED_STATEPATH");
  IElementType LET_STATEMENT = new AutoautoElementType("LET_STATEMENT");
  IElementType LINE_COMMENT = new AutoautoElementType("LINE_COMMENT");
  IElementType NEXT_STATEMENT = new AutoautoElementType("NEXT_STATEMENT");
  IElementType NON_BOOLEAN_VALUE = new AutoautoElementType("NON_BOOLEAN_VALUE");
  IElementType SKIP_STATEMENT = new AutoautoElementType("SKIP_STATEMENT");
  IElementType STATE = new AutoautoElementType("STATE");
  IElementType STATEMENT = new AutoautoElementType("STATEMENT");
  IElementType STATEPATH = new AutoautoElementType("STATEPATH");
  IElementType STRING_LITERAL = new AutoautoElementType("STRING_LITERAL");
  IElementType UNIT_VALUE = new AutoautoElementType("UNIT_VALUE");
  IElementType VALUE = new AutoautoElementType("VALUE");
  IElementType VARIABLE_REFERENCE = new AutoautoElementType("VARIABLE_REFERENCE");

  IElementType AFTER = new AutoautoTokenType("AFTER");
  IElementType CLOSE_PAREN = new AutoautoTokenType("CLOSE_PAREN");
  IElementType COLON = new AutoautoTokenType("COLON");
  IElementType COMMA = new AutoautoTokenType("COMMA");
  IElementType COMMENT_BEGIN = new AutoautoTokenType("COMMENT_BEGIN");
  IElementType COMMENT_END = new AutoautoTokenType("COMMENT_END");
  IElementType COMMENT_TEXT = new AutoautoTokenType("COMMENT_TEXT");
  IElementType COMPARE_EQ = new AutoautoTokenType("COMPARE_EQ");
  IElementType COMPARE_GT = new AutoautoTokenType("COMPARE_GT");
  IElementType COMPARE_GTE = new AutoautoTokenType("COMPARE_GTE");
  IElementType COMPARE_LT = new AutoautoTokenType("COMPARE_LT");
  IElementType COMPARE_LTE = new AutoautoTokenType("COMPARE_LTE");
  IElementType COMPARE_NEQ = new AutoautoTokenType("COMPARE_NEQ");
  IElementType EQUALS = new AutoautoTokenType("EQUALS");
  IElementType GOTO = new AutoautoTokenType("GOTO");
  IElementType IDENTIFIER = new AutoautoTokenType("IDENTIFIER");
  IElementType IF = new AutoautoTokenType("IF");
  IElementType LET = new AutoautoTokenType("LET");
  IElementType LINE_COMMENT_BEGIN = new AutoautoTokenType("LINE_COMMENT_BEGIN");
  IElementType LINE_COMMENT_END = new AutoautoTokenType("LINE_COMMENT_END");
  IElementType MINUS = new AutoautoTokenType("MINUS");
  IElementType NEXT = new AutoautoTokenType("NEXT");
  IElementType NON_QUOTE_CHARACTER = new AutoautoTokenType("NON_QUOTE_CHARACTER");
  IElementType NUMERIC_VALUE = new AutoautoTokenType("NUMERIC_VALUE");
  IElementType NUMERIC_VALUE_WITH_UNIT = new AutoautoTokenType("NUMERIC_VALUE_WITH_UNIT");
  IElementType OPEN_PAREN = new AutoautoTokenType("OPEN_PAREN");
  IElementType QUOTE = new AutoautoTokenType("QUOTE");
  IElementType SEMICOLON = new AutoautoTokenType("SEMICOLON");
  IElementType SKIP = new AutoautoTokenType("SKIP");
  IElementType STATEPATH_LABEL_ID = new AutoautoTokenType("STATEPATH_LABEL_ID");
  IElementType WHITESPACE_RANGE = new AutoautoTokenType("WHITESPACE_RANGE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == AFTER_STATEMENT) {
        return new AutoautoAfterStatementImpl(node);
      }
      else if (type == ARGUMENT_LIST) {
        return new AutoautoArgumentListImpl(node);
      }
      else if (type == ARRAY_LITERAL) {
        return new AutoautoArrayLiteralImpl(node);
      }
      else if (type == BLOCK_COMMENT) {
        return new AutoautoBlockCommentImpl(node);
      }
      else if (type == BOOLEAN) {
        return new AutoautoBooleanImpl(node);
      }
      else if (type == COMMENT) {
        return new AutoautoCommentImpl(node);
      }
      else if (type == COMMENT_OPPORTUNITY) {
        return new AutoautoCommentOpportunityImpl(node);
      }
      else if (type == COMPARISON_OPERATOR) {
        return new AutoautoComparisonOperatorImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new AutoautoFunctionCallImpl(node);
      }
      else if (type == FUNCTION_CALL_STATEMENT) {
        return new AutoautoFunctionCallStatementImpl(node);
      }
      else if (type == GOTO_STATEMENT) {
        return new AutoautoGotoStatementImpl(node);
      }
      else if (type == IF_STATEMENT) {
        return new AutoautoIfStatementImpl(node);
      }
      else if (type == LABELED_STATEPATH) {
        return new AutoautoLabeledStatepathImpl(node);
      }
      else if (type == LET_STATEMENT) {
        return new AutoautoLetStatementImpl(node);
      }
      else if (type == LINE_COMMENT) {
        return new AutoautoLineCommentImpl(node);
      }
      else if (type == NEXT_STATEMENT) {
        return new AutoautoNextStatementImpl(node);
      }
      else if (type == NON_BOOLEAN_VALUE) {
        return new AutoautoNonBooleanValueImpl(node);
      }
      else if (type == SKIP_STATEMENT) {
        return new AutoautoSkipStatementImpl(node);
      }
      else if (type == STATE) {
        return new AutoautoStateImpl(node);
      }
      else if (type == STATEMENT) {
        return new AutoautoStatementImpl(node);
      }
      else if (type == STATEPATH) {
        return new AutoautoStatepathImpl(node);
      }
      else if (type == STRING_LITERAL) {
        return new AutoautoStringLiteralImpl(node);
      }
      else if (type == UNIT_VALUE) {
        return new AutoautoUnitValueImpl(node);
      }
      else if (type == VALUE) {
        return new AutoautoValueImpl(node);
      }
      else if (type == VARIABLE_REFERENCE) {
        return new AutoautoVariableReferenceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

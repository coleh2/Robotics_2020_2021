// This is a generated file. Not intended for manual editing.
package net.coleh.autoautolanguageplugin.parse;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static net.coleh.autoautolanguageplugin.parse.AutoautoTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class AutoautoParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return autoautoFile(b, l + 1);
  }

  /* ********************************************************** */
  // AFTER unitValue statement
  public static boolean afterStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "afterStatement")) return false;
    if (!nextTokenIs(b, AFTER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AFTER);
    r = r && unitValue(b, l + 1);
    r = r && statement(b, l + 1);
    exit_section_(b, m, AFTER_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // (value COMMA)* value
  public static boolean argumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT_LIST, "<argument list>");
    r = argumentList_0(b, l + 1);
    r = r && value(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (value COMMA)*
  private static boolean argumentList_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!argumentList_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "argumentList_0", c)) break;
    }
    return true;
  }

  // value COMMA
  private static boolean argumentList_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentList_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = value(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // sum
  public static boolean arithmeticValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arithmeticValue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARITHMETIC_VALUE, "<arithmetic value>");
    r = sum(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OPEN_SQUARE_BRACKET [argumentList] CLOSE_SQUARE_BRACKET
  public static boolean arrayLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral")) return false;
    if (!nextTokenIs(b, OPEN_SQUARE_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPEN_SQUARE_BRACKET);
    r = r && arrayLiteral_1(b, l + 1);
    r = r && consumeToken(b, CLOSE_SQUARE_BRACKET);
    exit_section_(b, m, ARRAY_LITERAL, r);
    return r;
  }

  // [argumentList]
  private static boolean arrayLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "arrayLiteral_1")) return false;
    argumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // commentOpportunity* [frontMatter] commentOpportunity* labeledStatepath+ commentOpportunity*
  static boolean autoautoFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = autoautoFile_0(b, l + 1);
    r = r && autoautoFile_1(b, l + 1);
    r = r && autoautoFile_2(b, l + 1);
    r = r && autoautoFile_3(b, l + 1);
    r = r && autoautoFile_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // commentOpportunity*
  private static boolean autoautoFile_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoautoFile_0", c)) break;
    }
    return true;
  }

  // [frontMatter]
  private static boolean autoautoFile_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile_1")) return false;
    frontMatter(b, l + 1);
    return true;
  }

  // commentOpportunity*
  private static boolean autoautoFile_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoautoFile_2", c)) break;
    }
    return true;
  }

  // labeledStatepath+
  private static boolean autoautoFile_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = labeledStatepath(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!labeledStatepath(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoautoFile_3", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // commentOpportunity*
  private static boolean autoautoFile_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoautoFile_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "autoautoFile_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // commentOpportunity*  (arrayLiteral | functionCall | NUMERIC_VALUE | stringLiteral | unitValue | variableReference | valueInParens) commentOpportunity*
  public static boolean baseExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BASE_EXPRESSION, "<base expression>");
    r = baseExpression_0(b, l + 1);
    r = r && baseExpression_1(b, l + 1);
    r = r && baseExpression_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean baseExpression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseExpression_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "baseExpression_0", c)) break;
    }
    return true;
  }

  // arrayLiteral | functionCall | NUMERIC_VALUE | stringLiteral | unitValue | variableReference | valueInParens
  private static boolean baseExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseExpression_1")) return false;
    boolean r;
    r = arrayLiteral(b, l + 1);
    if (!r) r = functionCall(b, l + 1);
    if (!r) r = consumeToken(b, NUMERIC_VALUE);
    if (!r) r = stringLiteral(b, l + 1);
    if (!r) r = unitValue(b, l + 1);
    if (!r) r = variableReference(b, l + 1);
    if (!r) r = valueInParens(b, l + 1);
    return r;
  }

  // commentOpportunity*
  private static boolean baseExpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "baseExpression_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "baseExpression_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // COMMENT_BEGIN COMMENT_TEXT* COMMENT_END
  public static boolean blockComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blockComment")) return false;
    if (!nextTokenIs(b, COMMENT_BEGIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT_BEGIN);
    r = r && blockComment_1(b, l + 1);
    r = r && consumeToken(b, COMMENT_END);
    exit_section_(b, m, BLOCK_COMMENT, r);
    return r;
  }

  // COMMENT_TEXT*
  private static boolean blockComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blockComment_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT_TEXT)) break;
      if (!empty_element_parsed_guard_(b, "blockComment_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (arithmeticValue comparisonOperator arithmeticValue) | TRUE | FALSE
  public static boolean boolean_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_$")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BOOLEAN, "<boolean $>");
    r = boolean_0(b, l + 1);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // arithmeticValue comparisonOperator arithmeticValue
  private static boolean boolean_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = arithmeticValue(b, l + 1);
    r = r && comparisonOperator(b, l + 1);
    r = r && arithmeticValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // blockComment | lineComment
  public static boolean comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment")) return false;
    if (!nextTokenIs(b, "<comment>", COMMENT_BEGIN, LINE_COMMENT_BEGIN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMENT, "<comment>");
    r = blockComment(b, l + 1);
    if (!r) r = lineComment(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comment [WHITESPACE_RANGE]
  public static boolean commentOpportunity(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commentOpportunity")) return false;
    if (!nextTokenIs(b, "<comment opportunity>", COMMENT_BEGIN, LINE_COMMENT_BEGIN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMENT_OPPORTUNITY, "<comment opportunity>");
    r = comment(b, l + 1);
    r = r && commentOpportunity_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [WHITESPACE_RANGE]
  private static boolean commentOpportunity_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commentOpportunity_1")) return false;
    consumeToken(b, WHITESPACE_RANGE);
    return true;
  }

  /* ********************************************************** */
  // COMPARE_LT | COMPARE_LTE | COMPARE_EQ | COMPARE_NEQ | COMPARE_GTE | COMPARE_GT
  public static boolean comparisonOperator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comparisonOperator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPARISON_OPERATOR, "<comparison operator>");
    r = consumeToken(b, COMPARE_LT);
    if (!r) r = consumeToken(b, COMPARE_LTE);
    if (!r) r = consumeToken(b, COMPARE_EQ);
    if (!r) r = consumeToken(b, COMPARE_NEQ);
    if (!r) r = consumeToken(b, COMPARE_GTE);
    if (!r) r = consumeToken(b, COMPARE_GT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // modulo [EXPONENTIATE modulo]
  public static boolean exponent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPONENT, "<exponent>");
    r = modulo(b, l + 1);
    r = r && exponent_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [EXPONENTIATE modulo]
  private static boolean exponent_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent_1")) return false;
    exponent_1_0(b, l + 1);
    return true;
  }

  // EXPONENTIATE modulo
  private static boolean exponent_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exponent_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXPONENTIATE);
    r = r && modulo(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DOLLAR_SIGN commentOpportunity* (frontMatterKeyValue COMMA)* frontMatterKeyValue commentOpportunity* DOLLAR_SIGN
  public static boolean frontMatter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatter")) return false;
    if (!nextTokenIs(b, DOLLAR_SIGN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOLLAR_SIGN);
    r = r && frontMatter_1(b, l + 1);
    r = r && frontMatter_2(b, l + 1);
    r = r && frontMatterKeyValue(b, l + 1);
    r = r && frontMatter_4(b, l + 1);
    r = r && consumeToken(b, DOLLAR_SIGN);
    exit_section_(b, m, FRONT_MATTER, r);
    return r;
  }

  // commentOpportunity*
  private static boolean frontMatter_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatter_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "frontMatter_1", c)) break;
    }
    return true;
  }

  // (frontMatterKeyValue COMMA)*
  private static boolean frontMatter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatter_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!frontMatter_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "frontMatter_2", c)) break;
    }
    return true;
  }

  // frontMatterKeyValue COMMA
  private static boolean frontMatter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = frontMatterKeyValue(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // commentOpportunity*
  private static boolean frontMatter_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatter_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "frontMatter_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER COLON commentOpportunity* value commentOpportunity*
  public static boolean frontMatterKeyValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatterKeyValue")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, COLON);
    r = r && frontMatterKeyValue_2(b, l + 1);
    r = r && value(b, l + 1);
    r = r && frontMatterKeyValue_4(b, l + 1);
    exit_section_(b, m, FRONT_MATTER_KEY_VALUE, r);
    return r;
  }

  // commentOpportunity*
  private static boolean frontMatterKeyValue_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatterKeyValue_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "frontMatterKeyValue_2", c)) break;
    }
    return true;
  }

  // commentOpportunity*
  private static boolean frontMatterKeyValue_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "frontMatterKeyValue_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "frontMatterKeyValue_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER commentOpportunity* OPEN_PAREN [argumentList] CLOSE_PAREN
  public static boolean functionCall(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCall")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && functionCall_1(b, l + 1);
    r = r && consumeToken(b, OPEN_PAREN);
    r = r && functionCall_3(b, l + 1);
    r = r && consumeToken(b, CLOSE_PAREN);
    exit_section_(b, m, FUNCTION_CALL, r);
    return r;
  }

  // commentOpportunity*
  private static boolean functionCall_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCall_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "functionCall_1", c)) break;
    }
    return true;
  }

  // [argumentList]
  private static boolean functionCall_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCall_3")) return false;
    argumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // functionCall
  public static boolean functionCallStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "functionCallStatement")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = functionCall(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // GOTO IDENTIFIER
  public static boolean gotoStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gotoStatement")) return false;
    if (!nextTokenIs(b, GOTO)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, GOTO, IDENTIFIER);
    exit_section_(b, m, GOTO_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // IF OPEN_PAREN boolean CLOSE_PAREN statement
  public static boolean ifStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifStatement")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IF, OPEN_PAREN);
    r = r && boolean_$(b, l + 1);
    r = r && consumeToken(b, CLOSE_PAREN);
    r = r && statement(b, l + 1);
    exit_section_(b, m, IF_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // commentOpportunity* STATEPATH_LABEL_ID COLON commentOpportunity* statepath commentOpportunity*
  public static boolean labeledStatepath(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatepath")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LABELED_STATEPATH, "<labeled statepath>");
    r = labeledStatepath_0(b, l + 1);
    r = r && consumeTokens(b, 0, STATEPATH_LABEL_ID, COLON);
    r = r && labeledStatepath_3(b, l + 1);
    r = r && statepath(b, l + 1);
    r = r && labeledStatepath_5(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean labeledStatepath_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatepath_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "labeledStatepath_0", c)) break;
    }
    return true;
  }

  // commentOpportunity*
  private static boolean labeledStatepath_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatepath_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "labeledStatepath_3", c)) break;
    }
    return true;
  }

  // commentOpportunity*
  private static boolean labeledStatepath_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labeledStatepath_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "labeledStatepath_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LET variableReference commentOpportunity* EQUALS commentOpportunity* value
  public static boolean letStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "letStatement")) return false;
    if (!nextTokenIs(b, LET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LET);
    r = r && variableReference(b, l + 1);
    r = r && letStatement_2(b, l + 1);
    r = r && consumeToken(b, EQUALS);
    r = r && letStatement_4(b, l + 1);
    r = r && value(b, l + 1);
    exit_section_(b, m, LET_STATEMENT, r);
    return r;
  }

  // commentOpportunity*
  private static boolean letStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "letStatement_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "letStatement_2", c)) break;
    }
    return true;
  }

  // commentOpportunity*
  private static boolean letStatement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "letStatement_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "letStatement_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // LINE_COMMENT_BEGIN COMMENT_TEXT* LINE_COMMENT_END
  public static boolean lineComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lineComment")) return false;
    if (!nextTokenIs(b, LINE_COMMENT_BEGIN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LINE_COMMENT_BEGIN);
    r = r && lineComment_1(b, l + 1);
    r = r && consumeToken(b, LINE_COMMENT_END);
    exit_section_(b, m, LINE_COMMENT, r);
    return r;
  }

  // COMMENT_TEXT*
  private static boolean lineComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lineComment_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT_TEXT)) break;
      if (!empty_element_parsed_guard_(b, "lineComment_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // baseExpression [MODULUS baseExpression]
  public static boolean modulo(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "modulo")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MODULO, "<modulo>");
    r = baseExpression(b, l + 1);
    r = r && modulo_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [MODULUS baseExpression]
  private static boolean modulo_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "modulo_1")) return false;
    modulo_1_0(b, l + 1);
    return true;
  }

  // MODULUS baseExpression
  private static boolean modulo_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "modulo_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MODULUS);
    r = r && baseExpression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NEXT
  public static boolean nextStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nextStatement")) return false;
    if (!nextTokenIs(b, NEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEXT);
    exit_section_(b, m, NEXT_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // exponent [((MULTIPLY | DIVIDE) exponent)+]
  public static boolean product(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "product")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRODUCT, "<product>");
    r = exponent(b, l + 1);
    r = r && product_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [((MULTIPLY | DIVIDE) exponent)+]
  private static boolean product_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "product_1")) return false;
    product_1_0(b, l + 1);
    return true;
  }

  // ((MULTIPLY | DIVIDE) exponent)+
  private static boolean product_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "product_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = product_1_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!product_1_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "product_1_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (MULTIPLY | DIVIDE) exponent
  private static boolean product_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "product_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = product_1_0_0_0(b, l + 1);
    r = r && exponent(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MULTIPLY | DIVIDE
  private static boolean product_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "product_1_0_0_0")) return false;
    boolean r;
    r = consumeToken(b, MULTIPLY);
    if (!r) r = consumeToken(b, DIVIDE);
    return r;
  }

  /* ********************************************************** */
  // SKIP NUMERIC_VALUE
  public static boolean skipStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "skipStatement")) return false;
    if (!nextTokenIs(b, SKIP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SKIP, NUMERIC_VALUE);
    exit_section_(b, m, SKIP_STATEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // commentOpportunity* (statement COMMA)* statement [COMMA] commentOpportunity*
  public static boolean state(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATE, "<state>");
    r = state_0(b, l + 1);
    r = r && state_1(b, l + 1);
    r = r && statement(b, l + 1);
    r = r && state_3(b, l + 1);
    r = r && state_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean state_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "state_0", c)) break;
    }
    return true;
  }

  // (statement COMMA)*
  private static boolean state_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!state_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "state_1", c)) break;
    }
    return true;
  }

  // statement COMMA
  private static boolean state_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA]
  private static boolean state_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  // commentOpportunity*
  private static boolean state_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "state_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "state_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // commentOpportunity*  (afterStatement|functionCallStatement|gotoStatement|ifStatement|letStatement|nextStatement|skipStatement)  commentOpportunity*
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = statement_0(b, l + 1);
    r = r && statement_1(b, l + 1);
    r = r && statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean statement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statement_0", c)) break;
    }
    return true;
  }

  // afterStatement|functionCallStatement|gotoStatement|ifStatement|letStatement|nextStatement|skipStatement
  private static boolean statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1")) return false;
    boolean r;
    r = afterStatement(b, l + 1);
    if (!r) r = functionCallStatement(b, l + 1);
    if (!r) r = gotoStatement(b, l + 1);
    if (!r) r = ifStatement(b, l + 1);
    if (!r) r = letStatement(b, l + 1);
    if (!r) r = nextStatement(b, l + 1);
    if (!r) r = skipStatement(b, l + 1);
    return r;
  }

  // commentOpportunity*
  private static boolean statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statement_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // commentOpportunity* (state SEMICOLON)* state [SEMICOLON] commentOpportunity*
  public static boolean statepath(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEPATH, "<statepath>");
    r = statepath_0(b, l + 1);
    r = r && statepath_1(b, l + 1);
    r = r && state(b, l + 1);
    r = r && statepath_3(b, l + 1);
    r = r && statepath_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean statepath_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statepath_0", c)) break;
    }
    return true;
  }

  // (state SEMICOLON)*
  private static boolean statepath_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statepath_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statepath_1", c)) break;
    }
    return true;
  }

  // state SEMICOLON
  private static boolean statepath_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = state(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // [SEMICOLON]
  private static boolean statepath_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath_3")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  // commentOpportunity*
  private static boolean statepath_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statepath_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statepath_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // QUOTE NON_QUOTE_CHARACTER* QUOTE
  public static boolean stringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stringLiteral")) return false;
    if (!nextTokenIs(b, QUOTE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUOTE);
    r = r && stringLiteral_1(b, l + 1);
    r = r && consumeToken(b, QUOTE);
    exit_section_(b, m, STRING_LITERAL, r);
    return r;
  }

  // NON_QUOTE_CHARACTER*
  private static boolean stringLiteral_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stringLiteral_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, NON_QUOTE_CHARACTER)) break;
      if (!empty_element_parsed_guard_(b, "stringLiteral_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // product [((PLUS | MINUS) product)+]
  public static boolean sum(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sum")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SUM, "<sum>");
    r = product(b, l + 1);
    r = r && sum_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [((PLUS | MINUS) product)+]
  private static boolean sum_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sum_1")) return false;
    sum_1_0(b, l + 1);
    return true;
  }

  // ((PLUS | MINUS) product)+
  private static boolean sum_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sum_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sum_1_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!sum_1_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "sum_1_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (PLUS | MINUS) product
  private static boolean sum_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sum_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sum_1_0_0_0(b, l + 1);
    r = r && product(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PLUS | MINUS
  private static boolean sum_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sum_1_0_0_0")) return false;
    boolean r;
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // NUMERIC_VALUE_WITH_UNIT
  public static boolean unitValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unitValue")) return false;
    if (!nextTokenIs(b, NUMERIC_VALUE_WITH_UNIT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMERIC_VALUE_WITH_UNIT);
    exit_section_(b, m, UNIT_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // commentOpportunity*  (arithmeticValue | boolean) commentOpportunity*
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = value_0(b, l + 1);
    r = r && value_1(b, l + 1);
    r = r && value_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "value_0", c)) break;
    }
    return true;
  }

  // arithmeticValue | boolean
  private static boolean value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_1")) return false;
    boolean r;
    r = arithmeticValue(b, l + 1);
    if (!r) r = boolean_$(b, l + 1);
    return r;
  }

  // commentOpportunity*
  private static boolean value_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "value_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // commentOpportunity* OPEN_PAREN value CLOSE_PAREN commentOpportunity*
  public static boolean valueInParens(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueInParens")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE_IN_PARENS, "<value in parens>");
    r = valueInParens_0(b, l + 1);
    r = r && consumeToken(b, OPEN_PAREN);
    r = r && value(b, l + 1);
    r = r && consumeToken(b, CLOSE_PAREN);
    r = r && valueInParens_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // commentOpportunity*
  private static boolean valueInParens_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueInParens_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "valueInParens_0", c)) break;
    }
    return true;
  }

  // commentOpportunity*
  private static boolean valueInParens_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "valueInParens_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commentOpportunity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "valueInParens_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean variableReference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variableReference")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, VARIABLE_REFERENCE, r);
    return r;
  }

}

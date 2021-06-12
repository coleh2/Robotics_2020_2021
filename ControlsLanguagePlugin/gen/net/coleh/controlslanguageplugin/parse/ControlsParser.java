// This is a generated file. Not intended for manual editing.
package net.coleh.controlslanguageplugin.parse;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static net.coleh.controlslanguageplugin.parse.ControlsTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class ControlsParser implements PsiParser, LightPsiParser {

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
    return controls(b, l + 1);
  }

  /* ********************************************************** */
  // something*
  static boolean controls(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "controls")) return false;
    while (true) {
      int c = current_position_(b);
      if (!something(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "controls", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // WHITESPACE_RANGE | PERIOD | DOES | SETS | TO | ARE | NAMED | WITH | PRIORITY | COMMA | HIGHEST | HIGHER | HIGH | NORMAL | LOW | LOWER | LOWEST |
  //     A_DEFAULT_OF | A_SCALE_OF | MINUS | SEMICOLON | NEWLINE | AND | THE | A | FIRST_OFF | HERES_WHAT_WILL_HAPPEN | COLON | IF | WHEN | IS | REALLY | LESS_THAN |
  //     MORE_THAN | THE_SAME_AS | ACTUALLY | OR | ELSE | OTHERWISE | BUT | TRULY | THEN | WHEN_NOT | IF_NOT | I_PROMISE | I_THINK | THAT | HATES | AN | MIMICS |
  //     CONTROLS | CONTROL | CALCULATION | SET | IT | MIGHT | HATE | ROUGHLY | EQUAL_TO | WITHIN | OF | PERCENT_SIGN | IDENTIFIER | NUMERIC_VALUE | UNKNOWN_CHARACTER
  public static boolean something(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "something")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SOMETHING, "<something>");
    r = consumeToken(b, WHITESPACE_RANGE);
    if (!r) r = consumeToken(b, PERIOD);
    if (!r) r = consumeToken(b, DOES);
    if (!r) r = consumeToken(b, SETS);
    if (!r) r = consumeToken(b, TO);
    if (!r) r = consumeToken(b, ARE);
    if (!r) r = consumeToken(b, NAMED);
    if (!r) r = consumeToken(b, WITH);
    if (!r) r = consumeToken(b, PRIORITY);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, HIGHEST);
    if (!r) r = consumeToken(b, HIGHER);
    if (!r) r = consumeToken(b, HIGH);
    if (!r) r = consumeToken(b, NORMAL);
    if (!r) r = consumeToken(b, LOW);
    if (!r) r = consumeToken(b, LOWER);
    if (!r) r = consumeToken(b, LOWEST);
    if (!r) r = consumeToken(b, A_DEFAULT_OF);
    if (!r) r = consumeToken(b, A_SCALE_OF);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, NEWLINE);
    if (!r) r = consumeToken(b, AND);
    if (!r) r = consumeToken(b, THE);
    if (!r) r = consumeToken(b, A);
    if (!r) r = consumeToken(b, FIRST_OFF);
    if (!r) r = consumeToken(b, HERES_WHAT_WILL_HAPPEN);
    if (!r) r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, WHEN);
    if (!r) r = consumeToken(b, IS);
    if (!r) r = consumeToken(b, REALLY);
    if (!r) r = consumeToken(b, LESS_THAN);
    if (!r) r = consumeToken(b, MORE_THAN);
    if (!r) r = consumeToken(b, THE_SAME_AS);
    if (!r) r = consumeToken(b, ACTUALLY);
    if (!r) r = consumeToken(b, OR);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, OTHERWISE);
    if (!r) r = consumeToken(b, BUT);
    if (!r) r = consumeToken(b, TRULY);
    if (!r) r = consumeToken(b, THEN);
    if (!r) r = consumeToken(b, WHEN_NOT);
    if (!r) r = consumeToken(b, IF_NOT);
    if (!r) r = consumeToken(b, I_PROMISE);
    if (!r) r = consumeToken(b, I_THINK);
    if (!r) r = consumeToken(b, THAT);
    if (!r) r = consumeToken(b, HATES);
    if (!r) r = consumeToken(b, AN);
    if (!r) r = consumeToken(b, MIMICS);
    if (!r) r = consumeToken(b, CONTROLS);
    if (!r) r = consumeToken(b, CONTROL);
    if (!r) r = consumeToken(b, CALCULATION);
    if (!r) r = consumeToken(b, SET);
    if (!r) r = consumeToken(b, IT);
    if (!r) r = consumeToken(b, MIGHT);
    if (!r) r = consumeToken(b, HATE);
    if (!r) r = consumeToken(b, ROUGHLY);
    if (!r) r = consumeToken(b, EQUAL_TO);
    if (!r) r = consumeToken(b, WITHIN);
    if (!r) r = consumeToken(b, OF);
    if (!r) r = consumeToken(b, PERCENT_SIGN);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, NUMERIC_VALUE);
    if (!r) r = consumeToken(b, UNKNOWN_CHARACTER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}

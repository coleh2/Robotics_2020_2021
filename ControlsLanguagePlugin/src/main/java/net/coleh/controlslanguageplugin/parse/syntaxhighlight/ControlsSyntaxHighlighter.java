package net.coleh.controlslanguageplugin.parse.syntaxhighlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.coleh.controlslanguageplugin.parse.ControlsTypes;
import net.coleh.controlslanguageplugin.parse.lexer.ControlsLexerAdapter;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import org.jetbrains.annotations.NotNull;

public class ControlsSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey OPERATOR =
            createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey LABEL =
            createTextAttributesKey("AUTOAUTO_LABEL", DefaultLanguageHighlighterColors.CLASS_NAME);
    public static final TextAttributesKey VERB =
            createTextAttributesKey("AUTOAUTO_VERB", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey VALUE =
            createTextAttributesKey("AUTOAUTO_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMA =
            createTextAttributesKey("AUTOAUTO_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENS =
            createTextAttributesKey("AUTOAUTO_PARENS", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey SEMICOLON =
            createTextAttributesKey("AUTOAUTO_SEMICOLON", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("AUTOAUTO_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey STATEMENT =
            createTextAttributesKey("AUTOAUTO_STATEMENT", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey NUMERIC_VALUE =
            createTextAttributesKey("AUTOAUTO_NUMERIC_VALUE", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("AUTOAUTO_STRING", DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    public static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[] { OPERATOR };
    public static final TextAttributesKey[] PARENS_KEYS = new TextAttributesKey[] { PARENS };
    public static final TextAttributesKey[] LABEL_KEYS = new TextAttributesKey[] { LABEL };
    public static final TextAttributesKey[] VERB_KEYS = new TextAttributesKey[] { VERB };
    public static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[] { VALUE };
    public static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[] { COMMA };
    public static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[] { SEMICOLON };
    public static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] { COMMENT };
    public static final TextAttributesKey[] STATEMENT_KEYS = new TextAttributesKey[] { STATEMENT };
    public static final TextAttributesKey[] NUMERIC_VALUE_KEYS = new TextAttributesKey[] { NUMERIC_VALUE };
    public static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[] { STRING };
    public static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[] { BAD_CHARACTER };

    public static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[] { };

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ControlsLexerAdapter();
    }


    @NotNull
    @Override
    public final TextAttributesKey[] getTokenHighlights(IElementType tokenType) {

        if (tokenType.equals(ControlsTypes.WITH) ||
                tokenType.equals(ControlsTypes.REALLY) ||
                tokenType.equals(ControlsTypes.TRULY) ||
                tokenType.equals(ControlsTypes.ACTUALLY) ||
                tokenType.equals(ControlsTypes.I_PROMISE) ||
                tokenType.equals(ControlsTypes.I_THINK)) {
            return LABEL_KEYS;
        } else if (tokenType.equals(ControlsTypes.COMMA)) {
            return COMMA_KEYS;
        } else if (tokenType.equals(ControlsTypes.SEMICOLON) || tokenType.equals(ControlsTypes.PERIOD) || tokenType.equals(ControlsTypes.COLON)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHARACTER_KEYS;
        } else if(tokenType.equals(ControlsTypes.IS) ||
                tokenType.equals(ControlsTypes.DOES) ||
                tokenType.equals(ControlsTypes.HATES) ||
                tokenType.equals(ControlsTypes.HATE) ||
                tokenType.equals(ControlsTypes.MIMICS) ||
                tokenType.equals(ControlsTypes.SETS) ||
                tokenType.equals(ControlsTypes.SET) ||
                tokenType.equals(ControlsTypes.ARE) ||
                tokenType.equals(ControlsTypes.CONTROLS) ||
                tokenType.equals(ControlsTypes.CONTROL)) {
            return VERB_KEYS;
        } else if(tokenType.equals(ControlsTypes.OTHERWISE) ||
                tokenType.equals(ControlsTypes.WHEN_NOT) ||
                tokenType.equals(ControlsTypes.WHEN) ||
                tokenType.equals(ControlsTypes.IF) ||
                tokenType.equals(ControlsTypes.IF_NOT) ||
                tokenType.equals(ControlsTypes.ELSE) ||
                tokenType.equals(ControlsTypes.THEN)) {
            return STATEMENT_KEYS;
        } else if(tokenType.equals(ControlsTypes.AN) || tokenType.equals(ControlsTypes.THE) || tokenType.equals(ControlsTypes.A)) {
            return COMMENT_KEYS;
        } else if(tokenType.equals(ControlsTypes.NUMERIC_VALUE)) {
            return NUMERIC_VALUE_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}

package net.coleh.autoautolanguageplugin.parse.syntaxhighlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.coleh.autoautolanguageplugin.parse.AutoautoTypes;
import net.coleh.autoautolanguageplugin.parse.lexer.AutoautoLexerAdapter;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import org.jetbrains.annotations.NotNull;

public class AutoautoSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey OPERATOR =
            createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey LABEL =
            createTextAttributesKey("AUTOAUTO_LABEL", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey FUNCTION =
            createTextAttributesKey("AUTOAUTO_FUNCTION", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE =
            createTextAttributesKey("AUTOAUTO_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMA =
            createTextAttributesKey("AUTOAUTO_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey SEMICOLON =
            createTextAttributesKey("AUTOAUTO_SEMICOLON", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("AUTOAUTO_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    public static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[] { OPERATOR };
    public static final TextAttributesKey[] LABEL_KEYS = new TextAttributesKey[] { LABEL };
    public static final TextAttributesKey[] FUNCTION_KEYS = new TextAttributesKey[] { FUNCTION };
    public static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[] { VALUE };
    public static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[] { COMMA };
    public static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[] { SEMICOLON };
    public static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[] { COMMENT };
    public static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[] { BAD_CHARACTER };

    public static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[] { };

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new AutoautoLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(AutoautoTypes.VALUE)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(AutoautoTypes.STATEPATH_LABEL)) {
            return LABEL_KEYS;
        } else if (tokenType.equals(AutoautoTypes.COMMA)) {
            return COMMA_KEYS;
        } else if (tokenType.equals(AutoautoTypes.SEMICOLON)) {
            return SEMICOLON_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHARACTER_KEYS;
        } else if(tokenType.equals(AutoautoTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if(tokenType.equals(AutoautoTypes.FUNCTION_CALL)) {
            return FUNCTION_KEYS;
        } else if(tokenType.equals(AutoautoTypes.HASHTAG)) {
            return OPERATOR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}

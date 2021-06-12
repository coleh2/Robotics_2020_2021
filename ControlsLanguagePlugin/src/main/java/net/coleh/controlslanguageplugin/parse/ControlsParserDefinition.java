package net.coleh.controlslanguageplugin.parse;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.coleh.controlslanguageplugin.ControlsLanguage;
import net.coleh.controlslanguageplugin.parse.lexer.ControlsLexerAdapter;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.naming.ldap.Control;

public class ControlsParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(ControlsLanguage.INSTANCE);

    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE, ControlsTypes.WHITESPACE_RANGE);
    public static final TokenSet COMMENTS = TokenSet.create(ControlsTypes.HERES_WHAT_WILL_HAPPEN, ControlsTypes.FIRST_OFF);
    public static final TokenSet STRINGS = TokenSet.create();

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new ControlsLexerAdapter();
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    @Override
    public PsiParser createParser(final Project project) {
        return new ControlsParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new ControlsFile(viewProvider);
    }

    @Override
    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return ControlsTypes.Factory.createElement(node);
    }

    @Test
    public void test() {
    }


}

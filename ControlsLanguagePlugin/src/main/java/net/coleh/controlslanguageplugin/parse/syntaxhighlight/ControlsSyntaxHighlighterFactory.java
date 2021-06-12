package net.coleh.controlslanguageplugin.parse.syntaxhighlight;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class ControlsSyntaxHighlighterFactory  extends SyntaxHighlighterFactory {
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
        return new ControlsSyntaxHighlighter();
    }
}

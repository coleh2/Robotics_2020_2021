package net.coleh.controlslanguageplugin.parse.lexer;

import com.intellij.lexer.FlexAdapter;
import net.coleh.controlslanguageplugin.parse.lexer.ControlsLexer;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class ControlsLexerAdapter extends FlexAdapter {
    public ControlsLexerAdapter() {
        super(new ControlsLexer(null));
    }
}

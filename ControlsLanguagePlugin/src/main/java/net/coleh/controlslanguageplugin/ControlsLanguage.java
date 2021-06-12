package net.coleh.controlslanguageplugin;

import com.intellij.lang.Language;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ControlsLanguage extends Language {
    public static final ControlsLanguage INSTANCE = new ControlsLanguage();

    private ControlsLanguage() {
        super("Controls");
    }
}


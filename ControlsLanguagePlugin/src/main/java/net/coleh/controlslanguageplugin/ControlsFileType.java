package net.coleh.controlslanguageplugin;

import com.intellij.openapi.fileTypes.LanguageFileType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class ControlsFileType extends LanguageFileType {
    public static final ControlsFileType INSTANCE = new ControlsFileType();

    private ControlsFileType() {
        super(ControlsLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return "Controls File";
    }

    @NotNull
    public String getDescription() {
        return "Controls robot controls file";
    }

    @NotNull
    public String getDefaultExtension() {
        return "controls";
    }

    @Nullable
    public Icon getIcon() {
        return ControlsIcons.FILE;
    }

}
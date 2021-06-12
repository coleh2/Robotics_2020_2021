package net.coleh.controlslanguageplugin.parse;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import net.coleh.controlslanguageplugin.ControlsFileType;
import net.coleh.controlslanguageplugin.ControlsLanguage;

import org.jetbrains.annotations.NotNull;

public class ControlsFile extends PsiFileBase {
    public ControlsFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ControlsLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ControlsFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Controls File";
    }
}

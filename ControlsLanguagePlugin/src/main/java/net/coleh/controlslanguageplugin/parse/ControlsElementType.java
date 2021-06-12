package net.coleh.controlslanguageplugin.parse;

import com.intellij.psi.tree.IElementType;

import net.coleh.controlslanguageplugin.ControlsLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ControlsElementType extends IElementType {
    public ControlsElementType(@NotNull @NonNls String debugName) {
        super(debugName, ControlsLanguage.INSTANCE);
    }
}

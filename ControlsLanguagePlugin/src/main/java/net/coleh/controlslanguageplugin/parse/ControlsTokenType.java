package net.coleh.controlslanguageplugin.parse;

import com.intellij.psi.tree.IElementType;

import net.coleh.controlslanguageplugin.ControlsLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ControlsTokenType extends IElementType {
    public ControlsTokenType(@NotNull @NonNls String debugName) {
        super(debugName, ControlsLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "ControlsTokenType." + super.toString();
    }
}

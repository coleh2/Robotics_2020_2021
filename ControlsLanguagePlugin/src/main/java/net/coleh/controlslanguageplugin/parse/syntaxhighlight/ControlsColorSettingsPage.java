package net.coleh.controlslanguageplugin.parse.syntaxhighlight;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;

import net.coleh.controlslanguageplugin.ControlsIcons;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import javax.swing.Icon;

public class ControlsColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("OPERATOR", ControlsSyntaxHighlighter.OPERATOR),
            new AttributesDescriptor("LABEL", ControlsSyntaxHighlighter.LABEL),
            new AttributesDescriptor("FUNCTION", ControlsSyntaxHighlighter.VERB),
            new AttributesDescriptor("VALUE", ControlsSyntaxHighlighter.VALUE),
            new AttributesDescriptor("COMMA", ControlsSyntaxHighlighter.COMMA),
            new AttributesDescriptor("SEMICOLON", ControlsSyntaxHighlighter.SEMICOLON),
            new AttributesDescriptor("COMMENT", ControlsSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("STATEMENT", ControlsSyntaxHighlighter.STATEMENT),
            new AttributesDescriptor("BAD_CHARACTER", ControlsSyntaxHighlighter.BAD_CHARACTER)
    };

    public Icon getIcon() {
        return ControlsIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new ControlsSyntaxHighlighter();
    }

    public String getDemoText() {
        return "i think the speed is 1\n" +
                "i think that the driveMotors are fl, fr, bl, and br\n" +
                "\n" +
                "the leftJoystickX, the leftJoystickY, and the rightJoystickX control the driveMotors with the omni calculation\n" +
                "\n" +
                "the slowSpeed is the speed with a scale of 0.5\n" +
                "\n" +
                "the gamepad2LeftJoystickX, the gamepad2LeftJoystickY, and the gamepad2RightJoystickX control the driveMotors with the higher priority, an omni calculation, and a scale of the slowSpeed\n" +
                "\n" +
                "the aButton sets the drumMotor to 1\n" +
                "dpadDown sets the drumMotor to -1 with a lower priority\n" +
                "the leftTrigger does the flywheel\n" +
                "i promise that when the time is roughly 30,000, the shooterArm is 0.8. otherwise, it is 0.\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Controls";
    }
}

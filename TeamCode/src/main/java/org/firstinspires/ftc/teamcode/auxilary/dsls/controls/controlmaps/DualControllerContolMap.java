package org.firstinspires.ftc.teamcode.auxilary.dsls.controls.controlmaps;

public class DualControllerContolMap extends ControlMap {
    public static String drive = "ternary( or(or(leftStickY, leftStickX), rightStickX),vector3(deadzone(leftStickY, 0.1), deadzone(leftStickX, 0.05), rightStickX),vector3(scale(deadzone(gamepad2LeftStickY, 0.1), 0.4), scale(deadzone(gamepad2LeftStickX, 0.05), 0.4), scale(gamepad2RightStickX, 0.4)))";
    public static String intake = "ternary(cross, -1, ternary(triangle, 1, ternary(leftTrigger, -1, 0)))";
    public static String drum = "ternary(dpadUp, -1, ternary(dpadDown, 1, ternary(gamepad2DpadDown, 1, ternary(leftTrigger, -1, ternary(gamepad2DpadUp, -1, 0)))))";
    public static String shooterArm = "ternary(greaterThan(gamepad2RightTrigger, 0.1), 0, 0.63)";
    public static String flywheelRight = "scale(ternary(gamepad2LeftTrigger, 1, 0), -1)";
    public static String flywheelLeft  = "scale(ternary(gamepad2LeftTrigger, 1, 0), 1)";
    public static String wobbleGrabRight = "ternary(gamepad2RightBumper, 0, 1)";
    public static String wobbleGrabLeft = "ternary(gamepad2RightBumper, 1, 0)";
    public static String wobbleArmRight = "ternary(gamepad2LeftBumper, 0.086, 0.055)";
    public static String wobbleArmLeft = "ternary(gamepad2LeftBumper, 0.055, 0.086)";

}

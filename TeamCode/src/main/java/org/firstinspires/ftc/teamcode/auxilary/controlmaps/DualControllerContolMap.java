package org.firstinspires.ftc.teamcode.auxilary.controlmaps;

public class DualControllerContolMap extends ControlMap {
    public static String drive = "ternary( or(or(leftStickY, leftStickX), rightStickX),vector3(deadzone(leftStickY, 0.1), deadzone(leftStickX, 0.05), rightStickX),vector3(scale(deadzone(gamepad2LeftStickY, 0.1), 0.2), scale(deadzone(gamepad2LeftStickX, 0.05), 0.2), scale(gamepad2RightStickX, 0.2)))";
    public static String intake = "ternary(cross, -1, ternary(triangle, 1, ternary(leftTrigger, -1, 0)))";
    public static String drum = "ternary(dpadUp, -1, ternary(dpadDown, 1, ternary(gamepad2DpadDown, 1, ternary(leftTrigger, -1, ternary(gamepad2LeftTrigger, -1, 0)))))";
    public static String shooterArm = "ternary(gamepad2Cross, 0, 0.63)";
    public static String flywheelRight = "scale(ternary(gamepad2DpadUp, -1, ternary(gamepad2RightTrigger, 1, 0)), -1)";
    public static String flywheelLeft  = "scale(ternary(gamepad2DpadUp, -1, ternary(gamepad2RightTrigger, 1, 0)), 1)";
    public static String wobbleGrabRight = "toggleBetween(greaterThan(rightTrigger, 0.2), 0, -0.1)";
    public static String wobbleGrabLeft = "toggleBetween(greaterThan(rightTrigger, 0.2), 0, 0.1)";
    public static String wobbleArmRight = "toggleBetween(or(leftBumper, rightBumper), 0, 1)";
    public static String wobbleArmLeft = "toggleBetween(or(leftBumper, rightBumper), 1, 0)";

}

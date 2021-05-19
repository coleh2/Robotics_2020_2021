package org.firstinspires.ftc.teamcode.auxilary.controlmaps;

public class DualControllerContolMap extends ControlMap {
    public static String drive = "ternary( or(or(leftStickY, leftStickX), rightStickX), scale(vector3(deadzone(leftStickY, 0.1), deadzone(leftStickX, 0.05), rightStickX), ternary(rightStickX, 1.0, 1.3)), vector3(scale(deadzone(gamepad2LeftStickY, 0.15), 0.4), scale(deadzone(gamepad2LeftStickX, 0.15), 0.4), scale(gamepad2RightStickX, 0.4)))";
    public static String intake = "ternary(cross, -1, ternary(triangle, 1, ternary(leftTrigger, -1, 0)))";
    public static String drum = "ternary(dpadUp, -1, ternary(dpadDown, 1, ternary(gamepad2DpadDown, 1, ternary(leftTrigger, -1, ternary(gamepad2DpadUp, -1, 0)))))";
    public static String spinner = "ternary(dpadRight, 0.5, ternary(gamepad2DpadRight, 0.5, ternary(gamepad2Cross, -0.5, ternary(dpadLeft, -0.5, ternary(leftTrigger, 0.5, 0)))))";
    public static String shooterArm = "ternary(greaterThan(gamepad2RightTrigger, 0.1), 0.3, 0.95)";
//    public static String flywheel = "ternary(gampepad2Triangle, -0.3, ternary(greaterThan(gamepad2LeftTrigger, 0.3), 1, 0))";
    public static String flywheel = "ternary(gamepad2LeftTrigger, 1, 0)";
    public static String wobbleGrabRight = "ternary(gamepad2RightBumper, 1, 0)";
    public static String wobbleGrabLeft = "ternary(gamepad2RightBumper, 1, 0)";
    public static String wobbleArmRight = "ternary(gamepad2LeftBumper, 0.6, 0)";
    public static String wobbleArmLeft = "ternary(gamepad2LeftBumper, 0.6, 0)";
    public static String shoulderLeft = "ternary(gamepad2DpadLeft, 0, 0.1)";
    public static String shoulderRight = "ternary(gamepad2DpadLeft, 0, 0.1)";
    public static String shooterStop = "ternary(greaterThan(rightTrigger, 0.1), 0.25, 0.57)";

}

package org.firstinspires.ftc.teamcode.auxilary.controlmaps;

public class DualControllerContolMap extends ControlMap {
    public static String drive = "ternary( or(or(leftJoystickY, leftJoystickX), rightJoystickX),vector3(deadzone(leftJoystickY, 0.1), deadzone(leftJoystickX, 0.05), rightJoystickX),vector3(scale(deadzone(gamepad2leftJoystickY, 0.1), 0.2), scale(deadzone(gamepad2leftJoystickX, 0.05), 0.2), scale(gamepad2rightJoystickX, 0.2)))";
    public static String intake = "ternary(cross, 1, ternary(triangle, -1, ternary(leftTrigger, 1, 0)))";
    public static String drum = "ternary(dpadUp, 1, ternary(dpadDown, -1, ternary(gamepad2DpadDown, -1, ternary(leftTrigger, 1, ternary(gamepad2LeftTrigger, 1, 0)))))";
    public static String shooterArm = "ternary(gamepad2Cross, 0, 0.7)";
    public static String flywheelLeft = "scale(ternary(gamepad2DpadUp, -1, ternary(gamepad2RightTrigger, 1, 0)), -1)";
    public static String flywheelRight = "scale(ternary(gamepad2DpadUp, -1, ternary(gamepad2RightTrigger, 1, 0)), 1)";
    public static String wobbleGrabRight = "toggle(gt(rightTrigger, 0.2), 0, 1)";
    public static String wobbleGrabLeft = "toggle(gt(rightTrigger, 0.2), 1, 0)";
    public static String wobbleArmRight = "toggle(or(leftBumper, rightBumper), 0, 1)";
    public static String wobbleArmLeft = "toggle(or(leftBumper, rightBumper), 1, 0)";

}

package org.firstinspires.ftc.teamcode.auxilary.dsls.controls.controlmaps;

public class JoshuaIsDeadzoneAndKilledJameControlMap extends ControlMap {
    public static String drive = "vector3(deadzone(leftStickY,0.15), deadzone(leftStickX,0.15), rightStickX)";
}

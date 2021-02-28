package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

public class StopDriveFunction extends Function {
    public String name = "stopDrive";
    public int argCount = 0;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private org.firstinspires.ftc.teamcode.managers.MovementManager manager;

    public StopDriveFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    public float[] call(float[][] args) {
        manager.stopDrive();
        return new float[0];
    }
}
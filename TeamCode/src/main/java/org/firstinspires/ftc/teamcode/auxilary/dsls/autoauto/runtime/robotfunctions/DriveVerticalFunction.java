package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

public class DriveVerticalFunction extends Function {
    public String name = "driveVertical";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private MovementManager manager;

    public DriveVerticalFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    public float[] call(float[][] args) {
        manager.driveVertical(args[0][0], args[1][0]);
        return new float[0];
    }
}
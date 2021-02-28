package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

public class DriveWithVerticalFunction extends Function {
    public String name = "driveWithVertical";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private MovementManager manager;

    public DriveWithVerticalFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    public float[] call(float[][] args) {
        manager.driveWithVertical(args[0][0], args[1][0]);
        return new float[0];
    }
}
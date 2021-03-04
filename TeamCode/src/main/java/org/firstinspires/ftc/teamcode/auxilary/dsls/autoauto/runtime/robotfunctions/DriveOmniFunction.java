package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

import java.util.Arrays;

public class DriveOmniFunction extends Function {
    public String name = "driveOmni";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private MovementManager manager;

    public DriveOmniFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
        this.argCount = 3;
        this.name = "driveOmni";
    }

    public float[] call(float[][] args) {
        if(args.length > 1) manager.driveOmni(new float[] { args[0][0], args[1][0], args[2][0] });
        else manager.driveOmni(args[0]);
        return new float[0];
    }
}
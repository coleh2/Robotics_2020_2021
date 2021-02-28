package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

public class ResetEncodersFunction extends Function {
    public String name = "resetEncoders";
    public int argCount = 0;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private org.firstinspires.ftc.teamcode.managers.MovementManager manager;

    public ResetEncodersFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    public float[] call(float[][] args) {
        manager.resetEncoders();
        return new float[0];
    }
}
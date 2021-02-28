package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class ResetMotorEncoderFunction extends Function {
    public String name = "resetEncoders";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ManipulationManager.class;

    private org.firstinspires.ftc.teamcode.managers.ManipulationManager manager;

    public ResetMotorEncoderFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public float[] call(float[][] args) {
        manager.resetEncoders((int)args[0][0]);
        return new float[0];
    }
}
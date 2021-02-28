package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

public class RunMotorUsingEncoderFunction extends Function {
    public String name = "runUsingEncoders";
    public int argCount = 1;
    public Class<?> declaringClass = ManipulationManager.class;

    private ManipulationManager manager;

    public RunMotorUsingEncoderFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public float[] call(float[][] args) {
        manager.runUsingEncoders();
        return new float[0];
    }
}
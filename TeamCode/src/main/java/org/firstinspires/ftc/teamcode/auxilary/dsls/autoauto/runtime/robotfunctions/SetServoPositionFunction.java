package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

public class SetServoPositionFunction extends Function {
    public String name = "setServoPosition";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ManipulationManager.class;

    private org.firstinspires.ftc.teamcode.managers.ManipulationManager manager;

    public SetServoPositionFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public float[] call(float[][] args) {
        manager.setServoPosition((int)args[0][0], args[1][0]);
        return new float[0];
    }
}
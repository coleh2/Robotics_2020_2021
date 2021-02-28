package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

public class GetMotorPowerFunction extends Function {
    public String name = "getMotorPower";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ManipulationManager.class;

    private org.firstinspires.ftc.teamcode.managers.ManipulationManager manager;

    public GetMotorPowerFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public float[] call(float[][] args) {
        return new float[] { (float)manager.getMotorPower((int)args[0][0]) };
    }
}
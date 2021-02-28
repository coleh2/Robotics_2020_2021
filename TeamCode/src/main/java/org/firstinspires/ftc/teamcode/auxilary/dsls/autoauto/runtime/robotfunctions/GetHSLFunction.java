package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

public class GetHSLFunction extends Function {
    public String name = "getHSL";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.SensorManager.class;

    private org.firstinspires.ftc.teamcode.managers.SensorManager manager;

    public GetHSLFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.SensorManager)manager;
    }

    public float[] call(float[][] args) {
        return manager.getHSL((int)args[0][0]);
    }
}
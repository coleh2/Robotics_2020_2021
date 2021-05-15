package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class IsSpecialOneFunction extends Function {
    public String name = "isSpecial";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.SensorManager.class;

    private org.firstinspires.ftc.teamcode.managers.SensorManager manager;

    public IsSpecialOneFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.SensorManager)manager;
    }

    public float[] call(float[][] args) {
        return new float[] { manager.isSpecial1((int)args[0][0]) ? 1f : 0f };
    }
}

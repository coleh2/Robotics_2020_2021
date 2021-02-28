package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

public class GetServoPowerFunction extends Function {
    public String name = "getServoPower";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ManipulationManager.class;

    private org.firstinspires.ftc.teamcode.managers.ManipulationManager manager;

    public GetServoPowerFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public float[] call(float[][] args) {
        return new float[] { (float)manager.getServoPower((int)args[0][0]) };
    }
}
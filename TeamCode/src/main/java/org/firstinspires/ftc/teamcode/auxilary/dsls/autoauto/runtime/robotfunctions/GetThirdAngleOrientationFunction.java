package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class GetThirdAngleOrientationFunction extends Function {
    public String name = "getThirdAngleOrientation";
    public int argCount = 0;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ImuManager.class;


    private org.firstinspires.ftc.teamcode.managers.ImuManager manager;

    public GetThirdAngleOrientationFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ImuManager)manager;
    }

    public float[] call(float[][] args) {
        return new float[] { (float)manager.getOrientation().thirdAngle };
    }

}

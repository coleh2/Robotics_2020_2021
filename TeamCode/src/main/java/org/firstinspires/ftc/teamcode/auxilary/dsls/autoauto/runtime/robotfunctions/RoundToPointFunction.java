package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class RoundToPointFunction extends Function {
    public String name = "roundToPoint";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public RoundToPointFunction() {
        
    }

    public float[] call(float[][] args) {
        return new float[] { PaulMath.roundToPoint(args[0][0], args[1][0]) };
    }
}
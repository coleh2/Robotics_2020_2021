package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class HighestValueFunction extends Function {
    public String name = "highestValue";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public HighestValueFunction() {
        
    }

    public float[] call(float[][] args) {
        return new float[] { PaulMath.highestValue(args[0]) };
    }
}
package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class OmniCalcFunction extends Function {
    public String name = "omniCalc";
    public int argCount = 3;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public OmniCalcFunction() {
        
    }

    public float[] call(float[][] args) {
        return PaulMath.omniCalc(args[0][0], args[1][0], args[2][0]);
    }
}
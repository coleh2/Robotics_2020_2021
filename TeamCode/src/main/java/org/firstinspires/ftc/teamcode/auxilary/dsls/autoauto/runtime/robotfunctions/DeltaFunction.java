package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class DeltaFunction extends Function {
    public String name = "delta";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    public DeltaFunction() {
        
    }

    public float[] call(float[][] args) {
        return new float[] { PaulMath.delta(args[0][0], args[1][0]) };

    }
}
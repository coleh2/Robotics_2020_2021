package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class CartesianToPolarFunction extends Function {
    public String name = "cartesianToPolar";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    public CartesianToPolarFunction() {
        
    }

    public float[] call(float[][] args) {
        return PaulMath.cartesianToPolar(args[0][0], args[0][1]);
    }
}
package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class EncoderDistanceFunction extends Function {
    public String name = "encoderDistance";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    public EncoderDistanceFunction() {
        
    }

    public float[] call(float[][] args) {
        return new float[] { PaulMath.encoderDistance(args[0][0]) };
    }
}
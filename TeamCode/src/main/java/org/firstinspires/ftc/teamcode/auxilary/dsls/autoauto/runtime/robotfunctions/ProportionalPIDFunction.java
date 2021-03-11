package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class ProportionalPIDFunction extends Function {
    public String name = "proportionalPID";
    public int argCount = 3;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public float[] call(float[][] args) {
        return new float[] { PaulMath.proportionalPID(args[0][0], args[1][0], args[2][0]) };
    }
}
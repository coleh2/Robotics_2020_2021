package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoUndefined;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class RoundToPointFunction extends NativeRobotFunction {
    public String name = "roundToPoint";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public RoundToPointFunction() {
        
    }

    public float[] call(float[][] args) {
        return new float[] { PaulMath.roundToPoint(args[0][0], args[1][0]) };
    }
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        return new NumericValue(PaulMath.roundToPoint(((NumericValue)args[0]).getFloat(), ((NumericValue)args[1]).getFloat()));
    }
}
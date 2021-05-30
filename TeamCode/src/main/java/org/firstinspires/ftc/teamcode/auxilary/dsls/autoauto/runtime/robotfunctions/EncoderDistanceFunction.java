package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class EncoderDistanceFunction extends NativeRobotFunction {
    public String name = "encoderDistance";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    public EncoderDistanceFunction() {
        
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        return new NumericValue(PaulMath.encoderDistance(((NumericValue)args[0]).getFloat()));
    }
}
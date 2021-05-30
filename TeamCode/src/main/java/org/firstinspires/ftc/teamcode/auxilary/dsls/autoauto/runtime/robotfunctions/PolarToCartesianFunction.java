package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class PolarToCartesianFunction extends NativeRobotFunction {
    public String name = "polarToCartesian";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public PolarToCartesianFunction() {
        
    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        AutoautoArray coords = (AutoautoArray)args[0];

        float[] v = PaulMath.polarToCartesian(((NumericValue)coords.elems[0]).getFloat(), ((NumericValue)coords.elems[1]).getFloat());
        AutoautoValue[] res = new AutoautoValue[v.length];

        for(int i = 0; i < v.length; i++) {
            res[i] = new NumericValue(v[i]);
        }

        return new AutoautoArray(res);
    }
}
package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class OmniCalcFunction extends NativeRobotFunction {
    public String name = "omniCalc";
    public int argCount = 3;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public OmniCalcFunction() {
        
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {

        float[] v = PaulMath.omniCalc(((NumericValue)args[0]).getFloat(), ((NumericValue)args[1]).getFloat(), ((NumericValue)args[2]).getFloat());
        AutoautoValue[] res = new AutoautoValue[v.length];

        for(int i = 0; i < v.length; i++) {
            res[i] = new NumericValue(v[i]);
        }

        return new AutoautoArray(res);
    }
}
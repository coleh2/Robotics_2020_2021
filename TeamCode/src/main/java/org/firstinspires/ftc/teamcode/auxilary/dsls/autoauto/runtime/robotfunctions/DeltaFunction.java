package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class DeltaFunction extends NativeRobotFunction {
    public String name = "delta";
    public int argCount = 2;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    public DeltaFunction() {
        
    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        AutoautoArray coords = (AutoautoArray)args[0];

        return new NumericValue(PaulMath.delta(((NumericValue)coords.elems[0]).getFloat(), ((NumericValue)coords.elems[0]).getFloat()));
    }
}
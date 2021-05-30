package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoUndefined;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

public class ProportionalPIDFunction extends NativeRobotFunction {
    public String name = "proportionalPID";
    public int argCount = 3;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.auxilary.PaulMath.class;

    private org.firstinspires.ftc.teamcode.auxilary.PaulMath manager;

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        if(args.length == 2) return new NumericValue(PaulMath.proportionalPID(((NumericValue)args[0]).getFloat(), ((NumericValue)args[1]).getFloat()));
        else if(args.length == 5) return new NumericValue(PaulMath.proportionalPID(((NumericValue)args[0]).getFloat(), ((NumericValue)args[1]).getFloat(), ((NumericValue)args[2]).getFloat(),
                                                                                ((NumericValue)args[3]).getFloat(), ((NumericValue)args[4]).getFloat()));
        else if(args.length == 3) return new NumericValue(PaulMath.proportionalPID(((NumericValue)args[0]).getFloat(), ((NumericValue)args[1]).getFloat(), ((NumericValue)args[2]).getFloat()));
        else return new AutoautoUndefined();
    }
}
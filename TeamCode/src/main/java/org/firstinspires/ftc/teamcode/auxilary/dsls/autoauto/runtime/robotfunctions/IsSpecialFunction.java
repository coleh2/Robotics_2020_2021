package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.BooleanValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class IsSpecialFunction extends NativeRobotFunction {
    public String name = "isSpecial";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.SensorManager.class;

    private org.firstinspires.ftc.teamcode.managers.SensorManager manager;

    public IsSpecialFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.SensorManager)manager;
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        return new BooleanValue( manager.isSpecial((int) ((NumericValue)args[0]).getFloat()));
    }
}

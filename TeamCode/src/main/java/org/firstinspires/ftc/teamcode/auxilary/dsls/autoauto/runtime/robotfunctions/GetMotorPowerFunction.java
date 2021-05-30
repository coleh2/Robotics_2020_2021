package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class GetMotorPowerFunction extends NativeRobotFunction {
    public String name = "getMotorPower";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.ManipulationManager.class;

    private org.firstinspires.ftc.teamcode.managers.ManipulationManager manager;

    public GetMotorPowerFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.ManipulationManager)manager;
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        return new NumericValue((float) manager.getMotorPower((int)((NumericValue)args[0]).getFloat()));
    }
}
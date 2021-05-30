package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class GetMotorPositionsFunction extends NativeRobotFunction {
    public String name = "getMotorPositions";
    public int argCount = 0;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private org.firstinspires.ftc.teamcode.managers.MovementManager manager;

    public GetMotorPositionsFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        float[] v = manager.getMotorPositions();

        AutoautoValue[] res = new AutoautoValue[v.length];

        for(int i = 0; i < v.length; i++) {
            res[i] = new NumericValue(v[i]);
        }

        return new AutoautoArray(res);
    }
}
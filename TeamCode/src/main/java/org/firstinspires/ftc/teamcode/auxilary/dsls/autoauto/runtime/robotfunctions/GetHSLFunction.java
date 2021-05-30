package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class GetHSLFunction extends NativeRobotFunction {
    public String name = "getHSL";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.SensorManager.class;

    private org.firstinspires.ftc.teamcode.managers.SensorManager manager;

    public GetHSLFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.SensorManager)manager;
    }

    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        float[] v = manager.getHSL((int)((NumericValue)args[0]).getFloat());

        AutoautoValue[] res = new AutoautoValue[v.length];

        for(int i = 0; i < v.length; i++) {
            res[i] = new NumericValue(v[i]);
        }

        return new AutoautoArray(res);
    }
}
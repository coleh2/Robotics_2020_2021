package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoUndefined;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

public class DriveOmniExponentialFunction extends NativeRobotFunction {
    public String name = "driveOmniExponential";
    public int argCount = 1;
    public Class<?> declaringClass = org.firstinspires.ftc.teamcode.managers.MovementManager.class;

    private MovementManager manager;

    public DriveOmniExponentialFunction(FeatureManager manager) {
        this.manager = (org.firstinspires.ftc.teamcode.managers.MovementManager)manager;
    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        if(args.length == 3) {
            manager.driveOmniExponential(new float[] {
                    ((NumericValue)args[0]).getFloat(),
                    ((NumericValue)args[1]).getFloat(),
                    ((NumericValue)args[2]).getFloat()
            });
        } else {
            AutoautoArray arr = (AutoautoArray)args[0];
            float[] floats = new float[arr.elems.length];
            for(int i = 0; i < floats.length; i++) {
                floats[i] = ((NumericValue)arr.elems[i]).getFloat();
            }
            manager.driveOmniExponential(floats);
        }
        return new AutoautoUndefined();
    }
}
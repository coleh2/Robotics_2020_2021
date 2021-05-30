package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoUndefined;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.NativeRobotFunction;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.Arrays;

public class LogFunction extends NativeRobotFunction {
    public String name = "log";
    public int argCount = 1;

    public LogFunction() {

    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        FeatureManager.logger.log(args[0].getString());
        return new AutoautoUndefined();
    }
}
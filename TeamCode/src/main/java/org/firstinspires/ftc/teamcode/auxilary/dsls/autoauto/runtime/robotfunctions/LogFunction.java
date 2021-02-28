package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

import java.util.Arrays;

public class LogFunction extends Function {
    public String name = "log";
    public int argCount = 1;

    public LogFunction() {

    }

    public float[] call(float[][] args) {
        FeatureManager.logger.log(Arrays.toString(args[0]));
        return new float[0];
    }
}
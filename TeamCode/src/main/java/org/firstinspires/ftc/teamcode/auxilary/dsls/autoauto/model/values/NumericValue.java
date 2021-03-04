package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.Arrays;

public class NumericValue extends Value {
    public float[] returnValue;
    public NumericValue(float value) {
        this.returnValue = new float[] { value };
    }
    public NumericValue(String value) {
        this.returnValue = new float[] { Float.parseFloat(value) };
    }
    public void loop() {}

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }
    public String toString() {
        return this.returnValue[0] + "";
    }
}

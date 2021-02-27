package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

public class NumericValue extends Value {
    public float returnValue;
    public NumericValue(float value) {
        this.returnValue = value;
    }
    public NumericValue(String value) {
        this.returnValue = Float.parseFloat(value);
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

public class NumericValue extends Value {
    public float[] returnValue;
    public NumericValue(float value) {
        this.returnValue = new float[] { value };
    }
    public NumericValue(String value) {
        this.returnValue = new float[] { Float.parseFloat(value) };
    }
    public String toString() {
        return this.returnValue[0] + "";
    }
}

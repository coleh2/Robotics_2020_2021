package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.jetbrains.annotations.NotNull;

public class StringLiteral extends Value {
    public float[] returnValue;
    public StringLiteral(String value) {

        char[] charArray = value.toCharArray();
        this.returnValue = new float[charArray.length];

        for(int i = charArray.length - 1; i >= 0; i--) {
            this.returnValue[i] = charArray[i];
        }
    }
    public void loop() {}

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }
    @NotNull
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(float v : this.returnValue) res.append((char) v);
        return res.toString();
    }
}

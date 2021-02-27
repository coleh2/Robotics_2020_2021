package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

public class Value {
    public float returnValue = 0;

    public void loop() {

    }

    public static Value createProperValueType(String src) {
        String trimmed = src.trim();
        if(trimmed.matches("^-?[0-9]+\\.?[0-9]+$")) return new NumericValue(trimmed);
        else if(trimmed.matches("^-?[0-9]+\\.?[0-9]+[a-z]+$")) return new TimeUnit(trimmed);
        else if(trimmed.indexOf('(') > -1) return new FunctionCall(trimmed);
        else if(trimmed.matches("^\\w+$")) return new VariableReference(trimmed);

        return null;
    }
}

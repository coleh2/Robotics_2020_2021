package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.FunctionStore;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.VariableStore;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class Value {
    protected float[] returnValue = new float[0];
    public FunctionStore runtimeFunctionStore;
    public VariableStore runtimeVariableStore;


    public static Value createProperValueType(String src) {
        String trimmed = src.trim();
        if(trimmed.matches("^-?[0-9]*\\.?[0-9]+$")) return new NumericValue(trimmed);
        else if(trimmed.matches("^-?[0-9]*\\.?[0-9]+[a-z]+$")) return new UnitValue(trimmed);
        else if(trimmed.startsWith("[") && trimmed.endsWith("]")) return new ArrayLiteral(trimmed);
        else if(trimmed.indexOf('(') > -1) return new FunctionCall(trimmed);
        else if(trimmed.startsWith("\"") && trimmed.endsWith("\"")) return new StringLiteral(trimmed);
        else if(trimmed.matches("^\\w+$")) return new VariableReference(trimmed);
        else return new ArithmeticValue(trimmed);
    }

    public void init() {}
    public void loop() {}

    public float[] getReturnValue() {
        return this.returnValue;
    }

    public final void setRuntimeReferences(FunctionStore functions, VariableStore variables) {
        this.runtimeFunctionStore = functions;
        this.runtimeVariableStore = variables;
    }
}

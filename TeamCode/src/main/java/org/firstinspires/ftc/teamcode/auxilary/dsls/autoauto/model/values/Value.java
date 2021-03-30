package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.FunctionStore;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.VariableStore;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class Value {
    protected float[] returnValue = new float[0];
    public FunctionStore runtimeFunctionStore;
    public VariableStore runtimeVariableStore;

    public static Value createProperValueType(String src) {
        return createProperValueType(src, false);
    }

    public static Value createProperValueType(String src, boolean noArithmetic) {
        String trimmed = ParserTools.deParen(src.trim());
        if(trimmed.matches("^-?[0-9]*\\.?[0-9]+$")) return new NumericValue(trimmed);
        else if(trimmed.matches("^-?[0-9]*\\.?[0-9]+[a-z]+$")) return new UnitValue(trimmed);
        else if(trimmed.startsWith("[") && trimmed.endsWith("]")) return new ArrayLiteral(trimmed);
        else if(trimmed.matches("^\\w+\\(.+")) return new FunctionCall(trimmed);
        else if(trimmed.matches("^\"[^\"]+\"$")) return new StringLiteral(trimmed);
        else if(trimmed.matches("^-\\w+$")) return new ArithmeticValue(createProperValueType(trimmed.substring(1)), "*", new NumericValue("-1"));
        else if(trimmed.matches("^\\w+$")) return new VariableReference(trimmed);
        else if(!noArithmetic) return new ArithmeticValue(trimmed);
        else {
            FeatureManager.logger.log("[AUTOAUTO ERROR]  Unparsable value `" + trimmed + "`. This is not necessarily a 'bad' problem; it happens normally when negative literals are used in arithmetic. It will now default to `0`.");
            return new NumericValue("0");
        }
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

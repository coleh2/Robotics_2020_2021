package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArithmeticValue extends Value {
    String operator;
    Value right;
    Value left;

    //TODO: parsing!!
    public ArithmeticValue(Value left, String operator, Value right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ArithmeticValue(String src) {

        int additionIndex = ParserTools.groupAwareIndexOf(src, '+');
        int subtractionIndex = ParserTools.groupAwareIndexOf(src, '-');

        //lowest index that isn't -1
        int asGemdasIndex = Math.min(
                additionIndex < 0 ? subtractionIndex : additionIndex,
                subtractionIndex < 0 ? additionIndex : subtractionIndex);

        if(asGemdasIndex > -1) {
            this.left = Value.createProperValueType(src.substring(0, asGemdasIndex));
            this.operator = "" + src.charAt(asGemdasIndex);
            this.right = Value.createProperValueType(src.substring(asGemdasIndex + 1));
            return;
        }

        int multiplicationIndex = ParserTools.groupAwareIndexOf(src, '*');
        int divisionIndex = ParserTools.groupAwareIndexOf(src, '/');

        //lowest index that isn't -1
        int mdGemdasIndex = Math.min(
                multiplicationIndex < 0 ? divisionIndex : multiplicationIndex,
                divisionIndex < 0 ? multiplicationIndex : divisionIndex);

        if(mdGemdasIndex > -1) {
            this.left = Value.createProperValueType(src.substring(0, mdGemdasIndex));
            this.operator = "" + src.charAt(mdGemdasIndex);
            this.right = Value.createProperValueType(src.substring(mdGemdasIndex + 1));
            return;
        }

        int exponentIndex = ParserTools.groupAwareIndexOf(src, '^');
        if(exponentIndex > -1) {
            this.left = Value.createProperValueType(src.substring(0, exponentIndex));
            this.operator = "^";
            this.right = Value.createProperValueType(src.substring(exponentIndex + 1));
            return;
        }

        int moduloIndex = ParserTools.groupAwareIndexOf(src, '%');
        if(moduloIndex > -1) {
            this.left = Value.createProperValueType(src.substring(0, moduloIndex));
            this.operator = "%";
            this.right = Value.createProperValueType(src.substring(moduloIndex + 1));
            return;
        }

        //fallback
        this.left = Value.createProperValueType(src, true);
        this.operator = "";
        this.right = null;
    }

    @Override
    public void init() {
        if(left != null) {
            left.setRuntimeReferences(this.runtimeFunctionStore, this.runtimeVariableStore);
            left.init();
        }

        if(right != null) {
            right.setRuntimeReferences(this.runtimeFunctionStore, this.runtimeVariableStore);
            right.init();
        }
    }

    public void loop() {
        left.loop();
        right.loop();

        if(
            (left.getReturnValue().length >= 2 && left.getReturnValue()[0] == '"' && left.getReturnValue()[left.getReturnValue().length - 1] == '"') ||
            (right.getReturnValue().length >= 2 && right.getReturnValue()[0] == '"' && right.getReturnValue()[right.getReturnValue().length - 1] == '"')
            ) {
            if(operator.equals("+")) concatenate(left, right);
            else FeatureManager.logger.log("[AUTOAUTO ERROR] Bad operator " + operator + "on string value.");

            return;
        }

        switch(operator) {
            case "%":
                this.returnValue = new float[] { left.getReturnValue()[0] % right.getReturnValue()[0] };
                break;
            case "^":
                this.returnValue = new float[] { (float)Math.pow(left.getReturnValue()[0], right.getReturnValue()[0]) };
                break;
            case "*":
                this.returnValue = new float[] { left.getReturnValue()[0] * right.getReturnValue()[0] };
                break;
            case "/":
                this.returnValue = new float[] { left.getReturnValue()[0] / right.getReturnValue()[0] };
                break;
            case "+":
                this.returnValue = new float[] { left.getReturnValue()[0] + right.getReturnValue()[0] };
                break;
            case "-":
                this.returnValue = new float[] { left.getReturnValue()[0] - right.getReturnValue()[0] };
                break;
            default:
                this.returnValue = left.getReturnValue();
                break;
        }

    }

    private void concatenate(Value a, Value b) {
        String aStr = "";
        String bStr = "";

        if(StringLiteral.isStringCode(a.getReturnValue())) aStr = StringLiteral.codesToString(a.getReturnValue());
        else if(a instanceof UnitValue) aStr += a.toString();
        else if(a.getReturnValue().length == 1) aStr = a.getReturnValue()[0] + "";
        else aStr += Arrays.toString(a.getReturnValue());

        if(StringLiteral.isStringCode(b.getReturnValue())) bStr = StringLiteral.codesToString(b.getReturnValue());
        else if(b instanceof UnitValue) bStr += b.toString();
        else if(b.getReturnValue().length == 1) bStr = b.getReturnValue()[0] + "";
        else bStr += Arrays.toString(b.getReturnValue());

        this.returnValue = (new StringLiteral("\"" + aStr + bStr + "\"")).getReturnValue();
    }

    @NotNull
    public String toString() {
        return "(" + (left == null ? "<null>" : left.toString()) + ")" + operator + "(" + (right == null ? "<null>" : right.toString()) + ")";
    }

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }
}

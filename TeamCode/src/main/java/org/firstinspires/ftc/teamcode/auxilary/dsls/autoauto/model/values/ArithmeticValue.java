package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArithmeticValue extends AutoautoValue {
    String operator;
    AutoautoValue right;
    AutoautoValue left;

    AutoautoPrimitive returnValue;

    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public ArithmeticValue(AutoautoValue left, String operator, AutoautoValue right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
    @Override
    public void init() {
        left.init();
        right.init();
    }

    public void loop() {
        left.loop();
        right.loop();

        AutoautoValue leftRes = left.getResolvedValue();
        AutoautoValue rightRes = right.getResolvedValue();

        if(leftRes instanceof AutoautoString || rightRes instanceof AutoautoString) {
            if(operator.equals("+")) concatenate((AutoautoString)left, (AutoautoString)right);
            else FeatureManager.logger.log("[AUTOAUTO ERROR] Bad operator " + operator + "on string value.");

            return;
        }

        float a = ((NumericValue)leftRes).getFloat();
        float b = ((NumericValue)rightRes).getFloat();

        switch(operator) {
            case "%":
                this.returnValue = new NumericValue(a % b);
                break;
            case "^":
                this.returnValue = new NumericValue((float) Math.pow(a, b));
                break;
            case "*":
                this.returnValue = new NumericValue(a * b);
                break;
            case "/":
                this.returnValue = new NumericValue(a / b);
                break;
            case "+":
                this.returnValue = new NumericValue(a + b);
                break;
            case "-":
                this.returnValue = new NumericValue(a - b);
                break;
            default:
                this.returnValue = new NumericValue(a);
                break;
        }
    }

    @Override
    public String getString() {
        return returnValue.getString();
    }

    private void concatenate(AutoautoString a, AutoautoString b) {
        this.returnValue = new AutoautoString(a.getString() + b.getString());
    }

    @NotNull
    public String toString() {
        return left + " " + operator + " " + right;
    }

    @Override
    public AutoautoPrimitive getResolvedValue() {
        return this.returnValue;
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        left.setScope(scope);
        right.setScope(scope);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}

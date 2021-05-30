package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

enum Type {LESS_THAN, LESS_EQUAL_THAN, EQUAL, GREATER_THAN, GREATER_EQUAL_THAN, NOT_EQUAL, FUNC_CALL, HUH}

public class BooleanOperator extends AutoautoValue {

    Type type;

    AutoautoValue a;
    AutoautoValue b;

    BooleanValue resolvedValue;

    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public BooleanOperator(AutoautoValue a, AutoautoValue b, String operator) {
        this.a = a;
        this.b = b;
        switch (operator) {
            case "<": this.type = Type.LESS_THAN; break;
            case "<=": this.type = Type.LESS_EQUAL_THAN; break;
            case ">": this.type = Type.GREATER_THAN; break;
            case ">=": this.type = Type.GREATER_EQUAL_THAN; break;
            case "==": this.type = Type.EQUAL; break;
            case "!=": this.type = Type.NOT_EQUAL; break;
        }
    }
    @Override
    public AutoautoPrimitive getResolvedValue() {
        return resolvedValue;
    }

    public boolean getBoolean() {
        return resolvedValue.getBoolean();
    }

    public void init() {
        a.init();
        b.init();
    }

    public void loop() {
        a.loop();
        b.loop();

        AutoautoValue aRes = a.getResolvedValue();
        AutoautoValue bRes = b.getResolvedValue();

        //string comparison
        if(aRes instanceof AutoautoString || bRes instanceof AutoautoString) {
            String a = null;
            if (aRes instanceof AutoautoString) a = ((AutoautoString) aRes).getString();
            else if (aRes instanceof NumericValue) a = ((NumericValue) aRes).getFloat() + "";
            else if (aRes instanceof BooleanValue) a = ((BooleanValue) aRes).getBoolean() + "";

            String b = null;
            if (bRes instanceof AutoautoString) b = ((AutoautoString) bRes).getString();
            else if (bRes instanceof NumericValue) b = ((NumericValue) bRes).getFloat() + "";
            else if (bRes instanceof BooleanValue) b = ((BooleanValue) bRes).getBoolean() + "";

            boolean equal = a.equals(b);

            if (type.equals(Type.EQUAL)) resolvedValue = new BooleanValue(equal);
            else if (type.equals(Type.NOT_EQUAL)) resolvedValue = new BooleanValue(!equal);
            else if (type.equals(Type.GREATER_EQUAL_THAN)) resolvedValue = new BooleanValue(a.compareTo(b) >= 0);
            else if (type.equals(Type.GREATER_THAN)) resolvedValue = new BooleanValue(a.compareTo(b) > 0);
            else if (type.equals(Type.LESS_EQUAL_THAN)) resolvedValue = new BooleanValue(a.compareTo(b) <= 0);
            else if (type.equals(Type.LESS_THAN)) resolvedValue = new BooleanValue(a.compareTo(b) < 0);
        } else if(aRes instanceof NumericValue || bRes instanceof NumericValue) {
            //if it's not a string, numeric...
            float a = 0;
            if(aRes instanceof NumericValue) a = ((NumericValue) aRes).getFloat();
            else if (aRes instanceof BooleanValue) a = ((BooleanValue) aRes).getBoolean() ? 1f : 0f;

            float b = 0;
            if(bRes instanceof NumericValue) b = ((NumericValue) bRes).getFloat();
            else if (bRes instanceof BooleanValue) b = ((BooleanValue) bRes).getBoolean() ? 1f : 0f;

            if (type.equals(Type.EQUAL)) resolvedValue = new BooleanValue(a == b);
            else if (type.equals(Type.NOT_EQUAL)) resolvedValue = new BooleanValue(a != b);
            else if (type.equals(Type.GREATER_EQUAL_THAN)) resolvedValue = new BooleanValue(a >= b);
            else if (type.equals(Type.GREATER_THAN)) resolvedValue = new BooleanValue(a > b);
            else if (type.equals(Type.LESS_EQUAL_THAN)) resolvedValue = new BooleanValue(a <= b);
            else if (type.equals(Type.LESS_THAN)) resolvedValue = new BooleanValue(a < b);
        } else if(aRes instanceof BooleanValue || bRes instanceof BooleanValue) {
            //booleans are the lowest in the implicit casting cascade
            int a = ((BooleanValue) aRes).getBoolean() ? 1 : 0;

            int b = ((BooleanValue) bRes).getBoolean() ? 1 : 0;

            if (type.equals(Type.EQUAL)) resolvedValue = new BooleanValue(a == b);
            else if (type.equals(Type.NOT_EQUAL)) resolvedValue = new BooleanValue(a != b);
            else if (type.equals(Type.GREATER_EQUAL_THAN)) resolvedValue = new BooleanValue(a >= b);
            else if (type.equals(Type.GREATER_THAN)) resolvedValue = new BooleanValue(a > b);
            else if (type.equals(Type.LESS_EQUAL_THAN)) resolvedValue = new BooleanValue(a <= b);
            else if (type.equals(Type.LESS_THAN)) resolvedValue = new BooleanValue(a < b);
        }
    }

    @Override
    public String getString() {
        return resolvedValue.getString();
    }

    public String toString() {
        return this.a.toString() + " " + this.type.name() + " " + this.b.toString();
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return this.scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        a.setScope(scope);
        b.setScope(scope);
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

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import androidx.annotation.Nullable;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoArray;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoString;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.HashMap;

public class AutoautoRuntimeVariableScope {
    public HashMap<String, StoredAutoautoValue> variables;

    @Nullable
    public AutoautoRuntimeVariableScope parentScope;

    public AutoautoRuntimeVariableScope() {
        this.variables = new HashMap<String, StoredAutoautoValue>();
    }
    public AutoautoRuntimeVariableScope(@Nullable AutoautoRuntimeVariableScope parentScope) {
        this.parentScope = parentScope;
        this.variables = new HashMap<String, StoredAutoautoValue>();
    }

    public void put(String s, AutoautoPrimitive v) {
        StoredAutoautoValue value = getStored(s);

        if(value == null) {
            this.variables.put(s, new StoredAutoautoValue(v));
        } else {
            if(value.systemManaged) {
                throw new IllegalArgumentException("Variable " + s + " is a system variable");
            } else if(value.readOnly) {
                throw new IllegalArgumentException("Variable " + s + " is a read-only variable");
            };
            //if it's in this scope...
            if(this.variables.containsKey(s)) {
                //... set it
                value.value = v;
                this.variables.put(s, value);
            } // if not
            else {
                //... propagate up. This gets inefficient with a large number of nested scopes; should be fine for now, but should be fixed
                // TODO: optimize nested scopes
                parentScope.put(s, v);
            }
        }
    }

    public AutoautoPrimitive get(String s) {
        if(this.variables.containsKey(s)) return this.variables.get(s).value;
        else if(parentScope != null) return parentScope.get(s);
        else return null;
    }

    private StoredAutoautoValue getStored(String s) {
        if(this.variables.containsKey(s)) return this.variables.get(s);
        else if(parentScope != null) return parentScope.getStored(s);
        else return null;
    }

    public void systemSet(String s, AutoautoPrimitive v) {
        StoredAutoautoValue value = getStored(s);

        if(value == null) {
            value = new StoredAutoautoValue(v);
            value.systemManaged = true;
            this.variables.put(s, value);
        } else {
            if(!value.systemManaged) {
                throw new IllegalArgumentException("Variable " + s + " is not a system variable");
            }
            if(value.readOnly) {
                throw new IllegalArgumentException("Variable " + s + " is a read-only variable");
            }
            //if it's in this scope...
            if(this.variables.containsKey(s)) {
                //... set it
                value.value = v;
                this.variables.put(s, value);
            } // if not
            else {
                //... propagate up. This gets inefficient with a large number of nested scopes; should be fine for now, but should be fixed
                // TODO: optimize nested scopes
                parentScope.systemSet(s, v);
            }
        }
    }
}

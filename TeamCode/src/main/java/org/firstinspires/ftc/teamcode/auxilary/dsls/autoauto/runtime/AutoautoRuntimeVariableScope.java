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
    public HashMap<String, AutoautoPrimitive> variables;

    @Nullable
    public AutoautoRuntimeVariableScope parentScope;

    public AutoautoRuntimeVariableScope() {
        this.variables = new HashMap<String, AutoautoPrimitive>();
    }
    public AutoautoRuntimeVariableScope(AutoautoRuntimeVariableScope parentScope) {
        this.parentScope = parentScope;
        this.variables = new HashMap<String, AutoautoPrimitive>();
    }

    public void put(String s, AutoautoPrimitive v) {
        AutoautoPrimitive value = get(s);

        if(value == null) {
            this.variables.put(s, v);
        } else {
            if(value.systemManaged) {
                throw new IllegalArgumentException("Variable " + s + " is a system variable");
            } else if(value.readOnly) {
                throw new IllegalArgumentException("Variable " + s + " is a read-only variable");
            };
            //if it's in this scope...
            if(this.variables.containsKey(s)) {
                //... set it
                this.variables.put(s, v);
            } // if not
            else {
                //... propagate up. This gets inefficient with a large number of nested scopes; should be fine for now, but should be fixed
                // TODO: optimize nested scopes
                parentScope.put(s, v);
            }
        }
    }

    public AutoautoPrimitive get(String s) {
        if(this.variables.containsKey(s)) return this.variables.get(s);
        else if(parentScope != null) return parentScope.get(s);
        else return null;
    }

    public void systemSet(String s, AutoautoPrimitive v) {
        AutoautoPrimitive value = get(s);

        if(value == null) {
            v.systemManaged = true;
            this.variables.put(s, v);
        } else {
            if(!value.systemManaged) {
                throw new IllegalArgumentException("Variable " + s + " is not a system variable");
            }
            //if it's in this scope...
            if(this.variables.containsKey(s)) {
                //... set it
                v.systemManaged = true;
                this.variables.put(s, v);
            } // if not
            else {
                //... propagate up. This gets inefficient with a large number of nested scopes; should be fine for now, but should be fixed
                // TODO: optimize nested scopes
                parentScope.systemSet(s, v);
            }
        }
    }
}

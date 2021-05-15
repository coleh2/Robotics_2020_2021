package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.VariableStore;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class VariableReference extends Value {
    String name;
    public VariableReference(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void loop() {
        this.returnValue = runtimeVariableStore.get(this.name);
    }

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }

    public String toString() {
        return "var<" + this.name + ">";
    }
}

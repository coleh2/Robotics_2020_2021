package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.VariableStore;

public class VariableReference extends Value {
    String name;
    public VariableReference(String n) {
        this.name = n;
    }

    public void loop() {
        this.returnValue = runtimeVariableStore.get(this.name);
    }

    public String toString() {
        return "var<" + this.name + ">";
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public class VariableReference extends AutoautoValue {
    String name;

    public AutoautoPrimitive resolvedValue;
    private Location location;
    private AutoautoRuntimeVariableScope scope;

    public VariableReference(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void loop() {
        this.resolvedValue = scope.get(this.name);
    }

    public AutoautoPrimitive getResolvedValue() {
        return resolvedValue;
    }

    @Override
    public String getString() {
        return resolvedValue.getString();
    }

    public String toString() {
        return "var<" + this.name + ">";
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
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

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgramElement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public class BooleanValue extends AutoautoPrimitive {
    public boolean value;

    private Location location;
    private AutoautoRuntimeVariableScope scope;

    public BooleanValue(boolean value) {
        this.value = value;
    }
    public void loop() {}

    public boolean getBoolean() {
        return value;
    }

    public String toString() {
        return this.value + "";
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

    @Override
    public String getString() {
        return value + "";
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgramElement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.jetbrains.annotations.NotNull;

public class AutoautoString extends AutoautoPrimitive {
    public String value;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public AutoautoString(String value) {
        this.value = value;
    }
    public void loop() {}

    @Override
    public AutoautoPrimitive getResolvedValue() {
        return this;
    }
    @NotNull
    public String toString() {
        return value;
    }

    public String getString() {
        return value;
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

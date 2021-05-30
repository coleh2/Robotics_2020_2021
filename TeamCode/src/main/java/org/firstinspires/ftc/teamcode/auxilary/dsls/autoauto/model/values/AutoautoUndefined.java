package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public class AutoautoUndefined extends AutoautoPrimitive {
    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return null;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(Location location) {

    }

    @Override
    public String getString() {
        return null;
    }
}

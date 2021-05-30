package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public class UnitValue extends NumericValue {
    Location location;
    AutoautoRuntimeVariableScope scope;
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

    public static enum UnitType { TIME, DISTANCE };

    public UnitType unitType;
    public long baseAmount;
    public String unit;

    public UnitValue(long baseAmount, String unit) {
        super(baseAmount);
        this.baseAmount = baseAmount;
        this.unit = unit;
        if(unit.startsWith("ticks") || unit.startsWith("rots") || ((unit.charAt(0) == 'h' || unit.charAt(0) == 'v') && unit.substring(1).startsWith("ticks"))) {
            this.unitType = UnitType.DISTANCE;
        } else {
            this.unitType = UnitType.TIME;
        }

        if(unit.startsWith("ms")) {
            this.baseAmount *= 1;
            unitType = UnitType.TIME;
        } else if(unit.startsWith("s")) {
            this.baseAmount *= 1000;
            unitType = UnitType.TIME;
        } else if (unit.startsWith("m")) {
            this.baseAmount *= 60 * 1000;
            unitType = UnitType.TIME;
        } else if(unit.startsWith("h")) {
            this.baseAmount *= 60 * 60 * 1000;
            unitType = UnitType.TIME;
        } else if (unit.startsWith("d")) {
            this.baseAmount *= 24 * 60 * 60 * 1000;
            unitType = UnitType.TIME;
        }

        if(unit.startsWith("ticks")) {
            this.baseAmount *= 1;
            unitType = UnitType.DISTANCE;
        } else if(unit.startsWith("rots")) {
            this.baseAmount *= 135;
            unitType = UnitType.DISTANCE;
        } else if((unit.charAt(0) == 'h' || unit.charAt(0) == 'v') && unit.substring(1).startsWith("ticks")) {
            this.baseAmount *= 1;
            unitType = UnitType.DISTANCE;
        }
    }

    public String getString() {
        return this.baseAmount +
                ((unitType == UnitType.TIME) ? "ms" : "ticks");
    }

    @NonNull
    public String toString() {
        return this.baseAmount +
                ((unitType == UnitType.TIME) ? "ms" : "ticks");
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import androidx.annotation.NonNull;

public class UnitValue extends Value {
    public static enum UnitType { TIME, DISTANCE };

    public UnitType unitType;
    public long baseAmount;
    public String unit;

    public UnitValue(long baseAmount, String unit) {
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

    public UnitValue(String src) {
        String timeNumber = "";
        int unitIndex = 0;

        for(char c : src.toCharArray()) {
            unitIndex++;

            if(Character.isDigit(c) || c == '.') timeNumber += c;
            else break;
        }

        float timeParsedMs = Float.parseFloat(timeNumber);

        this.unit = src.substring(unitIndex - 1);

        if(unit.startsWith("ms")) {
            timeParsedMs *= 1;
            unitType = UnitType.TIME;
        } else if(unit.startsWith("s")) {
            timeParsedMs *= 1000;
            unitType = UnitType.TIME;
        } else if (unit.startsWith("m")) {
            timeParsedMs *= 60 * 1000;
            unitType = UnitType.TIME;
        } else if(unit.startsWith("h")) {
            timeParsedMs *= 60 * 60 * 1000;
            unitType = UnitType.TIME;
        } else if (unit.startsWith("d")) {
            timeParsedMs *= 24 * 60 * 60 * 1000;
            unitType = UnitType.TIME;
        }

        if(unit.startsWith("ticks")) {
            timeParsedMs *= 1;
            unitType = UnitType.DISTANCE;
        } else if(unit.startsWith("rots")) {
            timeParsedMs *= 135;
            unitType = UnitType.DISTANCE;
        } else if((unit.charAt(0) == 'h' || unit.charAt(0) == 'v') && unit.substring(1).startsWith("ticks")) {
            timeParsedMs *= 1;
            unitType = UnitType.DISTANCE;
        }

        this.baseAmount = (long)timeParsedMs;
        this.returnValue = new float[] { timeParsedMs };
    }

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }

    @NonNull
    public String toString() {
        return this.baseAmount +
                ((unitType == UnitType.TIME) ? "ms" : "ticks");
    }
}

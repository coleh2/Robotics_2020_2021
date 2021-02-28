package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import androidx.annotation.NonNull;

public class TimeUnit extends Value {
    public long ms;
    public String unit;

    public TimeUnit(String src) {
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
        } else if(unit.startsWith("s")) {
            timeParsedMs *= 1000;
        } else if (unit.startsWith("m")) {
            timeParsedMs *= 60 * 1000;
        } else if(unit.startsWith("h")) {
            timeParsedMs *= 60 * 60 * 1000;
        } else if (unit.startsWith("d")) {
            timeParsedMs *= 24 * 60 * 60 * 1000;
        }

        this.ms = (long)timeParsedMs;
        this.returnValue = new float[] { timeParsedMs };
    }

    @NonNull
    public String toString() {
        return this.ms + "ms";
    }
}

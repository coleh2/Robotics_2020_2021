package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import androidx.annotation.NonNull;

public class TimeUnit extends Value {
    public long ms;
    public String unit;

    public float returnValue;

    public TimeUnit(String src) {
        String timeNumber = "";
        int unitIndex = 0;

        for(char c : src.toCharArray()) {
            unitIndex++;

            if(Character.isDigit(c) || c == '.') timeNumber += c;
            else break;
        }

        float timeParsedMs = Float.parseFloat(timeNumber);

        this.unit = src.substring(unitIndex);

        if(src.startsWith("ms", unitIndex)) {
            timeParsedMs *= 1;
        } else if(src.startsWith("s", unitIndex)) {
            timeParsedMs *= 1000;
        } else if (src.startsWith("m", unitIndex)) {
            timeParsedMs *= 60 * 1000;
        } else if(src.startsWith("h", unitIndex)) {
            timeParsedMs *= 60 * 60 * 1000;
        } else if (src.startsWith("d", unitIndex)) {
            timeParsedMs *= 24 * 60 * 60 * 1000;
        }

        this.ms = (long)timeParsedMs;
        this.returnValue = (float)timeParsedMs;
    }

    @NonNull
    public String toString() {
        return this.ms + "ms";
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.HashMap;

public class VariableStore {
    public HashMap<String, float[]> variables;

    public VariableStore() {
        this.variables = new HashMap<String, float[]>();
    }

    public void put(String s, float[] f) {
        this.variables.put(s, f);
    }

    public float[] get(String s) {
        if(this.variables.containsKey(s)) return this.variables.get(s);

        FeatureManager.logger.log("[AUTOAUTO WARNING] Undefined varitable `" + s + "` requested; defaulting to 0");
        return new float[] {0};
    }
}

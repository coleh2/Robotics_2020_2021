package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.VariableReference;

public class AutoautoTelemetry {
    String programJson;
    VariableReference[] variables;

    public void setProgramJson(String programJson) {
        this.programJson = programJson;
    }

    public void setVariables(VariableReference[] variables) {
        this.variables = variables;
    }
}

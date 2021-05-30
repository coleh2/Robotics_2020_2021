package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.VariableReference;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class LetStatement extends Statement {
    public String variable;

    public AutoautoValue value;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public LetStatement(VariableReference var, AutoautoValue val) {
        this.variable = var.getName();
        this.value = val;
    }

    public LetStatement(String var, AutoautoValue val) {
        this.variable = var;
        this.value = val;
    }

    public void init() {
        this.value.init();
    }

    public void loop() {
        this.value.loop();
        this.scope.put(variable, value.getResolvedValue());
    }

    public String toString() {
        return "let " + this.variable + " = " + this.value.toString();
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

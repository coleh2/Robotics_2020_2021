package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.BooleanOperator;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.BooleanValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class IfStatement extends Statement {
    BooleanOperator conditional;
    Statement subject;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public IfStatement(AutoautoValue v, Statement s) {
        conditional = new BooleanOperator(v, new BooleanValue(false), "!=");
        subject = s;
    }

    public void init() {
        conditional.init();

        subject.init();
    }

    public void loop() {
        conditional.loop();
        if(conditional.getBoolean() == true) subject.loop();
    }
    public String toString() {
        return "if (" + conditional.toString() + ") " + subject.toString();
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        conditional.setScope(scope);
        subject.setScope(scope);
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

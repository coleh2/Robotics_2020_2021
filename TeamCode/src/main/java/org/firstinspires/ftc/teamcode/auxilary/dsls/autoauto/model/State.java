package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoCallableValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoPrimitive;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoUndefined;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.AutoautoValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoSystemVariableNames;
import org.jetbrains.annotations.NotNull;

public class State implements AutoautoProgramElement {
    public Statement[] statements;
    public AutoautoRuntimeVariableScope scope;
    public Location location;

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        for(Statement p : this.statements) p.setScope(scope);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public State(Statement[] statements) {
        this.statements = statements;
    }

    @NotNull
    public String toString() {
        StringBuilder statementsStr = new StringBuilder();
        for(Statement statement : statements) {
            statementsStr.append(statement.toString() + ", ");
        }
        return statementsStr.toString();
    }

    public void stepInit() {
        for(Statement s : statements) {
            if(s != null) s.stepInit();
        }
        //stop when new steps start
        ((AutoautoCallableValue)scope.get("stopDrive")).call(new AutoautoPrimitive[0]);
    }

    public void init() {
        for(Statement s : statements) {
            if(s != null) s.init();
        }
    }

    public void loop() {
        this.returnValue = new AutoautoUndefined();
        for(Statement s : statements) {
            if(!this.scope.get(AutoautoSystemVariableNames.STATEPATH_NAME).getString().equals(this.location.statepath)) break;
            if(((NumericValue)this.scope.get(AutoautoSystemVariableNames.STATE_NUMBER)).getFloat() != this.location.stateNumber) break;
            if(s != null) s.loop();

            //break if there's a return statement
            if(returned) break;
        }
    }

    //for if it's being used as a function body
    private AutoautoPrimitive returnValue;
    private boolean returned;
    public AutoautoPrimitive getReturnValue() {
        return returnValue;
    }
    public void setReturnValue(AutoautoPrimitive returnValue) {
        this.returnValue = returnValue;
        this.returned = true;
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoSystemVariableNames;
import org.jetbrains.annotations.NotNull;

public class SkipStatement extends Statement {
    public int delta;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public SkipStatement(int delta) {
        this.delta = delta;
    }
    public void loop() {
        int currentState = (int)((NumericValue)scope.get(AutoautoSystemVariableNames.STATE_NUMBER)).getFloat();
        int stateCount = (int)((NumericValue)scope.get(AutoautoSystemVariableNames.STATE_COUNT_OF_PREFIX + location.statepath)).getFloat();

        scope.systemSet(AutoautoSystemVariableNames.STATE_NUMBER, new NumericValue((currentState + delta) % stateCount));
    }
    @NotNull
    public String toString() {
        return "skip " + delta;
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

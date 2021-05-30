package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoSystemVariableNames;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Statepath implements AutoautoProgramElement {
    public State[] states;
    private int oldCurrentState;

    Location location;
    AutoautoRuntimeVariableScope scope;

    public String name;
    public AutoautoProgram program;

    public Statepath(State[] states, String name) {
        this.states = states;
        this.oldCurrentState = -1;
        this.name = name;
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        for(State s : states) s.setScope(scope);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;

        Location childLocation = location.clone();
        childLocation.statepath = this.name;
        for(State s : states) s.setLocation(childLocation);
    }

    public void init() {
        for(State s : this.states) s.init();
        this.scope.systemSet(AutoautoSystemVariableNames.STATE_COUNT_OF_PREFIX + name, new NumericValue(this.states.length));
    }

    public void stepInit() {
        for(State s : this.states) s.stepInit();
    }

    public void loop() {
        int currentState = (int)((NumericValue)(scope.get(AutoautoSystemVariableNames.STATE_NUMBER))).getFloat();

        //if steps have changed, init the new one
        if(currentState != this.oldCurrentState) {
            this.states[currentState].stepInit();
            this.oldCurrentState = currentState;
        }

        this.states[currentState].loop();
    }

    @NotNull
    public String toString() {
        StringBuilder statesStr = new StringBuilder();
        for(State state : states) {
            statesStr.append("\n    " + state.toString() + ";");
        }
        return statesStr.toString();
    }
}

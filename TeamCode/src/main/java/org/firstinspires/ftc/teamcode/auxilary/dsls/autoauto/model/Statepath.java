package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Statepath {
    public State[] states;
    public int currentState;
    private int oldCurrentState;

    public AutoautoProgram program;

    public Statepath(String src, AutoautoProgram program) {
        this.currentState = 0;
        this.oldCurrentState = -1;
        this.program = program;

        String[] stateSources = ParserTools.groupAwareSplit(src, ';');

        this.states = new State[stateSources.length];

        for(int i = stateSources.length - 1; i >= 0; i--) {
            String tr = stateSources[i].trim();
            if(tr.equals("")) {
                FeatureManager.logger.log("[AUTOAUTO ERROR] Empty state! Defaulting to `next`");
                this.states[i] = new State("next", program, this);
                continue;
            }
            states[i] = new State(tr, program, this);
        }
    }

    public void init() {
        for(State s : this.states) s.init();
    }

    public void stepInit() {
        for(State s : this.states) s.stepInit();
    }

    public void loop() {
        //if steps have changed, init the new one
        if(this.currentState != this.oldCurrentState) this.states[this.currentState].stepInit();

        this.states[this.currentState].loop();
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

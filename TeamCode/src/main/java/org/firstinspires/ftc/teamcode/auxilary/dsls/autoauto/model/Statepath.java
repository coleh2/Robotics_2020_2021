package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Statepath {
    public State[] states;
    public int currentState;

    public Statepath(String src) {
        this.currentState = 0;

        String[] stateSources = ParserTools.groupAwareSplit(src, ';');

        this.states = new State[stateSources.length];

        for(int i = stateSources.length - 1; i >= 0; i--) {
            String tr = stateSources[i].trim();
            if(tr.equals("")) {
                FeatureManager.logger.log("[AUTOAUTO ERROR] Empty state! Defaulting to `next`");
                this.states[i] = new State("next");
                continue;
            }
            states[i] = new State(tr);
        }
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

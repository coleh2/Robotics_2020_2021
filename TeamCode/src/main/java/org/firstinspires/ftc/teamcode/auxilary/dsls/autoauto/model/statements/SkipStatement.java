package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.jetbrains.annotations.NotNull;

public class SkipStatement extends Statement {
    public int delta;
    public SkipStatement() {}
    public SkipStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        this.delta = Integer.parseInt(src.substring("skip ".length()));
    }
    public SkipStatement(AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
    }
    public void loop() {
        int stateCount = this.statepath.states.length;
        this.statepath.currentState = (this.statepath.currentState + delta) % stateCount;
    }
    @NotNull
    public String toString() {
        return "skip " + delta;
    }
}

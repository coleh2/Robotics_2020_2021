package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;

public class NextStatement extends SkipStatement {

    public NextStatement() {
        super(1);
    }
    public NextStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        this.delta = 1;
    }
    public String toString() {
        return "next";
    }
}

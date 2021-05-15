package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;

public class GotoStatement extends Statement {
    String gotoPath;

    public GotoStatement(String path) {
        this.gotoPath = path;
    }

    public GotoStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        this.gotoPath = src.substring("goto ".length()).trim();
    }

    public void loop() {
        this.program.setCurrentPath(this.gotoPath);
    }

    public String toString() {
        return "goto " + this.gotoPath;
    }
}

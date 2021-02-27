package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

public class GotoStatement extends Statement {
    String gotoPath;

    public GotoStatement(String src) {
        this.gotoPath = src.substring("goto ".length()).trim();
    }
}

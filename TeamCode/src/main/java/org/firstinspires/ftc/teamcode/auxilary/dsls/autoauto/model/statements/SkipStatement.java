package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

public class SkipStatement extends Statement {
    public int delta;
    public SkipStatement() {}
    public SkipStatement(String src) {
        this.delta = Integer.parseInt(src.substring("skip ".length()));
    }
    public String toString() {
        return "skip " + delta;
    }
}

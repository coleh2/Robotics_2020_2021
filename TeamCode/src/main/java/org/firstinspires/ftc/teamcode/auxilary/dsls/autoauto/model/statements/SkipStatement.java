package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.jetbrains.annotations.NotNull;

public class SkipStatement extends Statement {
    public int delta;
    public SkipStatement() {}
    public SkipStatement(String src) {
        this.delta = Integer.parseInt(src.substring("skip ".length()));
    }
    @NotNull
    public String toString() {
        return "skip " + delta;
    }
}

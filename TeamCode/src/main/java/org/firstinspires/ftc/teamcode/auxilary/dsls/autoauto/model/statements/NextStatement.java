package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

public class NextStatement extends SkipStatement {
    public NextStatement(String src) {
        this.delta = 1;
    }
    public String toString() {
        return "next";
    }
}

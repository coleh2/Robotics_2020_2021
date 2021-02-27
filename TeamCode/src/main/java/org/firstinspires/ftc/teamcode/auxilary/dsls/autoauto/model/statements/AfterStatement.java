package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class AfterStatement extends Statement {
    TimeUnit wait;
    Statement action;

    public AfterStatement(String src) {
        src = src.substring("after ".length());

        int afterTimeUnit = src.indexOf(' ');

        this.wait = new TimeUnit(src.substring(0, afterTimeUnit));
        this.action = Statement.createProperStatementType(src.substring(afterTimeUnit + 1));
    }

    @NotNull
    public String toString() {
        return "after " + this.wait.toString() + " " + this.action.toString();
    }
}

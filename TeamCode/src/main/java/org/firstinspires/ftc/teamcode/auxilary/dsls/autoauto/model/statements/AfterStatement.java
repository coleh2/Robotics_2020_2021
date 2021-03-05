package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.TimeUnit;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

public class AfterStatement extends Statement {
    TimeUnit wait;
    Statement action;

    private long stepStart = 0;

    public AfterStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        src = src.substring("after ".length());

        int afterTimeUnit = src.indexOf(' ');

        this.wait = new TimeUnit(src.substring(0, afterTimeUnit));
        this.action = Statement.createProperStatementType(src.substring(afterTimeUnit + 1), program, statepath, state);
    }

    @NotNull
    public String toString() {
        return "after " + this.wait.toString() + " " + this.action.toString();
    }

    @Override
    public void stepInit() {
        this.stepStart = System.currentTimeMillis();
    }

    public void loop() {
        FeatureManager.logger.log("now: " + System.currentTimeMillis() + "\n    " + wait.ms);
        if(System.currentTimeMillis() >= stepStart + wait.ms) action.loop();
    }
}

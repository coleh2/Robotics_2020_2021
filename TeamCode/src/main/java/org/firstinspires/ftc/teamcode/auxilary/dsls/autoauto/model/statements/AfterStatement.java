package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.UnitValue;
import org.jetbrains.annotations.NotNull;

public class AfterStatement extends Statement {
    UnitValue wait;
    Statement action;

    private long stepStartTime = 0;
    private int stepStartTick;

    public AfterStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        src = src.substring("after ".length());

        int afterTimeUnit = src.indexOf(' ');

        this.wait = new UnitValue(src.substring(0, afterTimeUnit));
        this.action = Statement.createProperStatementType(src.substring(afterTimeUnit + 1), program, statepath, state);
    }

    @NotNull
    public String toString() {
        return "after " + this.wait.toString() + " " + this.action.toString();
    }

    @Override
    public void init() {
        action.init();

        wait.setRuntimeReferences(program.autoautoRuntime.functions, program.autoautoRuntime.variables);
        wait.init();
    }

    @Override
    public void stepInit() {
        this.stepStartTime = System.currentTimeMillis();
        this.stepStartTick = (int)program.autoautoRuntime.functions.get("getTicks", 0).call(new float[0][0])[0];
    }

    public void loop() {
        if(wait.unitType == UnitValue.UnitType.TIME) {
            if (System.currentTimeMillis() >= stepStartTime + wait.baseAmount) action.loop();
        } else if(wait.unitType == UnitValue.UnitType.DISTANCE) {
            int tarTicks = (int) wait.baseAmount;
            int ticksReferPoint = stepStartTick;
            int cTicks = (int)program.autoautoRuntime.functions.get(
                wait.unit.equals("ticks") ? "getTicks" :
                    wait.unit.equals("hticks") ? "getHorizontalTicks" :
                        wait.unit.equals("vticks") ? "getVerticalTicks" : "ERROR BAD BAD UNIT"
                , 0).call(new float[0][0])[0];
            
            if(Math.abs(cTicks - ticksReferPoint) >= Math.abs(tarTicks)) action.loop();
        }
    }
}

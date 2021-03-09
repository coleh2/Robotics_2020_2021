package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.Value;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class LetStatement extends Statement {
    public String variable;

    public Value value;

    public LetStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);

        src = src.substring("let ".length()).trim();

        //first word is the variable
        this.variable = src.substring(0, src.indexOf(' '));

        int equalsIndex = src.indexOf('=');
        if(equalsIndex == -1) {
            FeatureManager.logger.log("[AUTOAUTO ERROR] No equals sign in `let` statement!");
            equalsIndex = src.indexOf(' ');
        }


        this.value = Value.createProperValueType(src.substring(equalsIndex + 1).trim());
    }


    public void init() {
        this.value.setRuntimeReferences(program.autoautoRuntime.functions, program.autoautoRuntime.variables);
        this.value.init();
    }

    public void loop() {
        this.value.loop();
        this.program.autoautoRuntime.variables.put(this.variable, this.value.getReturnValue());
    }

    public String toString() {
        return "let " + this.variable + " = " + this.value.toString();
    }
}

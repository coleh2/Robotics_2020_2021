package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.FunctionCall;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

public class FunctionCallStatement extends Statement {
    public FunctionCall function;

    public FunctionCallStatement(FunctionCall f) {
        this.function = f;
    }

    public FunctionCallStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        this.function = new FunctionCall(src);
    }

    @NotNull
    public String toString() {
        return this.function.toString();
    }

    public void loop() {
        function.loop();
    }

    @Override
    public void init() {
        this.function.setRuntimeReferences(this.program.autoautoRuntime.functions, this.program.autoautoRuntime.variables);
        this.function.init();
    }

    @Override
    public void stepInit() {
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.FunctionCall;
import org.jetbrains.annotations.NotNull;

public class FunctionCallStatement extends Statement {
    public FunctionCall function;

    public FunctionCallStatement(String src) {
        this.function = new FunctionCall(src);
    }

    @NotNull
    public String toString() {
        return this.function.toString();
    }
}

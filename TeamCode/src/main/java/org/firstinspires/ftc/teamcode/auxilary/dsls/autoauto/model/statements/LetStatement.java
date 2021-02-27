package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.Value;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

public class LetStatement extends Statement {
    public String variable;

    public Value value;

    public LetStatement(String src) {
        src = src.substring("let ".length()).trim();

        //first word is the variable
        this.variable = src.substring(0, src.indexOf(' '));

        int equalsIndex = src.indexOf('=');
        if(equalsIndex == -1) FeatureManager.logger.log("[AUTOAUTO ERROR] No equals sign in `let` statement!");
        equalsIndex = src.indexOf(' ');

        this.value = Value.createProperValueType(src.substring(equalsIndex).trim());
    }
}

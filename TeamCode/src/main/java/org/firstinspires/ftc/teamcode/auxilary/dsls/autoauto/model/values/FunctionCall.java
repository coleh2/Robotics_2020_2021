package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.Value;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

public class FunctionCall extends Value {
    public Value[] args;
    public String name;

    public FunctionCall(String src) {
        this.name = "";
        for(char c : src.toCharArray()) {
            if(Character.isAlphabetic(c)) this.name += c;
            else break;
        }

        if(src.indexOf('(') < 0 || src.indexOf(')') < 0) FeatureManager.logger.log("[AUTOAUTO ERROR] Could not parse function call `" + src + "`");

        String argumentsSrc = src.substring(src.indexOf('(') + 1, src.lastIndexOf(')'));
        String[] argsSources = ParserTools.groupAwareSplit(argumentsSrc, ',', false);

        this.args = new Value[argsSources.length];

        for(int i = argsSources.length - 1; i >= 0; i--) {
            this.args[i] = Value.createProperValueType(argsSources[i]);
        }
    }

    @NotNull
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name + "(");
        for(Value arg : args) {
            if(arg == null) str.append("<null>");
            str.append(arg.toString() + ", ");
        }
        str.append(")");
        return str.toString();
    }
}

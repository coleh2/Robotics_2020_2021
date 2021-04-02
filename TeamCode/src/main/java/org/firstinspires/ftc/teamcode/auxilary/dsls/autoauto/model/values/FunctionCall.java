package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.Value;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.FunctionStore;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

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
        String[] argsSources = ParserTools.groupAwareSplit(argumentsSrc, ',', true);

        this.args = new Value[argsSources.length];

        for(int i = argsSources.length - 1; i >= 0; i--) {
            this.args[i] = Value.createProperValueType(argsSources[i]);
        }
    }

    public void init() {
        for(int i = args.length - 1; i >= 0; i--) {
            if(this.args[i] == null) {
                FeatureManager.logger.log("Null argument " + i + " in " + this.toString());
                continue;
            }
            this.args[i].setRuntimeReferences(this.runtimeFunctionStore, this.runtimeVariableStore);
            this.args[i].init();
        }
    }

    public void loop() {
        if(runtimeFunctionStore != null) {
            Function fn = runtimeFunctionStore.get(this);
            if(fn == null) FeatureManager.logger.add("[AUTOAUTO ERROR] Unknown function `" + this.name + "` with argument count + `" + this.args.length + "`");

            if(this.args.length > 0 && this.args[0] instanceof ArrayLiteral) {
                resolveWithArray(fn);
                return;
            }

            float[][] argsResolved = new float[this.args.length][];

            for(int i = this.args.length - 1; i >= 0; i--) {
                this.args[i].loop();
                argsResolved[i] = this.args[i].getReturnValue();
                if(argsResolved[i].length == 0) FeatureManager.logger.log("[AA ERROR] " + this.args[i].toString() + "is 0-len");
            }

            this.returnValue = fn.call(argsResolved);
        };

    }

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }

    public void resolveWithArray(Function fn) {
        float[][] arrg = new float[1][args[0].returnValue.length];

        args[0].loop();

        for(int i = args[0].returnValue.length - 1; i >= 0; i--) arrg[0][i] = args[0].returnValue[i];

        fn.call(arrg);
    }

    @NotNull
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name + "(");
        for(Value arg : args) {
            if(arg == null) str.append("<null>");
            else str.append(arg.toString() + ", ");
        }
        str.append(")");
        return str.toString();
    }
}

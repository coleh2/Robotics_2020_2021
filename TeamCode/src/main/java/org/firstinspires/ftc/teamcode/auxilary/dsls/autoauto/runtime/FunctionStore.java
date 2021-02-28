package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.FunctionCall;

import java.util.HashMap;

public class FunctionStore {
    public HashMap<String, Function> variables;

    public FunctionStore() {
        this.variables = new HashMap<String, Function>();
    }

    public void put(String n, int a, Function f) {
        this.variables.put(n + "(" + a + ")", f);
    }
    public void put(FunctionCall c, Function f) {
        String s = c.name + "(" + c.args.length + ")";
        this.variables.put(s, f);
    }

    public Function get(FunctionCall c) {
        String s = c.name + "(" + c.args.length + ")";

        if(this.variables.containsKey(s)) return this.variables.get(s);
        else return null;
    }
}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.Arrays;

public class ArrayLiteral extends Value {
    Value[] elems;
    public ArrayLiteral(String src) {
        src = src.substring(1, src.length() - 1);

        String[] elemSources = ParserTools.groupAwareSplit(src, ',', true);
        elems = new Value[elemSources.length];

        this.returnValue = new float[elemSources.length];

        for(int i = elemSources.length - 1; i >= 0; i--) {
            elems[i] = Value.createProperValueType(elemSources[i]);
        }
    }

    public void init() {
        for(int i = elems.length - 1; i >= 0; i--) {
            assert this.elems[i] != null;
            this.elems[i].runtimeFunctionStore = this.runtimeFunctionStore;
            this.elems[i].runtimeVariableStore = this.runtimeVariableStore;
            this.elems[i].init();
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(Value v : this.elems) s.append(v.toString()).append(",");
        return s + "]";
    }
}

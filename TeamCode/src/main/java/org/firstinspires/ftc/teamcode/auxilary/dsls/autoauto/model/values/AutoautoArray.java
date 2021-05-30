package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

import java.util.Arrays;

public class AutoautoArray extends AutoautoPrimitive {
    public AutoautoValue[] elems;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public AutoautoArray(AutoautoValue[] e) {
        this.elems = e;
    }

    public void init() {
        for(int i = elems.length - 1; i >= 0; i--) {
            assert this.elems[i] != null;
            this.elems[i].init();
        }
    }

    @Override
    public void loop() {
        for(int i = 0; i < elems.length; i++) {
            elems[i].loop();
        }
    }

    @Override
    public String getString() {
        String[] strElems = new String[elems.length];
        for(int i = 0; i < strElems.length; i++) {
            strElems[i] = elems[i].getString();
        }
        return Arrays.toString(strElems);
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for(AutoautoValue v : this.elems) s.append(v.toString()).append(",");
        return s + "]";
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return this.scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        for(AutoautoValue v : elems) v.setScope(scope);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}

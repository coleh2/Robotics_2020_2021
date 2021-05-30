package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

public class FunctionCall extends AutoautoValue {
    public AutoautoValue[] args;
    public String name;


    public AutoautoPrimitive returnValue;

    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public FunctionCall(String n, AutoautoValue[] a) {
        this.name = n;
        this.args = a;
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
        for(int i = args.length - 1; i >= 0; i--) this.args[i].setScope(scope);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public void init() {
        for(int i = args.length - 1; i >= 0; i--) {
            this.args[i].init();
        }
    }

    public void loop() {
        AutoautoValue val = scope.get(name);
        if(val == null) FeatureManager.logger.add("[AUTOAUTO ERROR] `" + this.name + "` is undefined" + AutoautoProgram.formatStack(location));

        if(!(val instanceof AutoautoCallableValue)) FeatureManager.logger.add("[AUTOAUTO ERROR] `" + name + "` is not a function" + AutoautoProgram.formatStack(location));

        AutoautoCallableValue fn = (AutoautoCallableValue)val;

        //set the location that it's being called from. for error logging purposes
        if(val.getLocation() == null) val.setLocation(this.location);

        for(int i = this.args.length - 1; i >= 0; i--) this.args[i].loop();

        AutoautoPrimitive[] argsResolved = new AutoautoPrimitive[args.length];
        for(int i = this.args.length - 1; i >= 0; i--) argsResolved[i] = this.args[i].getResolvedValue();

        this.returnValue = fn.call(argsResolved);
    }

    @Override
    public String getString() {
        return returnValue.getString();
    }

    public AutoautoPrimitive getResolvedValue() {
        return this.returnValue;
    }

    @NotNull
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name + "(");
        for(AutoautoValue arg : args) {
            if(arg == null) str.append("<null>");
            else str.append(arg.toString() + ", ");
        }
        str.append(")");
        return str.toString();
    }
}

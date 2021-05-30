package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public class FunctionDefinition extends AutoautoPrimitive implements AutoautoCallableValue {
    public State body;
    private AutoautoRuntimeVariableScope scope;
    private Location location;

    public FunctionDefinition(State body) {
        this.body = body;
    }

    @Override
    public AutoautoPrimitive call(AutoautoPrimitive[] args) {
        AutoautoRuntimeVariableScope callScope = new AutoautoRuntimeVariableScope(scope);
        callScope.put("args", new AutoautoArray(args));

        body.setScope(scope);

        body.loop();

        return body.getReturnValue();
    }

    @Override
    public AutoautoRuntimeVariableScope getScope() {
        return scope;
    }

    @Override
    public void setScope(AutoautoRuntimeVariableScope scope) {
        this.scope = scope;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void init() {
        body.init();
    }

    @Override
    public void loop() {
    }

    @Override
    public String getString() {
        return "<function>";
    }
}

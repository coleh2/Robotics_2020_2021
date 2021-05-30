package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgramElement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Location;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.FunctionStore;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntimeVariableScope;

public abstract class AutoautoValue implements AutoautoProgramElement {

    public boolean readOnly;
    public boolean systemManaged;
    public boolean tunable;

    public abstract AutoautoPrimitive getResolvedValue();

    public void init() {}
    public void loop() {}

    public abstract String getString();
}

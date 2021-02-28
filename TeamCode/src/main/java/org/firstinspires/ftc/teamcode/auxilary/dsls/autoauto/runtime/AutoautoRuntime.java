package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

public class AutoautoRuntime {
    public VariableStore variables;
    public FunctionStore functions;
    public AutoautoProgram program;

    public AutoautoRuntime(AutoautoProgram program, MovementManager drive, ManipulationManager manip, SensorManager sense) {
        this.variables = new VariableStore();
        this.functions = new FunctionStore();

        this.program = program;
        this.program.autoautoRuntime = this;

        Function[] builtInFunctions = RobotFunctionLoader.loadFunctions(drive, manip, sense);
        RobotFunctionLoader.addFunctionsToStore(builtInFunctions, functions);

        ManagerDeviceScanner.scan(variables, manip, sense);

        this.program.init();
        this.program.stepInit();
    }

    public void loop() {
        this.program.loop();
    }


}

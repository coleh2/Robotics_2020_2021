package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

public class AutoautoRuntime {
    public AutoautoRuntimeVariableScope globalScope;
    public AutoautoProgram program;

    public AutoautoRuntime(AutoautoProgram program, MovementManager drive, ManipulationManager manip, SensorManager sense, ImuManager imu) {
        this.globalScope = new AutoautoRuntimeVariableScope();

        this.program = program;
        this.program.setScope(globalScope);

        NativeRobotFunction[] builtInFunctions = RobotFunctionLoader.loadFunctions(drive, manip, sense, imu);
        RobotFunctionLoader.addFunctionsToStore(builtInFunctions, globalScope);

        ManagerDeviceScanner.scan(globalScope, manip, sense);

        this.program.init();
        this.program.stepInit();
    }

    public void loop() {
        this.program.loop();
    }


}

package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntime;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

public class Autoauto {
    public static AutoautoRuntime executeAutoautoProgram(String programSource, MovementManager drive, ManipulationManager manip, SensorManager sense) {
        AutoautoProgram parsedProgram = new AutoautoProgram(programSource);
        return new AutoautoRuntime(parsedProgram, drive, manip, sense);
    }
}

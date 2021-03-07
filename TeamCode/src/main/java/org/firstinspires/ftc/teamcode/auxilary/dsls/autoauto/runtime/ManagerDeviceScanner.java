package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

public class ManagerDeviceScanner {
    public static void scan(VariableStore store, ManipulationManager manip, SensorManager sense) {
        for(int i = manip.servoNames.length - 1; i >= 0; i--) store.put(manip.servoNames[i], new float[] {i});
        for(int i = manip.crservoNames.length - 1; i >= 0; i--) store.put(manip.crservoNames[i], new float[] {i});
        for(int i = manip.motorNames.length - 1; i >= 0; i--) store.put(manip.motorNames[i], new float[] {i});

        for(int i = sense.sensorNames.length - 1; i >= 0; i--) store.put(sense.sensorNames[i], new float[] {i});
    }
}

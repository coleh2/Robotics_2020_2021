package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.ControlModel;
import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.GamepadState;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyGamepad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableModelInitialStateControlUsageTest {


    public static class VariableTestControlMap extends ControlMap {
        public static String getter = "getVariable(foo)";
    }

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel(new VariableTestControlMap());

        FeatureManager.logger.add("Getter Function: " + mdl.get("getter").toString());

        float currentValue = mdl.get("getter").res(new GamepadState(new DummyGamepad()))[0];

        assertEquals(0f, currentValue, 0f);
    }
}

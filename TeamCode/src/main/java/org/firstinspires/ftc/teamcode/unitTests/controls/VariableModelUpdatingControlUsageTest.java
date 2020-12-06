package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.firstinspires.ftc.teamcode.auxilary.GamepadState;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyGamepad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableModelUpdatingControlUsageTest {


    public static class VariableTestControlMap extends ControlMap {
        public static String setter = "setVariable(foo, leftTrigger)";
        public static String getter = "getVariable(foo)";
    }

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel(new VariableTestControlMap());

        FeatureManager.logger.add("Getter Function: " + mdl.get("getter").toString());
        FeatureManager.logger.add("Setter Function: " + mdl.get("setter").toString());

        float value;
        DummyGamepad dummy = new DummyGamepad();

        value = mdl.get("getter").res(new GamepadState(dummy))[0];
        assertEquals(0f, value, 0f);

        dummy.left_trigger = 1f;
        value = mdl.get("getter").res(new GamepadState(dummy))[0];
        assertEquals(1f, value, 0f);
    }
}

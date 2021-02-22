package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableSettingControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "setVariable(foo, vector3(leftStickY, leftStickX, RIGHT_STICK_X))";
        String expectedOutput = "SET_VARIABLE(foo, VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, RIGHT_STICK_X))";

        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());

        assertEquals(expectedOutput, control.toString());
    }
}

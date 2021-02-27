package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.ControlModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableGettingControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "vector3(leftStickY, leftStickX, getVariable(foo))";
        String expectedOutput = "VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, GET_VARIABLE(foo))";

        System.out.println("Parsing " + testContent);
        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());
        System.out.println("Expected Result: " + expectedOutput);

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, control.toString());
    }
}

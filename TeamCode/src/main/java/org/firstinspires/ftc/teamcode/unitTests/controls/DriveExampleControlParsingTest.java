package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.ControlModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DriveExampleControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "vector3(leftStickY, leftStickX, rightStickX)";
        String expectedOutput = "VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, RIGHT_STICK_X)";

        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, control.toString());
    }
}


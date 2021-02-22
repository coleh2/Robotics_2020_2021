package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArbitraryNameControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "vector3(leftStickY, leftStickX, arbitraryName)";
        String expectedOutput = "VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, arbitraryName)";

        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, control.toString());
    }
}


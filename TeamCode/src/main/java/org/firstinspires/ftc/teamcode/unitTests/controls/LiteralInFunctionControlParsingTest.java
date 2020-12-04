package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.junit.Test;

public class LiteralInFunctionControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "vector3(leftStickY, leftStickX, 0.3)";
        String expectedOutput = "VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, 0.3)";

        System.out.println("Parsing " + testContent);
        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());
        System.out.println("Expected Result: " + expectedOutput);

        //exit with a non-zero code to indicate an error
        if(!control.toString().equals(expectedOutput)) System.exit(1);
    }
}


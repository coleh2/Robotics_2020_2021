package org.firstinspires.ftc.teamcode.unitTests.controls;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComplexControlParsingTest {

    @Test
    public void runTest() {
        ControlModel mdl = new ControlModel();
        String testContent = "ternary( or(or(leftStickY, leftStickX), rightStickX),vector3(deadzone(leftStickY, 0.1), deadzone(leftStickX, 0.05), rightStickX),vector3(scale(deadzone(gamepad2LeftStickY, 0.1), 0.2), scale(deadzone(gamepad2LeftStickX, 0.05), 0.2), scale(gamepad2RightStickX, 0.2)))";
        String expectedOutput = "VECTOR3(LEFT_STICK_Y, LEFT_STICK_X, RIGHT_STICK_X)";

        ControlModel.Control control = new ControlModel.Control(testContent,mdl);
        System.out.println("Result: " + control.toString());

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, control.toString());
    }
}


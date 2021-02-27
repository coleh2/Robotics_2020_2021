package org.firstinspires.ftc.teamcode.unitTests.largerModules;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyDcMotor;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyGamepad;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OmniDrivingFullProcessTest {
    public static class DummyBasicDrivingControlMap extends ControlMap {
        public static String drive = "vector3(leftStickY, leftStickX, rightStickX)";
    }

    MovementManager movementManager;
    InputManager inputManager;

    @Test
    public void runTest() {
        DummyDcMotor fr = new DummyDcMotor();
        DummyDcMotor fl = new DummyDcMotor();
        DummyDcMotor bl = new DummyDcMotor();
        DummyDcMotor br = new DummyDcMotor();

        DummyGamepad gamepad = new DummyGamepad();

        double[] motorPowers, expectedMotorPowers;

        motorPowers = new double[4];

        movementManager = new MovementManager(fr, fl, bl, br);
        inputManager = new InputManager(gamepad, new DummyBasicDrivingControlMap());



        //both sticks at center, which is the default -> robot should be still (all motors 0 power)
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {0.0, 0.0, 0.0, 0.0};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push left stick forwards -> robot moves forwards (all powers are expected to be 0.5 bc of normalization);
        gamepad.left_stick_y = 1.0f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {0.5, 0.5, 0.5, 0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push left stick backwards -> robot moves backwards (all powers are expected to be -0.5 bc of normalization);
        gamepad.left_stick_y = -1.0f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {-0.5, -0.5, -0.5, -0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push left stick left -> robot moves left
        gamepad.left_stick_y = 0f;
        gamepad.left_stick_x = -1f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {0.5, -0.5, 0.5, -0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push left stick right -> robot moves right
        gamepad.left_stick_x = 1f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {-0.5, 0.5, -0.5, 0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push right stick right -> robot turns right
        gamepad.left_stick_x = 0f;
        gamepad.right_stick_x = 1f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {-0.5, 0.5, 0.5, -0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));


        //push right stick left -> robot turns left
        gamepad.right_stick_x = -1f;
        inputManager.update();
        movementManager.driveOmni(inputManager.getVector("drive"));
        updateMotorPowersArray(motorPowers);
        expectedMotorPowers = new double[] {0.5, -0.5, -0.5, 0.5};

        assertEquals(Arrays.toString(expectedMotorPowers), Arrays.toString(motorPowers));
    }

    private void updateMotorPowersArray(double[] powers) {
        movementManager.driveOmni(inputManager.getVector("drive"));
        powers[0] = movementManager.frontRight.getPower();
        powers[1] = movementManager.frontLeft.getPower();
        powers[2] = movementManager.backLeft.getPower();
        powers[3] = movementManager.backRight.getPower();
    }
}

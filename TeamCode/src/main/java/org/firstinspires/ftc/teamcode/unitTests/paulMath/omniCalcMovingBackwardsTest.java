package org.firstinspires.ftc.teamcode.unitTests.paulMath;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class omniCalcMovingBackwardsTest {
    @Test
    public void runTest() {
        final float[] movementVhr = new float[] {-1f, 0f, 0f};

        final String expectedOutput = "[-1.0, -1.0, -1.0, -1.0]";

        System.out.println("Calculating input " + Arrays.toString(movementVhr) + "...");

        String result = Arrays.toString(PaulMath.omniCalc(movementVhr[0], movementVhr[1], movementVhr[2]));


        System.out.println("Result: " + result);

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, result);
    }
}
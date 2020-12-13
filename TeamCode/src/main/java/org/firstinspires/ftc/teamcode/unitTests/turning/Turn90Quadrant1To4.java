package org.firstinspires.ftc.teamcode.unitTests.turning;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.junit.Test;

import static org.firstinspires.ftc.teamcode.auxilary.PaulMath.proportionalPID;
import static org.junit.Assert.assertEquals;

public class Turn90Quadrant1To4 {
    @Test
    public void runTest() {
        FeatureManager.debug = true;

        float current = 135;
        float target = 45;

        //specify 0.5 as coefficient; don't clip result
        float result = proportionalPID(current, target, 0.5f, Float.MIN_VALUE, Float.MAX_VALUE);

        assertEquals(-90*0.5,
                result, 0f);
    }
}

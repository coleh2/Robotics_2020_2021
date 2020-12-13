package org.firstinspires.ftc.teamcode.unitTests.turning;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.junit.Test;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.auxilary.PaulMath.proportionalPID;
import static org.junit.Assert.assertEquals;

public class Turn180Difference {
    @Test
    public void runTest() {
        FeatureManager.debug = true;

        float current = 0;
        float target = 180;

        //specify 0.5 as coefficient; don't clip result
        float result = proportionalPID(current, target, 0.5f, Float.MIN_VALUE, Float.MAX_VALUE);

        //abs val; either +180 or -180 would be acceptable results
        result = Math.abs(result);

        assertEquals(180*0.5,
                result, 0f);
    }
}

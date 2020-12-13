package org.firstinspires.ftc.teamcode.unitTests.turning;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.junit.Test;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.auxilary.PaulMath.proportionalPID;
import static org.junit.Assert.assertEquals;

public class Turn358Difference {
    @Test
    public void runTest() {
        FeatureManager.debug = true;

        float current = 0;
        float target = 358;

        //specify 0.5 as coefficient; don't clip result
        float result = proportionalPID(current, target, 0.5f, Float.MIN_VALUE, Float.MAX_VALUE);

        //should detect lowest amount, only turn -2 instead of +358
        assertEquals(-2*0.5,
                result, 0f);
    }
}

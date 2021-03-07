package org.firstinspires.ftc.teamcode.unitTests.autoauto;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.UnitValue;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimeValueParserTest {
    @Test
    public void runTest() {
        UnitValue t = new UnitValue("2s");
        FeatureManager.logger.log("2s-- unit: " + t.unit);
        assertEquals("s", t.unit);
        assertEquals("2000ms", t.toString());
    }
}

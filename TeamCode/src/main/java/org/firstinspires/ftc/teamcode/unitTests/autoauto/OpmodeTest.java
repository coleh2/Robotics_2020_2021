package org.firstinspires.ftc.teamcode.unitTests.autoauto;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.Autoauto;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntime;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyColorRangeSensor;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyCrServo;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyDcMotor;
import org.firstinspires.ftc.teamcode.unitTests.dummy.DummyNormalizedColorSensor;
import org.junit.Test;

import java.util.Arrays;

public class OpmodeTest {

    public static final int TEST_DURATION_MS = 100;

    @Test
    public void runTest() {
        String programSource = "#init: resetEncoders(), runUsingEncoders(), after 1ms next; let colour = getHSL(sensor4), next; driveOmni([0, 1, 0]), after 1s goto path2 #path2: log(3.0)";
        MovementManager driver = new MovementManager(new DummyDcMotor(), new DummyDcMotor(), new DummyDcMotor(), new DummyDcMotor());
        ManipulationManager manip = new ManipulationManager(
                new CRServo[] {
                        new DummyCrServo()
                },
                new String[] {
                        "shooterArm"
                },
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        new DummyDcMotor(), new DummyDcMotor(), new DummyDcMotor(), new DummyDcMotor()
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheelRight",
                        "flywheelLeft"
                }
        );
        DummyNormalizedColorSensor dummySense = new DummyNormalizedColorSensor();
        SensorManager sense = new SensorManager(new DummyNormalizedColorSensor[] { dummySense }, new String[] { "sensor4" }, new DummyColorRangeSensor[] {}, new String [] {});

        AutoautoRuntime runner = Autoauto.executeAutoautoProgram(programSource, driver, manip, sense);

        dummySense.setNormalizedColors(new NormalizedRGBA());
        dummySense.rgba.alpha = 1;
        dummySense.rgba.red = 255;
        dummySense.rgba.green = 255;
        dummySense.rgba.blue = 255;

        FeatureManager.logger.log("Starting State: \n    On path #" + runner.program.getCurrentPathName() +"\n    In State " + runner.program.currentPath.currentState);

        long start = System.currentTimeMillis();
        while(start + TEST_DURATION_MS > System.currentTimeMillis()) {
            runner.loop();
            FeatureManager.logger.log("Running State: \n    On path #" + runner.program.getCurrentPathName() +"\n    In State " + runner.program.currentPath.currentState+"\n    Time: " + (System.currentTimeMillis() - start) + "ms");
        }

        FeatureManager.logger.log("Colour" + Arrays.toString(runner.variables.get("colour")));

        FeatureManager.logger.log("Final State: \n    On path #" + runner.program.getCurrentPathName() +"\n    In State " + runner.program.currentPath.currentState);
    }
}

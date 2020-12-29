package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.managers.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp(group = "testOpmodes")
public class ImuTester extends OpMode {

    ImuManager imu;
    InputManager input;
    MovementManager driver;

    public void init() {
        FeatureManager.logger.setBackend(telemetry.log());
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "br"),
                hardwareMap.get(DcMotor.class, "bl"));
        input = new InputManager(gamepad1, new BasicDrivingControlMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
    }

    public void loop() {

        if(input.getGamepad().a) {
            float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 45f);
            driver.driveOmni(new float[] {0,0,-proportional});
           // FeatureManager.logger.add("IMU Target: " + math.proportionalPID(imu.getOrientation().thirdAngle, 45));

        }
        if(input.gamepad.y){
            driver.stopDrive();
        }
        if(input.getGamepad().b) {
            float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 180f);
            driver.driveOmni(new float[] {0,0,-proportional});
          //  FeatureManager.logger.add("IMU Target: " + math.proportionalPID(imu.getOrientation().thirdAngle, 180));

        }
        if(input.getGamepad().x) {
            float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 90f);
            driver.driveOmni(new float[] {0,0,-proportional});
          //  FeatureManager.logger.add("IMU Target: " + math.proportionalPID(imu.getOrientation().thirdAngle, 90));


        }

        FeatureManager.logger.add("IMU Orientation: " + imu.getOrientation().thirdAngle);

        //  FeatureManager.logger.add("IMU Acceleration: " + imu.getLinearAcceleration().toString());
    }
}
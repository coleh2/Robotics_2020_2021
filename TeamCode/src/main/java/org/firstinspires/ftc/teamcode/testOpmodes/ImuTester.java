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

    float expected = 0f;
    float movement = 0f;

    public void loop() {

        imu.getPosition();

        if(input.getGamepad().dpad_up) expected = 90f;
        if(input.getGamepad().dpad_right) expected = 0f;
        if(input.getGamepad().dpad_down) expected = -90f;
        if(input.getGamepad().dpad_left) expected = 180f;

        float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, expected);
        driver.driveOmni(new float[] {0,0,-proportional});

        float[] manualDrivingControls = input.getVector("drive");
        if(manualDrivingControls[0] + manualDrivingControls[1] + manualDrivingControls[2] != 0) driver.driveOmni(manualDrivingControls);

        telemetry.addData("IMU Orientation: ", imu.getOrientation().thirdAngle);

        telemetry.addData("IMU Acceleration: ", imu.getLinearAcceleration().toString());
        telemetry.addData("IMU Position: ", imu.getPosition().toString());
    }
}
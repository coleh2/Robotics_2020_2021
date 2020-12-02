package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.managers.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp(group = "testOpmodes")
public class ImuTester extends OpMode {

    ImuManager imu;

    public void init() {
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));

        FeatureManager.logger = telemetry.log();
    }

    public void loop() {

        FeatureManager.logger.add("IMU Orientation: " + imu.getOrientation().toString());
        FeatureManager.logger.add("IMU Acceleration: " + imu.getLinearAcceleration().toString());
    }
}
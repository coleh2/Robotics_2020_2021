package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.JoshuaIsDeadzoneAndKilledJameControlMap;
import org.firstinspires.ftc.teamcode.managers.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp
public class Teleop extends OpMode {

    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    Servo grab;
    ImuManager imu;

    private static boolean toggleSpeed = false;

    public void init() {
        FeatureManager.logger.setBackend(telemetry.log());

        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "br"),
                hardwareMap.get(DcMotor.class, "bl"));
        input = new InputManager(gamepad1, new BasicDrivingControlMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));

       // driver.resetEncoders();
      //  driver.runUsingEncoders();


    }

    public void loop() {
        input.update();
        if(!input.getGamepad().left_bumper) {
            driver.driveOmni(input.getVector("drive"));
        } else {
            driver.driveOmniExponential(input.getVector("drive"));
        }
        if(input.getGamepad().dpad_up){
            driver.upScale();
        }
        if (input.getGamepad().dpad_down){
            driver.downScale();
        }


        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
                driver.frontRight.getCurrentPosition()+
                driver.backLeft.getCurrentPosition()+
                driver.backRight.getCurrentPosition())/4);

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());

        telemetry.addData("speed: ", driver.getScale());



        telemetry.addData("Orientation", imu.getOrientation().toString());
    }
}
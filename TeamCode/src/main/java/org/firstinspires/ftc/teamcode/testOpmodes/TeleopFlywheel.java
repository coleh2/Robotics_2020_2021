package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.managers.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp
public class TeleopFlywheel extends OpMode {

    InputManager input;
    ColorSensor sensor;
    Servo grab;
    ImuManager imu;
    ManipulationManager limbs;

    private static boolean toggleSpeed = false;

    public void init() {
        FeatureManager.logger.setBackend(telemetry.log());

//        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
//                hardwareMap.get(DcMotor.class, "fr"),
//                hardwareMap.get(DcMotor.class, "br"),
//                hardwareMap.get(DcMotor.class, "bl"));
        input = new InputManager(gamepad1, new BasicDrivingControlMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        limbs = new ManipulationManager(
                new CRServo[] {},
                new String[] {},
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "flywheelRight"),
                        hardwareMap.get(DcMotor.class, "flywheelLeft")
                },
                new String[] {
                        "flywheelRight",
                        "flywheelLeft"
                }
            );

       // driver.resetEncoders();
      //  driver.runUsingEncoders();


    }

    public void loop() {
//        input.update();
//        if(!input.getGamepad().left_bumper) {
//            driver.driveOmni(input.getVector("drive"));
//        } else {
//            driver.driveOmniExponential(input.getVector("drive"));
//        }
//        if(input.getGamepad().dpad_up){
//            driver.upScale();
//        }
//        if (input.getGamepad().dpad_down){
//            driver.downScale();
//        }


        if(input.getGamepad().right_trigger > 0.5){
            limbs.setMotorPower("flywheelRight", -1f);
        } else
        if(input.getGamepad().right_bumper){
            limbs.setMotorPower("flywheelRight", 1f);
        } else {
            limbs.setMotorPower("flywheelRight", 0f);
        }

        if(input.getGamepad().left_trigger > 0.5){
            limbs.setMotorPower("flywheelLeft", 1f);
        } else
        if(input.getGamepad().left_bumper){
            limbs.setMotorPower("flywheelLeft", -1f);
        } else {
            limbs.setMotorPower("flywheelLeft", 0f);
        }

        telemetry.addData("flywheelLeft Power", limbs.getMotorPower("flywheelLeft"));
        telemetry.addData("flywheelRight Power", limbs.getMotorPower("flywheelRight"));

        telemetry.addData("Orientation", imu.getOrientation().toString());
    }
}
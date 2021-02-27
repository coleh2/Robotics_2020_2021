package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;


@TeleOp
public class TeleopShoot extends OpMode {

    InputManager input;
//    MovementManager driver;
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
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm")
                },
                new String[] {
                        "shooterArm"
                },
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "drum"),
                        hardwareMap.get(DcMotor.class, "intake"),
                        hardwareMap.get(DcMotor.class, "flywheelRight"),
                        hardwareMap.get(DcMotor.class, "flywheelLeft")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheelRight",
                        "flywheelLeft"
                }
            );

       // driver.resetEncoders();
      //  driver.runUsingEncoders();


    }

    float directionDrum;

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




        if(input.getGamepad().right_trigger > 0.1){
            limbs.setMotorPower("flywheelRight", -1);
            limbs.setMotorPower("flywheelLeft", 1);
        } else {
            limbs.setMotorPower("flywheelRight", 0);
            limbs.setMotorPower("flywheelLeft", 0);
        }

        if(input.getGamepad().right_bumper) {
            limbs.setServoPower("shooterArm", 0);
        } else {
            limbs.setServoPower("shooterArm", 0.7);
        }

        if(input.getGamepad().a) directionDrum = -1;
        else directionDrum = 1;

        if(input.getGamepad().left_trigger > 0.1){
            limbs.setMotorPower("drum", -1*directionDrum);
            limbs.setMotorPower("intake", 0.5);
        } else {
            if (input.getGamepad().left_bumper) {
                limbs.setMotorPower("drum", -1);
            } else {
                limbs.setMotorPower("drum", 0);
            }
            if(input.getGamepad().b) {
                limbs.setMotorPower("intake", 0.5);
            } else {
                limbs.setMotorPower("intake", 0);
            }
        }




//        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
//        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
//        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
//        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
//        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
//                driver.frontRight.getCurrentPosition()+
//                driver.backLeft.getCurrentPosition()+
//                driver.backRight.getCurrentPosition())/4);
//
//        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
//        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());
//
//        telemetry.addData("FR Power: ", driver.frontRight.getPower());
//        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());
//
//        telemetry.addData("BL Power: ", driver.backLeft.getPower());
//        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());
//
//        telemetry.addData("BR Power: ", driver.backRight.getPower());
//        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());

//        telemetry.addData("speed: ", driver.getScale());

        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Right Power", limbs.getMotorPower("flywheelRight"));
        telemetry.addData("Flywheel Left Power", limbs.getMotorPower("flywheelLeft"));
        telemetry.addData("Orientation", imu.getOrientation().toString());
    }
}
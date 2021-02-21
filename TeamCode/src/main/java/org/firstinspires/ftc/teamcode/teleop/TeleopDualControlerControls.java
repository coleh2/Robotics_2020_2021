package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.DualControllerContolMap;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ShootingTogglesControlMap;
import org.firstinspires.ftc.teamcode.managers.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

import java.util.Arrays;


@TeleOp
public class TeleopDualControlerControls extends OpMode {

    InputManager input;
    MovementManager driver;
    ManipulationManager limbs;
    ColorSensor sensor;
    Servo grab;
    ImuManager imu;

    boolean errorLogged = false;

    private static boolean toggleSpeed = false;

    public void init() {
        try {
        FeatureManager.logger.setBackend(telemetry.log());



        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "br"),
                hardwareMap.get(DcMotor.class, "bl"));

        input = new InputManager(gamepad1, gamepad2, new DualControllerContolMap());

        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));

        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm"),
                        hardwareMap.get(CRServo.class, "wobbleArmLeft"),
                        hardwareMap.get(CRServo.class, "wobbleArmRight")
                },
                new String[] {
                        "shooterArm",
                        "wobbleArmLeft",
                        "wobbleArmRight"
                },
                new Servo[] {

                        hardwareMap.get(Servo.class, "wobbleGrabLeft"),
                        hardwareMap.get(Servo.class, "wobbleGrabRight")
                },
                new String[] {
                        "wobbleGrabLeft",
                        "wobbleGrabRight"
                },
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

        } catch (Exception e) {
            

        }
    }

    public void loop() {
        try {

            input.update();



        driver.driveOmni((input.getVector("drive")));


        limbs.setMotorPower("intake", 0.5*input.getScalar("intake"));
        limbs.setServoPower("wobbleArmRight", input.getScalar("wobbleArmRight"));
        limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGrabRight"));
        limbs.setServoPower("wobbleArmLeft", input.getScalar("wobbleArmLeft"));
        limbs.setServoPosition("wobbleGrabLeft", input.getScalar("wobbleGrabLeft"));
        limbs.setMotorPower("drum", input.getScalar("drum"));
        limbs.setMotorPower("flywheelRight", input.getScalar("flywheelRight"));
        limbs.setMotorPower("flywheelLeft", input.getScalar("flywheelLeft"));
        limbs.setServoPower("shooterArm", input.getScalar("shooterArm"));



//        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
//        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
//        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
//        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
//        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
//                driver.frontRight.getCurrentPosition()+
//                driver.backLeft.getCurrentPosition()+
//                driver.backRight.getCurrentPosition())/4);

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());

        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Right Power", limbs.getMotorPower("flywheelRight"));
        telemetry.addData("Flywheel Left Power", limbs.getMotorPower("flywheelLeft"));
        telemetry.addData("Orientation", imu.getOrientation().toString());

        telemetry.addData("speed: ", driver.getScale());

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);

        telemetry.addData("controls://fullIntake:", input.getControl("fullIntake").toString());
        telemetry.addData("controls://lt:", input.getScalar("lt"));
        } catch (Exception e) {
            if(!errorLogged) {

                FeatureManager.logger.add(input.getControl("drive").toString());
            }
        }
    }
}
package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ShootingTogglesControlMap;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp
public class TeleopControls extends OpMode {

    InputManager input;
    MovementManager driver;
    ManipulationManager limbs;
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
        input = new InputManager(gamepad1, new ShootingTogglesControlMap());
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

        driver.resetEncoders();
        driver.runUsingEncoders();


    }

    public void loop() {
        input.update();

        driver.driveOmni((input.getVector("drive")));

        limbs.setMotorPower("intake", 0.5*input.getScalar("fullIntake"));
        limbs.setServoPower("wobbleArmRight", input.getScalar("wobbleGraber"));
        limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGraberNegative"));
        limbs.setServoPower("wobbleArmLeft", -input.getScalar("wobbleGraber"));
        limbs.setServoPosition("wobbleGrabLeft", -input.getScalar("wobbleGraberNegative"));

        if(input.getGamepad().right_trigger > 0.1) {
            limbs.setMotorPower("flywheelRight", -1);
            limbs.setMotorPower("flywheelLeft", 1);
        } else {
            limbs.setMotorPower("flywheelRight", 0);
            limbs.setMotorPower("flywheelLeft", 0);
        }

        if(input.getGamepad().a) {
            limbs.setServoPower("shooterArm", 0.325);
        } else {
            limbs.setServoPower("shooterArm", 0.65);
        }

        limbs.setMotorPower("drum", (input.getGamepad().right_trigger > 0.1 || input.getScalar("fullIntake") == 1) ? -1 : 0);

        if(input.getGamepad().dpad_up) {
            limbs.setMotorPower("drum", -1);
        } else if(input.getGamepad().dpad_down) {
            limbs.setMotorPower("drum", 1);
        }



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

//        telemetry.addData("speed: ", driver.getScale());

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);

        telemetry.addData("controls://fullIntake:", input.getControl("fullIntake").toString());
        telemetry.addData("controls://lt:", input.getScalar("lt"));

    }
}
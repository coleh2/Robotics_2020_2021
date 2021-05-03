package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.OneControllerContolMap;
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
        input = new InputManager(gamepad1, new OneControllerContolMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm")
                },
                new String[] {
                        "shooterArm",
                },
                new Servo[] {
                        hardwareMap.get(Servo.class, "wobbleArmRight"),
                        hardwareMap.get(Servo.class, "wobbleArmLeft"),
                        hardwareMap.get(Servo.class, "wobbleGrabRight"),
                        hardwareMap.get(Servo.class, "wobbleGrabLeft")
                },
                new String[] {
                        "wobbleArmRight","wobbleArmLeft" , "wobbleGrabRight","wobbleGrabLeft"
                },
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "drum"),
                        hardwareMap.get(DcMotor.class, "intake"),
                        hardwareMap.get(DcMotor.class, "flywheel"),
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel"
                }

        );

        driver.resetEncoders();
        driver.runUsingEncoders();
        limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
        limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
        limbs.resetEncoders("flywheel");
        limbs.runUsingEncoders("flywheel");


    }

    float speed = 0.8f;
    boolean upPressed = false;
    boolean downPressed = false;

    public void loop() {
        input.update();

        driver.driveOmni((input.getVector("drive")));

        limbs.setMotorPower("intake", 0.8*input.getScalar("intake"));

        limbs.setMotorPower("drum", input.getScalar("drum"));
        limbs.setMotorPower("flywheel", speed*input.getScalar("flywheel"));
        limbs.setServoPower("shooterArm", input.getScalar("shooterArm"));

        limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGrabRight"));
        limbs.setServoPosition("wobbleGrabLeft", input.getScalar("wobbleGrabLeft"));
        limbs.setServoPosition("wobbleArmRight", input.getScalar("wobbleArmRight"));
        limbs.setServoPosition("wobbleArmLeft", input.getScalar("wobbleArmLeft"));


        if(input.getGamepad().dpad_right) {
            if(!upPressed) {
                speed = speed + 0.01f;
                upPressed = true;
            }
        } else upPressed = false;

        if (input.getGamepad().dpad_left) {
            if(!downPressed) {
                speed = speed - 0.01f;
                downPressed = true;
            }
        } else downPressed = false;

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
        telemetry.addData("Flywheel Power", limbs.getMotorPower("flywheel"));
        telemetry.addData("Orientation", imu.getOrientation().toString());

        telemetry.addData("speed: ", speed);

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);

//        telemetry.addData("controls://fullIntake:", input.getControl("fullIntake").toString());
//        telemetry.addData("controls://lt:", input.getScalar("lt"));

    }
}
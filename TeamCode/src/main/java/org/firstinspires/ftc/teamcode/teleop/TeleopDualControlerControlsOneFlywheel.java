package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.auxilary.EncodedMotor;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.DualControllerContolMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp
public class TeleopDualControlerControlsOneFlywheel extends OpMode {

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

                },
                new String[] {

                },
                new Servo[] {
                        hardwareMap.get(Servo.class, "wobbleArmRight"),
                        hardwareMap.get(Servo.class, "wobbleArmLeft"),
                        hardwareMap.get(Servo.class, "wobbleGrabRight"),
                        hardwareMap.get(Servo.class, "wobbleGrabLeft"),
                        hardwareMap.get(Servo.class, "shoulderLeft"),
                        hardwareMap.get(Servo.class, "shoulderRight"),
                        hardwareMap.get(Servo.class, "shooterStop"),
                        hardwareMap.get(Servo.class, "shooterArm")
                },
                new String[] {
                        "wobbleArmRight","wobbleArmLeft" , "wobbleGrabRight","wobbleGrabLeft", "shoulderLeft", "shoulderRight", "shooterStop", "shooterArm"
                },
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "drum"),
                        hardwareMap.get(DcMotor.class, "intake"),
                        new EncodedMotor(hardwareMap.get(DcMotor.class, "flywheel"), 1000),
                        hardwareMap.get(DcMotor.class, "spinner")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel",
                        "spinner"
                }

        );


            limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
            limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);

//            limbs.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);

        } catch (Exception e) {
            

        }
    }
    float target = 0.1f;
    boolean uped = false;
    float speed = 1f;
    boolean upPressed = false;
    boolean downPressed = false;

    public void loop() {
        try {

            input.update();

            driver.driveOmni((input.getVector("drive")));

            limbs.setMotorPower("intake", 0.8*input.getScalar("intake"));

            limbs.setMotorPower("drum", input.getScalar("drum"));
            limbs.setMotorPower("spinner", input.getScalar("spinner"));

            limbs.setMotorPower("flywheel", speed*input.getScalar("flywheel"));
            limbs.setServoPosition("shooterArm", input.getScalar("shooterArm"));

            limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGrabRight"));
            limbs.setServoPosition("wobbleGrabLeft", input.getScalar("wobbleGrabLeft"));
            limbs.setServoPosition("wobbleArmRight", input.getScalar("wobbleArmRight"));
            limbs.setServoPosition("wobbleArmLeft", input.getScalar("wobbleArmLeft"));

            limbs.setServoPosition("shoulderLeft", input.getScalar("shoulderLeft"));
            limbs.setServoPosition("shoulderRight", input.getScalar("shoulderRight"));

            limbs.setServoPosition("shooterStop", input.getScalar("shooterStop"));


//            if(input.getGamepad().dpad_right) {
//                if(!upPressed) {
//                    speed = speed + 0.01f;
//                    upPressed = true;
//                }
//            } else upPressed = false;
//
//            if (input.getGamepad().dpad_left) {
//                if(!downPressed) {
//                    speed = speed - 0.01f;
//                    downPressed = true;
//                }
//            } else downPressed = false;


            telemetry.addData("Flywheel control", input.getControl("flywheel").toString());
            telemetry.addData("gamepad 2 triangle: ", input.gamepad2.y);
            telemetry.addData("Verticle Position", driver.getVerticalTicks());
            telemetry.addData("Position", driver.getTicks());

//            telemetry.addData("Horisontal Position", driver.getHorizontalTicks());

            telemetry.addData("wobbleGrabRight", limbs.getServo("wobbleGrabRight").getPosition());
            telemetry.addData("wobbleGrabLeft", limbs.getServo("wobbleGrabLeft").getPosition());
            telemetry.addData("wobbleArmRight", limbs.getServo("wobbleArmRight").getPosition());
            telemetry.addData("wobbleArmLeft", limbs.getServo("wobbleArmLeft").getPosition());



            telemetry.addData("FL Power: ", driver.frontLeft.getPower());
            telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());

        telemetry.addData("Flywheel Input", input.getScalar("flywheel"));

        telemetry.addData("Shooter Arm", limbs.getServoPosition("shooterArm"));

        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Power", limbs.getMotorPower("flywheel"));
        telemetry.addData("Flywheel Position", limbs.motors[2].getCurrentPosition());
        telemetry.addData("Orientation", imu.getOrientation().toString());

        telemetry.addData("shoulderLeft", limbs.getServoPosition("shoulderLeft"));
        telemetry.addData("shoulderRight", limbs.getServoPosition("shoulderRight"));

        telemetry.addData("shooterStop", limbs.getServoPosition("shooterStop"));
        telemetry.addData("FL ticks", driver.getTicks());

        telemetry.addData("speed: ", speed);

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);
        } catch (Exception e) {
            FeatureManager.logger.log(e.toString());
        }
    }
}
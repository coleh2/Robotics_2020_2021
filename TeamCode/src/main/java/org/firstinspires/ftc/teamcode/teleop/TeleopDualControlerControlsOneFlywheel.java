package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
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
                        hardwareMap.get(DcMotor.class, "flywheel")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel"
                }

        );


            limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
            limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
            limbs.resetEncoders("flywheel");
            limbs.runUsingEncoders("flywheel");
       // driver.resetEncoders();
      //  driver.runUsingEncoders();

        } catch (Exception e) {
            

        }
    }
    float target = 0.1f;
    boolean uped = false;
    float speed = 0.8f;
    boolean upPressed = false;
    boolean downPressed = false;

    public void loop() {
        try {

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

        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Power", limbs.getMotorPower("flywheel"));
        telemetry.addData("Orientation", imu.getOrientation().toString());

        telemetry.addData("speed: ", speed);

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);
        } catch (Exception e) {
            FeatureManager.logger.log(e.toString());
        }
    }
}
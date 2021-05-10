package org.firstinspires.ftc.teamcode.testOpmodes;

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
import org.firstinspires.ftc.teamcode.managers.OdometryManager;

import java.util.Arrays;


@TeleOp
public class TeleopDualControlerOdometryControls extends OpMode {

    InputManager input;
    OdometryManager roadrunner;
    ManipulationManager limbs;
    ColorSensor sensor;
    Servo grab;
    ImuManager imu;


    boolean errorLogged = false;

    private static boolean toggleSpeed = false;

    public void init() {
        try {
        FeatureManager.logger.setBackend(telemetry.log());

        roadrunner = new OdometryManager(hardwareMap);

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
                        hardwareMap.get(DcMotor.class, "flywheel"),
                        hardwareMap.get(DcMotor.class, "spinnyThingUpTop")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel",
                        "spinnyThingUpTop"
                }

        );


            limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
            limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
//            limbs.resetEncoders("flywheel");
//            limbs.runUsingEncoders("flywheel");
       // driver.resetEncoders();
      //  driver.runUsingEncoders();

        } catch (Exception e) {
            

        }
    }
    float target = 0.1f;
    boolean uped = false;

    public void loop() {
        try {

            input.update();

            roadrunner.driveOmni((input.getVector("drive")));

            limbs.setMotorPower("intake", 0.8*input.getScalar("intake"));

            limbs.setMotorPower("drum", input.getScalar("drum"));
            limbs.setMotorPower("flywheel", input.getScalar("flywheel"));
            limbs.setServoPower("shooterArm", input.getScalar("shooterArm"));

            limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGrabRight"));
            limbs.setServoPosition("wobbleGrabLeft", input.getScalar("wobbleGrabLeft"));
            limbs.setServoPosition("wobbleArmRight", input.getScalar("wobbleArmRight"));
            limbs.setServoPosition("wobbleArmLeft", input.getScalar("wobbleArmLeft"));

            telemetry.addData("Position garbage: ", Arrays.toString(roadrunner.getCurrentPosition()));

            telemetry.addData("wobbleGrabRight", limbs.getServo("wobbleGrabRight").getPosition());
            telemetry.addData("wobbleGrabLeft", limbs.getServo("wobbleGrabLeft").getPosition());
            telemetry.addData("wobbleArmRight", limbs.getServo("wobbleArmRight").getPosition());
            telemetry.addData("wobbleArmLeft", limbs.getServo("wobbleArmLeft").getPosition());

            telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
            telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
            telemetry.addData("Flywheel Power", limbs.getMotorPower("flywheel"));
            telemetry.addData("Orientation", imu.getOrientation().toString());

            telemetry.addData("left trigger: ", input.getGamepad().left_trigger);

            roadrunner.update();
        } catch (Exception e) {
            FeatureManager.logger.log(e.toString());
        }
    }
}
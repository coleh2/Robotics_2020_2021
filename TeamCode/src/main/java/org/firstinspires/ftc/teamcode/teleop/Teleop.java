package org.firstinspires.ftc.teamcode.teleop;

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
import org.firstinspires.ftc.teamcode.managers.MovementManager;

import java.util.Arrays;


@TeleOp
public class Teleop extends OpMode {

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
        input = new InputManager(gamepad1, new BasicDrivingControlMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm")
                },
                new String[] {
                        "shooterArm"
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
        driver.runWithOutEncoders();
        limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
        limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
//        driver.runUsingEncoders();
    }

    public void loop() {
        input.update();
        //gamepad 1 fast drive HIGH priortiy
        driver.driveOmni(input.getVector("drive"));
        //BOTH gamepad fly wheel privlidges = prioity
        if(input.getGamepad().right_trigger > 0.1 || input.getGamepad2().right_trigger > 0.1){
            limbs.setMotorPower("flywheelRight", -1);
            limbs.setMotorPower("flywheelLeft", 1);
        } else {
            limbs.setMotorPower("flywheelRight", 0);
            limbs.setMotorPower("flywheelLeft", 0);
        }
        //also giving both = prioity
        if(input.getGamepad().a) {
            limbs.setServoPower("shooterArm", 0);
        } else {
            limbs.setServoPower("shooterArm", 0.57);
        }
        //gamepad 1 drum, LOW priority
            //well actually, we want gamepads to be = unless 2 is spinning backwards
        if(input.getGamepad().dpad_up || input.getGamepad2().dpad_up){
            limbs.setMotorPower("drum", -0.6);
        } else if(input.getGamepad().dpad_down) {
            limbs.setMotorPower("drum", 0.6);
        } else if (input.getGamepad().left_trigger > 0.1 || input.getGamepad2().x) {
            limbs.setMotorPower("drum", -0.6);
        } else {
            limbs.setMotorPower("drum", 0);
        }
        //both =
        if (input.getGamepad().left_trigger > 0.1 || input.getGamepad().left_trigger > 0.1) {
            limbs.setMotorPower("intake", -0.8);
        } else {
            limbs.setMotorPower("intake", 0);
        }

        //wobble grabber thing - gamepad2 only?
        if (input.getGamepad2().left_bumper){
            limbs.setServoPosition("wobbleArmLeft", 0.086);
            limbs.setServoPosition("wobbleArmRight", 0.086);
            //45 degrees = ~0.065
        }else {
            limbs.setServoPosition("wobbleArmLeft", 0.055);
            limbs.setServoPosition("wobbleArmRight", 0.055);
        }
        if (input.getGamepad2().right_bumper){
            limbs.setServoPosition("wobbleGrabLeft", 1);
            limbs.setServoPosition("wobbleGrabRight", 1);
            //45 degrees = ~0.065
        }else {
            limbs.setServoPosition("wobbleGrabLeft", 0);
            limbs.setServoPosition("wobbleGrabRight", 0);
        }

        if(gamepad2.a){

        }

//        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
//        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
//        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
//        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
//        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
//                driver.frontRight.getCurrentPosition()+
//                driver.backLeft.getCurrentPosition()+
//                driver.backRight.getCurrentPosition())/4);

        telemetry.addData("ticks", Arrays.toString(driver.getMotorPositions()));

        telemetry.addData("FL Power: ", driver.frontLeft);
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

        telemetry.addData("controls:drive", input.getControl("drive").toString());
        telemetry.addData("controls:drive/result", Arrays.toString(input.getVector("drive")));

        telemetry.addData("speed: ", driver.getScale());

    }
}
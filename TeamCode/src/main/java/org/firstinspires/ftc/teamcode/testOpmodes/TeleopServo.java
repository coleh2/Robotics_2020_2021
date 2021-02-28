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
public class TeleopServo extends OpMode {

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
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm"),

                },
                new String[]
                        {"shooterArm"},
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
                        hardwareMap.get(DcMotor.class, "flywheelRight"),
                        hardwareMap.get(DcMotor.class, "flywheelLeft")
                },
                new String[] {
                        "flywheelRight",
                        "flywheelLeft"
                }
            );
        limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
        limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
       // driver.resetEncoders();
      //  driver.runUsingEncoders();


    }
 float target = 0.1f;
    boolean uped = false;
    public void loop() {
        input.update();
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

        if(input.getGamepad().a) {
            //limbs.setServoPosition("shooterArm", 0.7);
            limbs.setServoPower("shooterArm", 0.0);
        } else {
            limbs.setServoPower("shooterArm", 0.63);
        }


        if (input.getGamepad().x){
            limbs.setServoPosition("wobbleArmLeft", 0.2);
            limbs.setServoPosition("wobbleArmRight", 0.2);
            //45 degrees = ~0.065
        }else {
            limbs.setServoPosition("wobbleArmLeft", 0.05);
            limbs.setServoPosition("wobbleArmRight", 0.05);
        }
        if (input.getGamepad().b){
            limbs.setServoPosition("wobbleGrabLeft", 1);
            limbs.setServoPosition("wobbleGrabRight", 1);
            //45 degrees = ~0.065
        }else {
            limbs.setServoPosition("wobbleGrabLeft", 0);
            limbs.setServoPosition("wobbleGrabRight", 0);
        }

        if(input.getGamepad().dpad_up ){
            target+=0.0001;
        }
        if(input.getGamepad().dpad_down ){
            target-=0.0001;
        }



//        if(input.getGamepad().right_trigger > 0.5){
//            limbs.setMotorPower("flywheelRight", -1f);
//        } else
//        if(input.getGamepad().right_bumper){
//            limbs.setMotorPower("flywheelRight", 1f);
//        } else {
//            limbs.setMotorPower("flywheelRight", 0f);
//        }
//
//        if(input.getGamepad().left_trigger > 0.5){
//            limbs.setMotorPower("flywheelLeft", 1f);
//        } else
//        if(input.getGamepad().left_bumper){
//            limbs.setMotorPower("flywheelLeft", -1f);
//        } else {
//            limbs.setMotorPower("flywheelLeft", 0f);
//        }
//
//        telemetry.addData("flywheelLeft Power", limbs.getMotorPower("flywheelLeft"));
//        telemetry.addData("flywheelRight Power", limbs.getMotorPower("flywheelRight"));

        telemetry.addData("Orientation", imu.getOrientation().toString());
        telemetry.addData("Servo Power", limbs.getServoPower("shooterArm"));
        telemetry.addData("wobbleGrabRight", limbs.getServo("wobbleGrabRight").getPosition());
        telemetry.addData("wobbleGrabLeft", limbs.getServo("wobbleGrabLeft").getPosition());
        telemetry.addData("wobbleArmRight", limbs.getServo("wobbleArmRight").getPosition());
        telemetry.addData("wobbleArmLeft", limbs.getServo("wobbleArmLeft").getPosition());



    }
}
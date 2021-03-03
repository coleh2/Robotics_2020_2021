package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.dsls.controls.controlmaps.DualControllerContolMap;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


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
    float target = 0.1f;
    boolean uped = false;

    public void loop() {
        try {

            input.update();

            driver.driveOmni((input.getVector("drive")));

            limbs.setMotorPower("intake", 0.8*input.getScalar("intake"));

            limbs.setMotorPower("drum", input.getScalar("drum"));
            limbs.setMotorPower("flywheelRight", input.getScalar("flywheelRight"));
            limbs.setMotorPower("flywheelLeft", input.getScalar("flywheelLeft"));
            limbs.setServoPower("shooterArm", input.getScalar("shooterArm"));

            limbs.setServoPosition("wobbleGrabRight", input.getScalar("wobbleGrabRight"));
            limbs.setServoPosition("wobbleGrabLeft", input.getScalar("wobbleGrabLeft"));
            limbs.setServoPosition("wobbleArmRight", input.getScalar("wobbleArmRight"));
            limbs.setServoPosition("wobbleArmLeft", input.getScalar("wobbleArmLeft"));

//            if(input.getGamepad().a) {
//                //limbs.setServoPosition("shooterArm", 0.7);
//                limbs.setServoPower("shooterArm", 0.0);
//            } else {
//                limbs.setServoPower("shooterArm", 0.63);
//            }

//
//            if (input.getGamepad().x){
//                limbs.setServoPosition("wobbleArmLeft", 0.086);
//                limbs.setServoPosition("wobbleArmRight", 0.086);
//                //45 degrees = ~0.065
//            }else {
//                limbs.setServoPosition("wobbleArmLeft", 0.055);
//                limbs.setServoPosition("wobbleArmRight", 0.055);
//            }
//            if (input.getGamepad().b){
//                limbs.setServoPosition("wobbleGrabLeft", 1);
//                limbs.setServoPosition("wobbleGrabRight", 1);
//                //45 degrees = ~0.065
//            }else {
//                limbs.setServoPosition("wobbleGrabLeft", 0);
//                limbs.setServoPosition("wobbleGrabRight", 0);
//            }
//
//            if(input.getGamepad().dpad_up ){
//                target+=0.0001;
//            }
//            if(input.getGamepad().dpad_down ){
//                target-=0.0001;
//            }

//        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
//        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
//        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
//        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
//        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
//                driver.frontRight.getCurrentPosition()+
//                driver.backLeft.getCurrentPosition()+
//                driver.backRight.getCurrentPosition())/4);

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
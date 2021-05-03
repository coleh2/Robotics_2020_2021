package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.auxilary.EncodedMotor;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;


@TeleOp
public class TeleopMotorTest extends OpMode {

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
        EncodedMotor enc = new EncodedMotor(hardwareMap.get(DcMotor.class, "flywheelRight"), 2800);
        limbs = new ManipulationManager(
                new CRServo[] {},
                new String[] {},
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "flywheelRight"),
                        hardwareMap.get(DcMotor.class, "flywheelLeft")
                },
                new String[] {
                        "flywheelRight",
                        "flywheelLeft"
                }
            );

       // driver.resetEncoders();
      //  driver.runUsingEncoders();
        limbs.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    float speed = 0.1f;
    boolean upPressed = false;
    boolean downPressed = false;

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


        if(input.getGamepad().right_trigger > 0.5){
            limbs.setMotorPower("flywheelRight", -speed);
        } else
        if(input.getGamepad().right_bumper){
            limbs.setMotorPower("flywheelRight", speed);
        } else {
            limbs.setMotorPower("flywheelRight", 0f);
        }

        if(input.getGamepad().dpad_up) {
            if(!upPressed) {
                speed = speed + 0.01f;
                upPressed = true;
            }
        } else upPressed = false;

        if (input.getGamepad().dpad_down) {
            if(!downPressed) {
                speed = speed - 0.01f;
                downPressed = true;
            }
        } else downPressed = false;


//        telemetry.addData("flywheelLeft Power", limbs.getMotorPower("flywheelLeft"));
        telemetry.addData("flywheelRight Power", limbs.getMotorPower("flywheelRight"));
        telemetry.addData("speed: ", speed);
        telemetry.addData("Orientation", imu.getOrientation().toString());

    }
}
package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.BasicDrivingControlMap;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;


@TeleOp
public class TeleopFlywheel extends OpMode {

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
                new CRServo[] {},
                new String[] {},
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "flywheel"),
                },
                new String[] {
                        "flywheel"
                }
            );

       // driver.resetEncoders();
      //  driver.runUsingEncoders();


    }

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
            limbs.setMotorPower("flywheel", 0.3f);
        } else
        if(input.getGamepad().right_bumper){
            limbs.setMotorPower("flywheel", -0.3f);
        } else {
            limbs.setMotorPower("flywheel", 0f);
        }


        telemetry.addData("flywheel Power", limbs.getMotorPower("flywheel"));

        telemetry.addData("Orientation", imu.getOrientation().toString());
    }
}
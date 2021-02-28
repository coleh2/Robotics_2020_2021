package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.auxilary.*;

import java.util.ArrayList;
import java.util.Timer;

@Autonomous(group = "Step")
public class EncoderTest extends StepAuto {
    MovementManager driver;
    ManipulationManager limbs;
    ColorSensor sensorOne;
    ColorSensor sensorFour;
    ImuManager imu;
    int stepTime=0;




    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm")
                },
                new String[] {
                        "shooterArm"
                },
                new Servo[] {},
                new String[] {},
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
driver.setTargetPositions(0, 0, 0,0 );
        driver.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driver.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driver.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driver.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //driver.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driver.backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        sensorOne = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne"));
        sensorFour = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"));

    }



    public void loop() {
        switch(currentStep){
            case START:
                //Flywheels startup + set shooterArm
          //      limbs.setServoPower("shooterArm", 0.63);
              //  limbs.setMotorPower("flywheelRight", -1);
              //  limbs.setMotorPower("flywheelLeft", 1);
                nextStep(1000);
                break;
            case MOVE1:
                //go forward
                driver.driveRaw( 0.3f, 0.3f, 0.3f, 0.3f);
                driver.setTargetPositions(5000,50000,50000,50000);

                nextStep(10000);
                break;
            case MOVE2:
                //strafe to rings
                driver.driveRaw(-0.3f,0.3f,0.3f, -0.3f);
                nextStepStop(2000);
                break;
            default:
                driver.driveRaw(0f, 0f, 0f, 0f);
                break;

        }
        telemetry.addData("Step:", currentStep);
        telemetry.addData("Time:", timer.seconds());


        telemetry.addData("FL Ticks: ", driver.frontLeft.getCurrentPosition() + "/" + driver.frontLeft.getTargetPosition());
        telemetry.addData("FR Ticks: ", driver.frontRight.getCurrentPosition() + "/" + driver.frontRight.getTargetPosition());
        telemetry.addData("BL Ticks: ", driver.backLeft.getCurrentPosition() + "/" + driver.backLeft.getTargetPosition());
        telemetry.addData("BR Ticks: ", driver.backRight.getCurrentPosition() + "/" + driver.backRight.getTargetPosition());

        telemetry.addData("IMU Orientation: ", imu.getOrientation().thirdAngle);
        telemetry.addData("IMU Acceleration: ", imu.getLinearAcceleration().toString());
        telemetry.addData("IMU Position: ", imu.getPosition().toString());
        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Right Power", limbs.getMotorPower("flywheelRight"));
        telemetry.addData("Flywheel Left Power", limbs.getMotorPower("flywheelLeft"));
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());

    }
}

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
import java.util.Arrays;
import java.util.Timer;

@Autonomous(group = "Step")
public class DeltaVFrankenstein extends StepAuto {
    MovementManager driver;
    ManipulationManager limbs;
    ColorSensor sensorOne;
    ColorSensor sensorFour;
    ImuManager imu;
    int stepTime=0;
    int conditional;




    public void init() {
        FeatureManager.logger.setBackend(telemetry.log());
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
//        driver.runUsingEncoders();
//        driver.getMotor()[2].setDirection(DcMotorSimple.Direction.REVERSE);
//        driver.getMotor()[3].setDirection(DcMotorSimple.Direction.FORWARD);


        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        sensorOne = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne"));
        sensorFour = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"));
        limbs.setServoPosition("wobbleArmLeft", 0.055);
        limbs.setServoPosition("wobbleArmRight", 0.055);
    }



    public void loop() {
        switch(currentStep){
            case START:
                //Flywheels startup + set shooterArm
                limbs.setServoPower("shooterArm", 0.63);
               // limbs.setMotorPower("flywheelRight", -1);
               // limbs.setMotorPower("flywheelLeft", 1);
                    nextStep(500);


                break;
            case MOVE1:
                //go forward
                driver.driveRaw( 0.3f, 0.3f, -0.3f, -0.3f);
                nextStepTicks(1680, driver.frontRight.getCurrentPosition());
                break;
            case MOVE2:
                //strafe to rings
                driver.driveRaw(0.3f,-0.3f,0.3f, -0.3f);
               nextStepTicks(70, driver.frontRight.getCurrentPosition());
                break;
            case MOVE3:
                //scan and set conditional
                driver.driveRaw(0f,0f,0f, 0f);

                sensorOne.runSample();
                sensorFour.runSample();
                if(sensorOne.isSpecial1()) {
                    if(sensorFour.isSpecial1()){
                        conditional = 4;
                    } else conditional = 1;
                } else conditional = 0;
                nextStep(500);
              break;
            case MOVE4:
                //drive to line

                driver.driveRaw( 0.3f, 0.3f, -0.3f, -0.3f);
                nextStepTicks(1080, driver.frontRight.getCurrentPosition());
                break;
            case MOVE5:
                //strafe back to center
                limbs.setMotorPower("flywheelRight", -1);
                limbs.setMotorPower("flywheelLeft", 1);
                driver.driveRaw(0.3f,-0.3f,0.3f, -0.3f);
               nextStepTicks(400, driver.frontRight.getCurrentPosition());
                break;
            case MOVE5and5:
                //Turn to center

                imu.getPosition();
                float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 180, 0.001f);
                driver.driveRaw(proportional, -proportional, -proportional, proportional);
                nextStep(300);

                break;
            case MOVE6:
            case MOVE9:
            case MOVE12:
                //shoot ring 1
                driver.driveRaw(0f,0f,0f, 0f);

                limbs.setServoPower("shooterArm", 0.45);
                limbs.setMotorPower("drum", 0);

                nextStep(200);
                break;
            case MOVE7:
            case MOVE10:
            case MOVE13:
                //retract
                limbs.setMotorPower("drum", -0.5);

                limbs.setServoPower("shooterArm", 0.63);

                nextStep(800);
                break;

            case MOVE8:
            case MOVE11:
            case MOVE14:
                //retract
                limbs.setMotorPower("drum", 0);


                nextStep(300);
                break;
            case MOVE15:

                //Choose one of three paths based on conditional. 20 = 0 rings, 30 = 1 ring, 40 = 4 rings
                limbs.setMotorPower("flywheelRight", 0);
                limbs.setMotorPower("flywheelLeft", 0);
                if(conditional == 4) {currentStep = step.THREADTHREE1; break;}
                else if (conditional == 1) {currentStep = step.THREADTWO1; break;}
                else {currentStep = step.THREADONE1; break;}
            case THREADONE1:
                //If 0 rings go forward
                driver.driveRaw( 0.3f, 0.3f, -0.3f, -0.3f);
                nextStepStop(2000);
                break;
            case THREADONE2:
                //lower wobble goal
//              limbs.setServoPower("wobleArmLeft", 1);
//              limbs.setServoPower("wobleArmRight", -1);
                nextStep(500);
                break;
            case THREADONE3:
//              limbs.setServoPower("wobleClawLeft", 1);
//              limbs.setServoPower("wobleClawRight", -1);
                nextStep(500);
                break;
            case THREADONE4:
                driver.driveRaw(-0.6f, -0.6f, 0.6f, 0.6f);
                nextStepStop(2000);
                break;
            case THREADTWO1:
                //If 1 ring drive forward more
                driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                nextStepStop(2000);
                break;
            case THREADTWO2:
                //strafe to section 1
                driver.driveRaw(-0.4f,0.4f,0.4f, -0.4f);
                nextStepStop(1000);
                break;
            case THREADTWO3:
                //lower wobble goal
//              limbs.setServoPower("wobleArmLeft", 1);
//              limbs.setServoPower("wobleArmRight", -1);
                nextStep(500);
                break;
            case THREADTWO4:
                //release wobble goal
//              limbs.setServoPower("wobleClawLeft", 1);
//              limbs.setServoPower("wobleClawRight", -1);
                nextStep(500);
                break;
            case THREADTWO5:
                //drive back to line and park
                driver.driveRaw(-0.6f, -0.6f, 0.6f, 0.6f);
                nextStepStop(2000);
                break;
            case THREADTHREE1:
                driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                nextStepStop(2000);
                break;
            case THREADTHREE2:
                //If 4 rings drive to thirds section
                driver.driveRaw(0.6f,0.6f,-0.6f, -0.6f);
                nextStepStop(2000);
                break;
            case THREADTHREE3:
                imu.getPosition();
                 proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, -90);
                driver.driveRaw(-proportional, proportional, -proportional, proportional);
                nextStepStop(2000);
                break;
            case THREADTHREE4:
                //lower wobble goal
//              limbs.setServoPower("wobleArmLeft", 1);
//              limbs.setServoPower("wobleArmRight", -1);
                nextStep(500);
                break;
            case THREADTHREE5:
//              limbs.setServoPower("wobleClawLeft", 1);
//              limbs.setServoPower("wobleClawRight", -1);
                nextStep(500);
                break;
            case THREADTHREE6:
                driver.driveRaw(-0.6f,-0.6f,0.6f, 0.6f);
                nextStepStop(2000);
                break;

            default:
                driver.driveRaw(0f, 0f, 0f, 0f);
                limbs.setMotorPower("flywheelRight", 0);
                limbs.setMotorPower("flywheelLeft", 0);
                limbs.setServoPower("shooterArm", 0.63);
                break;

        }
        telemetry.addData("Step:", currentStep);
        telemetry.addData("Conditional:", conditional+"");
        telemetry.addData("ticks", Arrays.toString(driver.getMotorPositions()));

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

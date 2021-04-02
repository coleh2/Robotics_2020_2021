package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.auxilary.EncodedMotor;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

import java.util.Arrays;

@Autonomous(group = "Step")
public class LongerAutoBecauseScrewJoshua extends StepAuto {
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
                        new EncodedMotor(hardwareMap.get(DcMotor.class, "flywheel"), 13)
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel"
                }
        );
        driver.resetEncoders();
        driver.runWithOutEncoders();
//        driver.runUsingEncoders();
//        driver.getMotor()[2].setDirection(DcMotorSimple.Direction.REVERSE);
//        driver.getMotor()[3].setDirection(DcMotorSimple.Direction.FORWARD);

        SensorManager sense = new SensorManager(
                new ColorSensor[] {
                        new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne")),
                        new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"))
                },
                new String[] {
                        "sensorOne",
                        "sensorFour"
                }
        );


        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        sensorOne = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne"));
        sensorFour = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"));
        limbs.getServo("wobbleArmLeft").setDirection(Servo.Direction.REVERSE);
        limbs.getServo("wobbleGrabRight").setDirection(Servo.Direction.REVERSE);
    }



    public void loop() {
        switch(currentStep){
            case START:
                //Flywheels startup + set shooterArm
                limbs.setServoPower("shooterArm", 0.63);
               // limbs.setMotorPower("flywheelRight", -1);
               // limbs.setMotorPower("flywheelLeft", 1);
                    nextStep(500);
                //engage serovs to release the wobble
                limbs.setServoPosition("wobbleArmLeft", 0.055);
                limbs.setServoPosition("wobbleArmRight", 0.055);
                limbs.setServoPosition("wobbleGrabLeft", 1);
                limbs.setServoPosition("wobbleGrabRight", 1);

                break;
            case MOVE1:
                //go forward
                driver.driveRaw( 0.4f, 0.4f, -0.4f, -0.4f);
                nextStepTicks(1550, driver.getTicks());
                break;
            case MOVE2:
                //strafe to rings
                driver.driveRaw(0.4f,-0.4f,0.4f, -0.4f);
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

                driver.driveRaw( 0.4f, 0.4f, -0.4f, -0.4f);
                nextStepTicks(900, driver.frontRight.getCurrentPosition());
                break;
            case MOVE5:
                //strafe back to center
                limbs.setMotorPower("flywheel", .75);
                driver.driveRaw(0.4f,-0.4f,0.4f, -0.4f);
               nextStepTicks(300, driver.frontRight.getCurrentPosition());
                break;
            case MOVE5and5:
                //Turn to center

                imu.getPosition();
                float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 180, 0.001f);
                double driveProp = (proportional/Math.abs(proportional)) * Range.clip(Math.abs(proportional), 0.06f, 0.4f);

                driver.driveRaw(proportional, -proportional, -proportional, proportional);
                if(Math.abs(imu.getOrientation().thirdAngle) == 178) {
                    currentStep = step.MOVE6;
                } else {
                    nextStep(1000);
                }

                break;
            case MOVE6:
            case MOVE9:
            case MOVE12:
                //shoot ring 1
                driver.driveRaw(0f,0f,0f, 0f);

                limbs.setServoPower("shooterArm", 0.4);
                limbs.setMotorPower("drum", 0);

                nextStep(400);
                break;
            case MOVE7:
            case MOVE10:
            case MOVE13:
                //retract
                limbs.setMotorPower("flywheel", -0.25);
                limbs.setMotorPower("drum", -0.5);

                limbs.setServoPower("shooterArm", 0.57);

                nextStep(800);
                break;

            case MOVE8:
            case MOVE11:
            case MOVE14:
                //retract
                limbs.setMotorPower("drum", 0);
                limbs.setMotorPower("flywheel", 0.75);


                nextStep(300);
                break;
            case MOVE15:

                //Choose one of three paths based on conditional. 20 = 0 rings, 30 = 1 ring, 40 = 4 rings
                limbs.setMotorPower("flywheel", 0);
                if(conditional == 4) {currentStep = step.THREADTHREE1; break;}
                else if (conditional == 1) {currentStep = step.THREADTWO1; break;}
                else {currentStep = step.THREADONE1; break;}
            case THREADONE1:
                //If 0 rings go forward
                driver.driveRaw( 0.4f, 0.4f, -0.4f, -0.4f);
                nextStepTicks(1300, driver.frontRight.getCurrentPosition());
            break;
            case THREADONE2:
               //strafe to the side
                driver.driveRaw(-0.4f, 0.4f,-0.4f, 0.4f);

                nextStepTicks(2000, driver.frontRight.getCurrentPosition());
                break;
            case THREADONE3:
            case THREADTWO3:
            case THREADTHREE3:
                driver.driveRaw(0,0,0, 0);
//lowe rarm
                limbs.setServoPosition("wobbleArmLeft", 0.086);
                limbs.setServoPosition("wobbleArmRight", 0.086);
                nextStep(2000);
                break;
            case THREADONE4:
            case THREADTWO4:
            case THREADTHREE4:
                //lower grabber
                limbs.setServoPosition("wobbleGrabLeft", 0.2);
                limbs.setServoPosition("wobbleGrabRight", 0.2);
                nextStep(2000);
                break;

            case THREADONE41:
            case THREADTWO41:
            case THREADTHREE41:

                //raise arm
                limbs.setServoPosition("wobbleArmLeft", 0.055);
                limbs.setServoPosition("wobbleArmRight", 0.055);
//strafe back
                driver.driveRaw(0.4f, -0.4f,0.4f, -0.4f);
                nextStepTicks(1300, driver.frontRight.getCurrentPosition());

                break;
            case THREADONE42:

               //drive back

                driver.driveRaw( -0.4f, -0.4f, 0.4f, 0.4f);
                nextStepTicks(1000, driver.frontRight.getCurrentPosition());

                break;
            case THREADONE5:
                //strafe to the side
                driver.driveRaw(0.4f, -0.4f,0.4f, -0.4f);

                nextStepTicks(1500, driver.frontRight.getCurrentPosition());
                break;
            case THREADONE6:
                //raise arm
                limbs.setServoPosition("wobbleArmLeft", 0.055);
                limbs.setServoPosition("wobbleArmRight", 0.055);
                break;
            case THREADTWO1:
                //If 1 rings go forward
                driver.driveRaw( 0.4f, 0.4f, -0.4f, -0.4f);
                nextStepTicks(2000, driver.frontRight.getCurrentPosition());
                break;
            case THREADTWO2:
                //strafe to the side
              //  driver.driveRaw(-0.4f, 0.4f,-0.4f, 0.4f);
                nextStepTicks(100, driver.frontRight.getCurrentPosition());
                break;
//            case THREADTWO3:
//                //lower wobble goal
//                driver.driveRaw(0,0,0, 0);
//                limbs.setServoPosition("wobbleArmLeft", 0.086);
//                limbs.setServoPosition("wobbleArmRight", 0.086);
//                nextStep(1000);
//                break;
            case THREADTWO42:
                //come back to park
                driver.driveRaw( -0.4f, -0.4f, 0.4f, 0.4f);
                nextStepTicks(1500, driver.frontRight.getCurrentPosition());

                break;
//            case THREADTWO5:
//                //drive back to line and park
//                driver.driveRaw(-0.6f, -0.6f, 0.6f, 0.6f);
//                nextStepStop(2000);
//                break;
            case THREADTHREE1:
                driver.driveRaw( 0.4f, 0.4f, -0.4f, -0.4f);
                nextStepStop(2800);
                break;
            case THREADTHREE2:

                imu.getPosition();
                 proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, 90, 0.004f);
                 driveProp = (proportional/Math.abs(proportional)) * Range.clip(Math.abs(proportional), 0.001f, 0.4f);

                driver.driveRaw(proportional, -proportional, -proportional, proportional);
                nextStep(2000);

                break;
            case THREADTHREE2and2:
                driver.driveRaw(-0.4f, -0.4f, 0.4f, 0.4f);

                nextStepTicks(400, driver.frontRight.getCurrentPosition());
                break;
            case THREADTHREE42:
                driver.driveRaw(0.4f, -0.4f,0.4f, -0.4f);
                nextStepTicks(800, driver.frontRight.getCurrentPosition());

                break;

            default:
                driver.driveRaw(0f, 0f, 0f, 0f);
                limbs.setMotorPower("flywheel", 0);
                limbs.setServoPower("shooterArm", 0.63);
                limbs.closeMotors();
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
        telemetry.addData("Flywheel", limbs.getMotorPower("flywheel"));
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());

    }

    @Override
    public void stop() {
        limbs.closeMotors();
        super.stop();
    }
}
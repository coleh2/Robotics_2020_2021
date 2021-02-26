package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.AutoState;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.StateMachine;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

@Autonomous
public class SwitchAuto extends OpMode {

    MovementManager driver;
    ManipulationManager limbs;
    ColorSensor sensorOne;
    ColorSensor sensorFour;
    ImuManager imu;
    int step;
    int conditional;
    double startTime;

    public void init() {
        resetStartTime();
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
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        sensorOne = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne"));
        sensorFour = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"));



        /*
            ... init limbs and driver, not shown ...
         */
        step = 0;
    }


    public void loop() {
        limbs.setServoPosition("shooterArm", 0.63);

        switch(step){
            case 0:
                limbs.setMotorPower("flywheelRight", -1);
                limbs.setMotorPower("flywheelLeft", 1);
                step++;
                break;
            case 1:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1000) {
                    driver.driveOmni(new float[] {0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 2:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {0f,0.4f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 3:
                sensorOne.runSample();
                sensorFour.runSample();
                if(sensorOne.isSpecial1()) {
                    if(sensorFour.isSpecial1()){
                        conditional = 4;
                    } else conditional = 1;
                } else conditional = 0;
                step++;
                break;
            case 4:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {0f,-0.4f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 5:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1000) {
                    driver.driveOmni(new float[] {0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 6:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0);
                } else {
                    step++;
                    break;
                }
            case 7:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0.63);
                } else {
                    step++;
                    break;
                }
            case 8:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0);
                } else {
                    step++;
                    break;
                }
            case 9:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0.63);
                } else {
                    step++;
                    break;
                }
            case 10:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0);
                } else {
                    step++;
                    break;
                }
            case 11:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    limbs.setServoPosition("servoArm", 0.63);
                } else {
                    step++;
                    break;
                }
            case 12:
                if(conditional == 0) {step = 20; break;}
                    else if (conditional == 1) {step = 30; break;}
                    else if (conditional == 4) {step = 40; break;}
            case 20:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 21:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    break;
                }
            case 22:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    break;
                }
            case 23:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {-0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step = 77;
                    break;
                }
            case 30:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1000) {
                    driver.driveOmni(new float[] {0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 31:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {0f,-0.4f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 32:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
                    driver.driveOmni(new float[] {0f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 33:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    break;
                }
            case 34:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    break;
                }
            case 35:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1000) {
                    driver.driveOmni(new float[] {-0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step = 77;
                    break;
                }
            case 40:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1500) {
                    driver.driveOmni(new float[] {0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 41:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1000) {
                    imu.getPosition();
                    float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, -90);
                    driver.driveOmni(new float[] {0,0,-proportional});;
                } else {
                    driver.stopDrive();
                    step++;
                    break;
                }
            case 42:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    break;
                }
            case 43:
                startTime = getRuntime();
                if(getRuntime()-startTime < 500) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    break;
                }
            case 44:
                startTime = getRuntime();
                if(getRuntime()-startTime < 1500) {
                    driver.driveOmni(new float[] {-0.6f,0f,0f});
                } else {
                    driver.stopDrive();
                    step = 77;
                    break;
                }
        }

        telemetry.addData("Step:", step);
        telemetry.addData("IMU Orientation: ", imu.getOrientation().thirdAngle);
        telemetry.addData("IMU Acceleration: ", imu.getLinearAcceleration().toString());
        telemetry.addData("IMU Position: ", imu.getPosition().toString());
        telemetry.addData("Time:", getRuntime());
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

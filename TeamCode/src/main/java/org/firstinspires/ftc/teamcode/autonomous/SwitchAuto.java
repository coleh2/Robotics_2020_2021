package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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

        switch(step){
            case 0:
                //set shooterArm and turn on Flywheels
                limbs.setServoPower("shooterArm", 0.63);
                limbs.setMotorPower("flywheelRight", -1);
                limbs.setMotorPower("flywheelLeft", 1);
                step++;
                startTime = getRuntime(); break;
            case 1:
                //go forward
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw( 0.6f, 0.6f, 0.6f, 0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 2:
                //strafe to rings
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw(0.4f,-0.4f,-0.4f, 0.4f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 3:
                //scan and set conditional
                sensorOne.runSample();
                sensorFour.runSample();
                if(sensorOne.isSpecial1()) {
                    if(sensorFour.isSpecial1()){
                        conditional = 4;
                    } else conditional = 1;
                } else conditional = 0;
                step++;
                startTime = getRuntime(); break;
            case 4:
                //strafe back to center
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw(-0.4f,0.4f,0.4f, -0.4f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 5:
                //drive to line
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 6:
                //shoot ring 1
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 7:
                //retract
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0.63);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 8:
                //shoot ring 2
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 9:
                //retract
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0.63);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 10:
                //shoot ring 3
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 11:
                //retract
                if(getRuntime()-startTime < .5) {
                    limbs.setServoPower("shooterArm", 0.63);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 12:
                //Choose one of three paths based on conditional. 20 = 0 rings, 30 = 1 ring, 40 = 4 rings
                if(conditional == 0) {step = 20; startTime = getRuntime(); break;}
                    else if (conditional == 1) {step = 30; startTime = getRuntime(); break;}
                    else if (conditional == 4) {step = 40; startTime = getRuntime(); break;}
            case 20:
                //If 0 rings go forward
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 21:
                //lower wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 22:
                //release wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 23:
                //go back to line and park
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw(-0.6f, -0.6f, 0.6f, 0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step = 77;
                    startTime = getRuntime(); break;
                }
            case 30:
                //If 1 ring drive forward more
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 31:
                //strafe to section 1
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw(-0.4f,0.4f,0.4f, -0.4f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 32:
                //lower wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 33:
                //release wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 34:
                //drive back to line and park
                if(getRuntime()-startTime < 1) {
                    driver.driveRaw(-0.6f, -0.6f, 0.6f, 0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step = 77;
                    startTime = getRuntime(); break;
                }
            case 40:
                if(getRuntime()-startTime < 1.5) {
                    driver.driveRaw( 0.6f, 0.6f, -0.6f, -0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 41:
                //If 4 rings drive to thirds section
                if(getRuntime()-startTime < 1.5) {
                    driver.driveRaw(0.6f,0.6f,-0.6f, -0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 42:
                //turn 90 degrees
                if(getRuntime()-startTime < 1) {
                    imu.getPosition();
                    float proportional = PaulMath.proportionalPID(imu.getOrientation().thirdAngle, -90);
                    driver.driveRaw(-proportional, proportional, -proportional, proportional);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step++;
                    startTime = getRuntime(); break;
                }
            case 43:
                //lower wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleArmLeft", 1);
//                    limbs.setServoPower("wobleArmRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 44:
                //release wobble goal
                if(getRuntime()-startTime < .5) {
//                    limbs.setServoPower("wobleClawLeft", 1);
//                    limbs.setServoPower("wobleClawRight", -1);
                } else {
                    step++;
                    startTime = getRuntime(); break;
                }
            case 45:
                //drive back to the line and park
                if(getRuntime()-startTime < 1.5) {
                    driver.driveRaw(-0.6f,-0.6f,0.6f, 0.6f);
                } else {
                    driver.driveRaw(0f, 0f, 0f, 0f);
                    step = 77;
                    startTime = getRuntime(); break;
                }
        }

        telemetry.addData("Step:", step);
        telemetry.addData("IMU Orientation: ", imu.getOrientation().thirdAngle);
        telemetry.addData("IMU Acceleration: ", imu.getLinearAcceleration().toString());
        telemetry.addData("IMU Position: ", imu.getPosition().toString());
        telemetry.addData("Time:", getRuntime());
        telemetry.addData("StartTime: ", startTime);
        telemetry.addData("Step Run Time:", getRuntime()-startTime);
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

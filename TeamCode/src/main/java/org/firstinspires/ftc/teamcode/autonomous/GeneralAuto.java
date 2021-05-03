package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.teleop.*;

@Autonomous
public class GeneralAuto extends OpMode {

    MovementManager driver;
    ManipulationManager limbs;



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
                        hardwareMap.get(DcMotor.class, "flywheel")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheel"
                }
        );

    }


    public void loop() {
        limbs.setServoPower("shooterArm", 0.65);

        if( getRuntime() < 2.5) {
            driver.driveRaw(0.6f,0.6f,-0.6f, -0.6f);
        } else if(getRuntime() < 3) {
            driver.stopDrive();
        } else if(getRuntime() < 3.5) {
            limbs.setMotorPower("flywheel", 1);
        } else if(getRuntime() < 4.5) {
            limbs.setServoPower("shooterArm", 0);
            limbs.setMotorPower("drum", -0.5);
        } else if(getRuntime() < 5) {
            limbs.setServoPower("shooterArm", 0.65);
            limbs.setMotorPower("drum", 0);
        } else if(getRuntime() < 5.5) {
            limbs.setServoPower("shooterArm", 0);
            limbs.setMotorPower("drum", -0.5);
        } else if(getRuntime() < 6) {
            limbs.setServoPower("shooterArm", 0.65);
            limbs.setMotorPower("drum", 0);
        }else if(getRuntime() < 6.5) {
            limbs.setServoPower("shooterArm", 0);
            limbs.setMotorPower("drum", -0.5);
        } else if(getRuntime() < 7) {
            limbs.setServoPower("shooterArm", 0.65);
            limbs.setMotorPower("drum", 0);
        } else if(getRuntime() < 7.5) {
            limbs.setMotorPower("flywheel", 0);
        } else if(getRuntime() < 8) {
            driver.driveRaw(0.5f,0.5f,-0.5f, -0.5f);
        } else driver.stopDrive();

        telemetry.addData("Time:", getRuntime());
        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Power", limbs.getMotorPower("flywheel"));
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BR Power: ", driver.backRight.getPower());

    }

}

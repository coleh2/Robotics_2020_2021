package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ShootingTogglesControlMap;
import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ImuManager;
import org.firstinspires.ftc.teamcode.managers.InputManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;


@TeleOp
public class TeleopColorSensor extends OpMode {

    InputManager input;
    MovementManager driver;
    ManipulationManager limbs;
    ColorSensor sensorOne;
    ColorSensor sensorFour;
    Servo grab;
    ImuManager imu;


    private static boolean toggleSpeed = false;
  //  private Object;

    public void init() {
        FeatureManager.logger.setBackend(telemetry.log());

        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "br"),
                hardwareMap.get(DcMotor.class, "bl"));
        input = new InputManager(gamepad1, new ShootingTogglesControlMap());
        imu = new ImuManager(hardwareMap.get(com.qualcomm.hardware.bosch.BNO055IMU.class, "imu"));
        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm"),

                },
                new String[] {
                        "shooterArm",

                },
                new Servo[] {


                },
                new String[] {

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
        driver.runUsingEncoders();

        sensorOne = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorOne"));
        sensorFour = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorFour"));
    }

    //determine and return the number of rings
    public int ReturnRingNumber(){
        int num = 0;
        if(sensorOne.isSpecial1()){
            num = 1;
        }
        if(sensorFour.isSpecial1()){
            num = 4;
        }
        return num;
    }

    public void loop() {
        input.update();

        driver.driveOmni((input.getVector("drive")));

//        limbs.setMotorPower("intake", 0.5*input.getScalar("fullIntake"));
//        limbs.setServoPower("wobbleArmRight", input.getScalar("wobbleGraber"));
//        limbs.setServoPower("wobbleGrabRight", input.getScalar("wobbleGraberNegative"));
//        limbs.setServoPower("wobbleArmLeft", -input.getScalar("wobbleGraber"));
//        limbs.setServoPower("wobbleGrabLeft", -input.getScalar("wobbleGraberNegative"));

        sensorOne.runSample();
        sensorFour.runSample();




        if(input.getGamepad().right_trigger > 0.1) {
            limbs.setMotorPower("flywheelRight", -1);
            limbs.setMotorPower("flywheelLeft", 1);
        } else {
            limbs.setMotorPower("flywheelRight", 0);
            limbs.setMotorPower("flywheelLeft", 0);
        }

        if(input.getGamepad().a) {
            limbs.setServoPower("shooterArm", 0.325);
        } else {
            limbs.setServoPower("shooterArm", 0.65);
        }

        limbs.setMotorPower("drum", (input.getGamepad().right_trigger > 0.1 || input.getScalar("fullIntake") == 1) ? -1 : 0);

        if(input.getGamepad().dpad_up) {
            limbs.setMotorPower("drum", -1);
        } else if(input.getGamepad().dpad_down) {
            limbs.setMotorPower("drum", 1);
        }



//        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
//        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
//        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
//        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
//        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
//                driver.frontRight.getCurrentPosition()+
//                driver.backLeft.getCurrentPosition()+
//                driver.backRight.getCurrentPosition())/4);

        telemetry.addData("rings:",ReturnRingNumber());


        telemetry.addData("colorhsv_oneHue",sensorOne.getHsv()[0]);
        telemetry.addData("colorhsv_SAT",sensorOne.getHsv()[1]);
        telemetry.addData("colorhsv_VALUE", sensorOne.getHsv()[2]);
        telemetry.addData("isSpelical1", sensorOne.isSpecial1());


        telemetry.addData("colorhsv_fourHue",sensorFour.getHsv()[0]);
        telemetry.addData("colorhsv_SAT",sensorFour.getHsv()[1]);
        telemetry.addData("colorhsv_VALUE", sensorFour.getHsv()[2]);
        telemetry.addData("isSpecial1", sensorFour.isSpecial1());


        telemetry.addData("Color Code", sensorOne.getHexCode());
        telemetry.addData("Color Code", sensorFour.getHexCode());

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

//        telemetry.addData("speed: ", driver.getScale());

        telemetry.addData("left trigger: ", input.getGamepad().left_trigger);

        telemetry.addData("controls://fullIntake:", input.getControl("fullIntake").toString());
        telemetry.addData("controls://lt:", input.getScalar("lt"));

    }
}
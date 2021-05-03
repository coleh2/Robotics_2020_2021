package org.firstinspires.ftc.teamcode.managers;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.auxilary.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.auxilary.roadrunner.drive.StandardTrackingWheelLocalizer;

public class OdometryManager extends FeatureManager {
    SampleMecanumDrive driveBackend;

    public OdometryManager(HardwareMap hardwareMap) {
        driveBackend = new SampleMecanumDrive(hardwareMap);
        driveBackend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void update() {
        driveBackend.update();
    }

    public void driveOmni(float[] powers) {
        driveBackend.setWeightedDrivePower(
                new Pose2d(
                        powers[1]*0.3,
                        -powers[2]*0.3,
                        powers[0]*0.3
                )
        );
    }

    public void driveRaw(float fl, float fr, float bl, float br) {
        driveBackend.setMotorPowers(fl, fr, bl, br);
    }

    public float[] getCurrentPosition() {
        Pose2d estimate = driveBackend.getPoseEstimate();
        return new float[] {
            (float) estimate.getX(),
            (float) estimate.getY(),
            (float) estimate.getHeading()
        };
    }
}

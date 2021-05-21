package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.NaiveAccelerationIntegrator;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;

import static org.firstinspires.ftc.robotcore.external.navigation.NavUtil.meanIntegrate;
import static org.firstinspires.ftc.robotcore.external.navigation.NavUtil.plus;

public class ImuManager extends FeatureManager {
    public BNO055IMU imu;

    public ImuManager(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.useExternalCrystal = true;
        parameters.loggingEnabled  = true;
        parameters.loggingTag = "Imu";

        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(DistanceUnit.CM, 0.0, 0.0, 0.0, System.nanoTime()),
                new Velocity(DistanceUnit.CM, 0.0, 0.0, 0.0, System.nanoTime()),
                10);
    }

    /**
     * Get the orientation
     * @return Current orientation of the robot, in degrees
     */
    public Orientation getOrientation() {
        return imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
    }

    public Position getPosition() {
        return imu.getPosition();
    }

    public Acceleration getLinearAcceleration() {
        return imu.getLinearAcceleration();
    }

    public float[] rotateDriveControlToHeadless(float[] drive) {
        float y = drive[0];
        float x = drive[1];

        float[] polar = PaulMath.polarToCartesian(x, y);

        polar[0] -= getOrientation().thirdAngle;

        float[] cartesian = PaulMath.cartesianToPolar(polar[0], polar[1]);

        return new float[] {
                cartesian[0],
                cartesian[1],
                drive[2]
        };
    }


}

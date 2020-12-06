package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class ImuManager {
    public BNO055IMU imu;

    public ImuManager(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.useExternalCrystal = true;
        parameters.loggingEnabled  = false;
        parameters.accelerationIntegrationAlgorithm = new NavUtilIntegrationAlgorithm();

        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(DistanceUnit.CM, 0.0, 0.0, 0.0, System.nanoTime()),
                new Velocity(DistanceUnit.CM, 0.0, 0.0, 0.0, System.nanoTime()), 10);
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


    private static class NavUtilIntegrationAlgorithm implements BNO055IMU.AccelerationIntegrator {

        Position position;
        Velocity velocity;
        Acceleration acceleration;

        long time;

        @Override
        public void initialize(BNO055IMU.Parameters parameters, Position initialPosition, Velocity initialVelocity) {
            this.position = initialPosition;
            this.velocity = initialVelocity;

            time = position.acquisitionTime;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public Velocity getVelocity() {
            return velocity;
        }

        @Override
        public Acceleration getAcceleration() {
            return acceleration;
        }

        @Override
        public void update(Acceleration linearAcceleration) {
            this.acceleration = linearAcceleration;

            long deltaTime = acceleration.acquisitionTime - time;

            this.velocity = NavUtil.integrate(acceleration, deltaTime);
            this.position = NavUtil.integrate(velocity, deltaTime);

            time = acceleration.acquisitionTime;
        }
    }
}

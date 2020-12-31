package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManipulationManager {
    public CRServo[] servos;
    public DcMotor[] motors;

    public String[] servoNames;
    public String[] motorNames;

    public float[] motorCoefficient;

    public ManipulationManager(CRServo[] _servos, DcMotor[] _motors, float[] motorCoefficient) {
        this.servos = _servos;
        this.motors = _motors;
        this.motorCoefficient = motorCoefficient;
    }

    public ManipulationManager(CRServo[] _servos, String[] _servoNames, DcMotor[] _motors, String[] _motorNames, float[] motorCoefficient) {
        if(_servoNames.length != _servos.length) throw new IllegalArgumentException("Servo Names must be the same length as Servos");
        if(_motorNames.length != _motors.length) throw new IllegalArgumentException("Motor Names must be the same length as Motors");
        this.servos = _servos;
        this.servoNames = _servoNames;
        this.motors = _motors;
        this.motorNames = _motorNames;
        this.motorCoefficient = motorCoefficient;
    }

    public void setServoNames(String[] _servoNames) {
        if(_servoNames.length != servos.length) throw new IllegalArgumentException("Servo Names must be the same length as Servos");
        this.servoNames = servoNames;
    }

    public void setMotorNames(String[] _motorNames) {
        if(_motorNames.length != motors.length) throw new IllegalArgumentException("Motor Names must be the same length as Motors");
        this.motorNames = motorNames;
    }

    public void setServoPower(String name, double power) {
        int index = (Arrays.asList(servoNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Servo " + name + " does not exist or is not registered");
        servos[index].setPower(power);
    }

    public void setMotorPower(String name, double power) {
        int index = (Arrays.asList(motorNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Motor " + name + " does not exist or is not registered");
        motors[index].setPower(power*motorCoefficient[index]);
    }

    public double getMotorPower(String name) {
        int index = (Arrays.asList(motorNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Motor " + name + " does not exist or is not registered");
        return motors[index].getPower();
    }

    public void setMotorPower(int i, double power) {
        motors[i].setPower(power);
    }

    public void setServoPower(int i, double power) {
        servos[i].setPower(power);
    }


}

package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManipulationManager {
    public CRServo[] crservos;
    public DcMotor[] motors;
    public Servo[] servos;

    public String[] crservoNames;
    public String[] motorNames;
    public String[] servoNames;


    public ManipulationManager(CRServo[] _crservos, Servo[] _servos, DcMotor[] _motors) {
        this.crservos = _crservos;
        this.servos = _servos;
        this.motors = _motors;
    }

    public ManipulationManager(CRServo[] _crservos, String[] _crservoNames, Servo[] _servos, String[] _servoNames, DcMotor[] _motors, String[] _motorNames) {
        if(_crservoNames.length != _crservos.length) throw new IllegalArgumentException("CRServo Names must be the same length as CRServos");
        if(_servoNames.length != _servos.length) throw new IllegalArgumentException("Servo Names must be the same length as Servos");
        if(_motorNames.length != _motors.length) throw new IllegalArgumentException("Motor Names must be the same length as Motors");
        this.crservos = _crservos;
        this.crservoNames = _crservoNames;
        this.servos = _servos;
        this.servoNames = _servoNames;
        this.motors = _motors;
        this.motorNames = _motorNames;
    }

    public void setCRServoNames(String[] _crservoNames) {
        if(_crservoNames.length != crservos.length) throw new IllegalArgumentException("CRServo Names must be the same length as CRServos");
        this.crservoNames = _crservoNames;
    }

    public void setServoNames(String[] _servoNames) {
        if (_servoNames.length != servos.length) throw new IllegalArgumentException("Servo Names must be the same length as Servos");
        this.servoNames = _servoNames;
    }

    public void setMotorNames(String[] _motorNames) {
        if(_motorNames.length != motors.length) throw new IllegalArgumentException("Motor Names must be the same length as Motors");
        this.motorNames = _motorNames;
    }

    public void setServoPosition(String name, double position) {
        int index = (Arrays.asList(servoNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Servo " + name + " does not exist or is not registered");
        servos[index].setPosition(position);
    }

    public void setServoPower(String name, double power) {
        int index = (Arrays.asList(crservoNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Servo " + name + " does not exist or is not registered");
        crservos[index].setPower(power);
    }

    public void setMotorPower(String name, double power) {
        int index = (Arrays.asList(motorNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Motor " + name + " does not exist or is not registered");
        motors[index].setPower(power);
    }

    public double getMotorPower(String name) {
        int index = (Arrays.asList(motorNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Motor " + name + " does not exist or is not registered");
        return motors[index].getPower();
    }

    public double getServoPower(String name) {
        int index = (Arrays.asList(crservoNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("CRServo " + name + " does not exist or is not registered");
        return crservos[index].getPower();
    }

//    public double getServoPosition(String name) {
//        int index = (Arrays.asList(servoNames)).indexOf(name);
//        if(index == -1) throw new IllegalArgumentException("Servo " + name + " does not exist or is not registered");
//        return servos[index].getPosition();
//    }

    public void setMotorPower(int i, double power) {
        motors[i].setPower(power);
    }

    public void setServoPower(int i, double power) {
        crservos[i].setPower(power);
    }

//    public void setServoPosition(int i, double power) {
//        servos[i].setPosition(power);
//    }



}
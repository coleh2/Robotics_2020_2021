package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

public class SensorManager {
    public NormalizedColorSensor[] sensors;
    public ColorRangeSensor[] rangeSensors;

    public String[] sensorsNames;
    public String[] rangeSensorsNames;


    public SensorManager(NormalizedColorSensor[] _sensors, ColorRangeSensor[] _rangeSensors) {
        this.sensors = _sensors;
        this.rangeSensors = _rangeSensors;
    }

    public SensorManager(NormalizedColorSensor[] _sensors, String[] _sensorNames, ColorRangeSensor[] _rangeSensors, String[] _rangeSensorNames) {
        if(_sensorNames.length != _sensors.length) throw new IllegalArgumentException("Sensor Names must be the same length as Sensors");
        if(_rangeSensorNames.length != _sensors.length) throw new IllegalArgumentException("Ranage Servo Names must be the same length as Range Sensors");
        this.sensors = _sensors;
        this.sensorsNames = _sensorNames;
        this.rangeSensors = _rangeSensors;
        this.rangeSensorsNames = _rangeSensorNames;
    }

    public void setSensorNames(String[] _sensorNames) {
        if(_sensorNames.length != sensorsNames.length) throw new IllegalArgumentException("Sensor Names must be the same length as Sensors");
        this.sensorsNames = _sensorNames;
    }

    public void setRangeSensorNames(String[] _rangeSensorNames) {
        if (_rangeSensorNames.length != rangeSensors.length) throw new IllegalArgumentException("Range Sensor Names must be the same length as Range Sensors");
        this.rangeSensorsNames = _rangeSensorNames;
    }




}

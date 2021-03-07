package org.firstinspires.ftc.teamcode.managers;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.auxilary.ColorSensor;
import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;

import java.util.Arrays;

public class SensorManager extends FeatureManager {
    public ColorSensor[] sensors;

    public String[] sensorNames;

    public SensorManager(ColorSensor[] _sensors, String[] _sensorNames) {
        if(_sensorNames.length != _sensors.length) throw new IllegalArgumentException("Sensor Names must be the same length as Sensors");
        this.sensors = _sensors;
        this.sensorNames = _sensorNames;
    }

    public void setSensorNames(String[] _sensorNames) {
        if(_sensorNames.length != sensorNames.length) throw new IllegalArgumentException("Sensor Names must be the same length as Sensors");
        this.sensorNames = _sensorNames;
    }

    public float[] getHSL(String name) {
        int index = (Arrays.asList(sensorNames)).indexOf(name);
        if(index == -1) throw new IllegalArgumentException("Motor " + name + " does not exist or is not registered");
        return getHSL(index);
    }

    public float[] getHSL(int index) {
        NormalizedRGBA color = this.sensors[index].getNormalizedColors();
        return PaulMath.rgbToHsl(color.red, color.green, color.blue);
    }

    public int getColorInteger(int index) {
        NormalizedRGBA color = this.sensors[index].getNormalizedColors();
        float scale = 256; int min = 0, max = 255;
        return (Range.clip((int)(color.alpha * scale), min, max) << 24) |
                (Range.clip((int)(color.red   * scale), min, max) << 16) |
                (Range.clip((int)(color.alpha * scale), min, max) << 8) |
                Range.clip((int)(color.blue  * scale), min, max);
    }

    public boolean isSpecial(int index) {
        return sensors[index].isSpecial1();
    }




}

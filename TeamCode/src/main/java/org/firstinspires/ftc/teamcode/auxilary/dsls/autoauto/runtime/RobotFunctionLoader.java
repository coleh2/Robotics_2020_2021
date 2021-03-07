package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import android.util.Log;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.robotfunctions.*;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;
import org.firstinspires.ftc.teamcode.managers.SensorManager;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class RobotFunctionLoader {

    public static void addFunctionsToStore(Function[] fns, FunctionStore store) {
        for(Function f : fns) store.put(f.name, f.argCount, f);
    }

    public static Function[] loadFunctions(MovementManager driver, ManipulationManager manip, SensorManager sense) {
        ArrayList<Function> functions = new ArrayList<>();

        DriveOmniFunction driveOmni = new DriveOmniFunction(driver); driveOmni.setName("driveOmni"); driveOmni.setArgCount(3); functions.add(driveOmni);
        StopDriveFunction stopDrive = new StopDriveFunction(driver); stopDrive.setName("stopDrive"); stopDrive.setArgCount(0); functions.add(stopDrive);
        GetScaleFunction getScale = new GetScaleFunction(driver); getScale.setName("getScale"); getScale.setArgCount(0); functions.add(getScale);
        DownScaleFunction downScale = new DownScaleFunction(driver); downScale.setName("downScale"); downScale.setArgCount(0); functions.add(downScale);
        RunUsingEncodersFunction runUsingEncoders = new RunUsingEncodersFunction(driver); runUsingEncoders.setName("runUsingEncoders"); runUsingEncoders.setArgCount(0); functions.add(runUsingEncoders);
        ResetEncodersFunction resetEncoders = new ResetEncodersFunction(driver); resetEncoders.setName("resetEncoders"); resetEncoders.setArgCount(0); functions.add(resetEncoders);
        DriveVerticalFunction driveVertical = new DriveVerticalFunction(driver); driveVertical.setName("driveVertical"); driveVertical.setArgCount(2); functions.add(driveVertical);
        RunToPositionFunction runToPosition = new RunToPositionFunction(driver); runToPosition.setName("runToPosition"); runToPosition.setArgCount(0); functions.add(runToPosition);
        UpScaleFunction upScale = new UpScaleFunction(driver); upScale.setName("upScale"); upScale.setArgCount(0); functions.add(upScale);
        DriveRawFunction driveRaw = new DriveRawFunction(driver); driveRaw.setName("driveRaw"); driveRaw.setArgCount(4); functions.add(driveRaw);
        DriveOmniExponentialFunction driveOmniExponential = new DriveOmniExponentialFunction(driver); driveOmniExponential.setName("driveOmniExponential"); driveOmniExponential.setArgCount(1); functions.add(driveOmniExponential);
        RunWithOutEncodersFunction runWithOutEncoders = new RunWithOutEncodersFunction(driver); runWithOutEncoders.setName("runWithOutEncoders"); runWithOutEncoders.setArgCount(0); functions.add(runWithOutEncoders);
        SetTargetPositionsFunction setTargetPositions = new SetTargetPositionsFunction(driver); setTargetPositions.setName("setTargetPositions"); setTargetPositions.setArgCount(4); functions.add(setTargetPositions);
        GetMotorPositionsFunction getMotorPositions = new GetMotorPositionsFunction(driver); getMotorPositions.setName("getMotorPositions"); getMotorPositions.setArgCount(0); functions.add(getMotorPositions);
        DriveWithVerticalFunction driveWithVertical = new DriveWithVerticalFunction(driver); driveWithVertical.setName("driveWithVertical"); driveWithVertical.setArgCount(2); functions.add(driveWithVertical);

        SetMotorPowerFunction setMotorPower = new SetMotorPowerFunction(manip); setMotorPower.setName("setMotorPower"); setMotorPower.setArgCount(2); functions.add(setMotorPower);
        SetServoPositionFunction setServoPosition = new SetServoPositionFunction(manip); setServoPosition.setName("setServoPosition"); setServoPosition.setArgCount(2); functions.add(setServoPosition);
        SetServoPowerFunction setServoPower = new SetServoPowerFunction(manip); setServoPower.setName("setServoPower"); setServoPower.setArgCount(2); functions.add(setServoPower);
        GetMotorPowerFunction getMotorPower = new GetMotorPowerFunction(manip); getMotorPower.setName("getMotorPower"); getMotorPower.setArgCount(1); functions.add(getMotorPower);
        GetServoPowerFunction getServoPower = new GetServoPowerFunction(manip); getServoPower.setName("getServoPower"); getServoPower.setArgCount(1); functions.add(getServoPower);
        RunMotorUsingEncoderFunction runMotorUsingEncoder = new RunMotorUsingEncoderFunction(manip); runMotorUsingEncoder.setName("runMotorUsingEncoder"); runMotorUsingEncoder.setArgCount(1); functions.add(runMotorUsingEncoder);
        ResetMotorEncoderFunction resetMotorEncoder = new ResetMotorEncoderFunction(manip); resetMotorEncoder.setName("resetEncoders"); resetMotorEncoder.setArgCount(1); functions.add(resetMotorEncoder);

        GetHSLFunction getHSL = new GetHSLFunction(sense); getHSL.setName("getHSL"); getHSL.setArgCount(1); functions.add(getHSL);
        IsSpecialFunction isSpecial = new IsSpecialFunction(sense); isSpecial.setName("isSpecial"); isSpecial.setArgCount(1); functions.add(isSpecial);

        OmniCalcFunction omniCalc = new OmniCalcFunction(); omniCalc.setName("omniCalc"); omniCalc.setArgCount(3); functions.add(omniCalc);
        ProportionalPIDFunction proportionalPID = new ProportionalPIDFunction(); proportionalPID.setName("proportionalPID"); proportionalPID.setArgCount(2); functions.add(proportionalPID);
        DeltaFunction delta = new DeltaFunction(); delta.setName("delta"); delta.setArgCount(2); functions.add(delta);
        NormalizeArrayFunction normalizeArray = new NormalizeArrayFunction(); normalizeArray.setName("normalizeArray"); normalizeArray.setArgCount(1); functions.add(normalizeArray);
        CartesianToPolarFunction cartesianToPolar = new CartesianToPolarFunction(); cartesianToPolar.setName("cartesianToPolar"); cartesianToPolar.setArgCount(2); functions.add(cartesianToPolar);
        RoundToPointFunction roundToPoint = new RoundToPointFunction(); roundToPoint.setName("roundToPoint"); roundToPoint.setArgCount(2); functions.add(roundToPoint);
        PolarToCartesianFunction polarToCartesian = new PolarToCartesianFunction(); polarToCartesian.setName("polarToCartesian"); polarToCartesian.setArgCount(2); functions.add(polarToCartesian);
        HighestValueFunction highestValue = new HighestValueFunction(); highestValue.setName("highestValue"); highestValue.setArgCount(1); functions.add(highestValue);
        EncoderDistanceFunction encoderDistance = new EncoderDistanceFunction(); encoderDistance.setName("encoderDistance"); encoderDistance.setArgCount(1); functions.add(encoderDistance);

        LogFunction log = new LogFunction(); log.setName("log"); log.setArgCount(1); functions.add(log);

        return functions.toArray(new Function[0]);
    }
}

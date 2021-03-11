package net.coleh.autoautolanguageplugin.completion;

public class AutoautoBuiltinFunctionCompletions {
    public static class AutoautoBuiltinFunctionRecord {
        String name;
        int argCount;
        String[] argNames;
        String definingClass;

        public AutoautoBuiltinFunctionRecord(String name, int argCount, String[] argNames, String definingClass) {
            this.name = name;
            this.argCount = argCount;
            this.argNames = argNames;
            this.definingClass = definingClass;
        }

        public AutoautoBuiltinFunctionRecord(String name, int argCount, String definingClass) {
            this.name = name;
            this.argCount = argCount;
            this.argNames = new String[argCount];
            this.definingClass = definingClass;
        }

        public String getName() {
            return name;
        }

        public int getArgCount() {
            return argCount;
        }

        public String[] getArgNames() {
            return argNames;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setArgCount(int argCount) {
            this.argCount = argCount;
        }

        public void setArgNames(String[] argNames) {
            this.argNames = argNames;
        }

        public String getDefiningClass() {
            return this.definingClass;
        }
    }


    public static String[] names = new String[] { "driveOmni", "stopDrive", "getScale", "downScale", "runUsingEncoders", "resetEncoders", "driveVertical", "runToPosition", "upScale", "driveRaw", "driveOmniExponential", "runWithOutEncoders", "setTargetPositions", "getMotorPositions", "driveWithVertical", "getTicks", "setMotorPower", "setServoPosition", "setServoPower", "getMotorPower", "getServoPower", "runMotorUsingEncoder", "resetEncoders", "getHSL", "isSpecial", "omniCalc", "proportionalPID", "delta", "normalizeArray", "cartesianToPolar", "roundToPoint", "polarToCartesian", "highestValue", "encoderDistance", "getThirdAngleOrientation", "log" };
    public static AutoautoBuiltinFunctionRecord[] records = new AutoautoBuiltinFunctionRecord[] {
            new AutoautoBuiltinFunctionRecord("driveOmni", 3, new String[] {"vertical", "horizontal", "rotational"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("stopDrive", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("getScale", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("downScale", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("runUsingEncoders", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("resetEncoders", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("driveVertical", 2, new String[] {"power", "distance"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("runToPosition", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("upScale", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("driveRaw", 4, new String[] {"fl", "fr", "br", "bl"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("driveOmniExponential", 1, new String[] {"powersArray"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("runWithOutEncoders", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("setTargetPositions", 4, new String[] {"fl", "fr", "br", "bl"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("getMotorPositions", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("driveWithVertical", 2, new String[] {"power", "distance"}, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("getTicks", 0, "MovementManager"),
            new AutoautoBuiltinFunctionRecord("setMotorPower", 2, new String[] {"motorId", "power"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("setServoPosition", 2, new String[] {"servoId", "position"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("setServoPower", 2, new String[] {"servoId", "power"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("getMotorPower", 1, new String[] {"motorId"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("getServoPower", 1, new String[] {"servoId"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("runMotorUsingEncoder", 1, new String[] {"motorId"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("resetEncoders", 1, new String[] {"motorId"}, "ManipulationManager"),
            new AutoautoBuiltinFunctionRecord("getHSL", 1, new String[] {"sensorId"}, "SensorManager"),
            new AutoautoBuiltinFunctionRecord("isSpecial", 1, new String[] {"sensorId"}, "SensorManager"),
            new AutoautoBuiltinFunctionRecord("omniCalc", 3, new String[] {"vertical", "horizontal", "rotational"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("proportionalPID", 3, new String[] {"current", "target", "kP"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("delta", 2, new String[] {"a", "b"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("normalizeArray", 1, new String[] {"valueArray"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("cartesianToPolar", 2, new String[] {"x", "y"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("roundToPoint", 2, new String[] {"value", "point"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("polarToCartesian", 2, new String[] {"angle", "magnitude"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("highestValue", 1, new String[] {"valuesArray"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("encoderDistance", 1, new String[] {"motorId"}, "PaulMath"),
            new AutoautoBuiltinFunctionRecord("getThirdAngleOrientation", 0, "ImuManager"),
            new AutoautoBuiltinFunctionRecord("log", 1, new String[] {"logValue"}, "<Autoauto-provided function>")
    };
}

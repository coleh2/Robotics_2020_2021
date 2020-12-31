package org.firstinspires.ftc.teamcode.auxilary;

import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class AutoState {
    public static final float CONTINUE_TO = -1f;
    public static final float DRIVE_OMNI = 1f;
    public static final float MANIP_SERVO = 2f;
    public static final float MANIP_MOTOR = 3f;

    public ContinueCondition condition;
    public StateAction stateAction;

    public AutoState(ContinueCondition condition, StateAction action) {
        this.condition = condition;
        this.stateAction = action;
    }

    public float[][] step() {
        float[] action = stateAction.value;
        float type = stateAction.typeInt();
        if(condition.shouldContinue()) {
            type = 0f;
            action = new float[] {(float)condition.moveTo};
        }

        return new float[][] {
                new float[] {type},
                action
        };

    }

    public static class StateAction {
        public static enum ActionType { DRIVE, MANIP_SERVO, MANIP_MOTOR };

        public float[] value;
        public int target;
        public String targetName;
        public ActionType type;
        public int typeInt() {
            return type.ordinal() + 1;
        }
    }

    public static class ContinueCondition {
        public static enum ContinueType { TIME, SENSOR, INSTANT }

        public int moveTo;
        public ContinueType type;
        public String sensorProp;
        public Sensor sensor;
        public long timeMs;
        public float target;

        public LogicalComparison comparison;

        private long startMs;

        public boolean shouldContinue() {
            switch(type) {
                case TIME:
                    return System.currentTimeMillis() - startMs > timeMs;
                case SENSOR:
                    //TODO: Implement sensors!
                    return comparison.res((float) sensor.getValue(sensorProp), target);
                case INSTANT:
                    return true;
                default:
                    return false;
            }
        }

        private static class LogicalComparison {
            private static enum Type {LT, LTE, EQ, GTE, GT};
            private Type t;

            public LogicalComparison(String t) {
                this.t = Type.valueOf(t);
            }

            public boolean res(float a, float b) {
                switch(t) {
                    case LT: return a < b;
                    case LTE: return a <= b;
                    case EQ: return a == b;
                    case GTE: return a >= b;
                    case GT: return a > b;
                }
                return false;
            }
        }

        private static class Sensor {
            private HardwareDevice sensor;

            public Sensor(HardwareDevice s) {
                this.sensor = s;
            }

            public double getValue(String prop) {
                if(sensor instanceof AccelerationSensor) {
                    switch(prop) {
                        case "accelX": return ((AccelerationSensor) sensor).getAcceleration().xAccel;
                        case "yAccel": return ((AccelerationSensor) sensor).getAcceleration().yAccel;
                        case "zAccel": return ((AccelerationSensor) sensor).getAcceleration().zAccel;
                    }
                }
                return 0;
            }
        }
    }
}

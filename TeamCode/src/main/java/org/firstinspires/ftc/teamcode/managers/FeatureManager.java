package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.autonomous.*;
import org.firstinspires.ftc.teamcode.auxilary.*;
import org.firstinspires.ftc.teamcode.managers.*;
import org.firstinspires.ftc.teamcode.teleop.*;

import java.io.OutputStream;
import java.io.PrintStream;

public class FeatureManager {

    public final static float SPEED = 0.6f;
    public final static int TICK_PER_ROT = 1680;

    public final static float P = 0.03f;
    public final static double ENCODER_CPR = 1680;
    public final static double GEAR_RATIO = 1;
    public final static double WHEEL_DIAMETER = 4;
    public final static double SLIP = 0.7;
    public final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public final static float EXPONENTIAL_SCALAR = 3f;

    public final static int INPUT_DOUBLECLICK_TIME = 400;

    public static final FeatureManager.Logger logger = new Logger();

    public static class Logger {
        public Telemetry.Log backend;
        public PrintStream fallbackBackend;
        private boolean usesFallback;
        public void setBackend(Telemetry.Log log) {
            backend = log;
            usesFallback = false;
        }

        public void setBackend(PrintStream log) {
            fallbackBackend = log;
            usesFallback = true;
        }

        public Logger() {this.fallbackBackend = System.out; }

        public Logger(Telemetry.Log log) { this.backend = log;}

        public void log(String l) {
            if(usesFallback) fallbackBackend.println(l);
            else backend.add(l);
        }
        public void println(String l) {
            log(l);
        }
        public void add(String l) {
            log(l);
        }
    }
}

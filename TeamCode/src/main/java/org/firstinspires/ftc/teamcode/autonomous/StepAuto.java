package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.managers.*;

enum step {
    START, MOVE1, MOVE2, MOVE3, MOVE4, MOVE5, MOVE5and5, MOVE6, MOVE7, MOVE8, MOVE9, MOVE10, MOVE11, MOVE12, MOVE13, MOVE14, MOVE15, MOVE16, MOVE17, MOVE18, MOVE19, MOVE20, MOVE21, MOVE22, MOVE23, MOVE24, MOVE25, MOVE26, MOVE27, MOVE28, MOVE29, MOVE30, MOVE31, MOVE32,
    THREADONE1, THREADONE2, THREADONE3, THREADONE4, THREADONE41, THREADONE42, THREADONE5, THREADONE6, THREADONE7,
    THREADTWO1, THREADTWO2, THREADTWO3, THREADTWO4, THREADTWO41, THREADTWO42, THREADTWO5, THREADTWO6, THREADTWO7,
    THREADTHREE1, THREADTHREE2, THREADTHREE3, THREADTHREE4,THREADTHREE41, THREADTHREE42, THREADTHREE5, THREADTHREE6, THREADTHREE7;
}

@Autonomous
public class StepAuto extends OpMode {
    ElapsedTime timer;
    step currentStep = step.START;

    int numberCalled = 0;
    double referPoint = 0;

    MovementManager driver;
    ManipulationManager limbs;
    private int ticksNumberCalled;
    private long ticksReferPoint;

    public step getNext() {
        step[] x = step.values();
        int i = 0;
        for (; x[i] != currentStep; i++);
        i++;
        i %= x.length;
        return x[i];
    }

    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
    }
    public void loop() {

    }

    void nextStepTicks(int tarTicks, int cTicks) {

        if(currentStep == null)
            currentStep = step.START;



        ticksNumberCalled++;
        if(ticksNumberCalled == 1) {
            ticksReferPoint = cTicks;
        }

        if (Math.abs(cTicks - ticksReferPoint) >= Math.abs(tarTicks)) {
            ticksNumberCalled = 0;
            currentStep = getNext();
        }
    }

    void nextStep(int milliseconds) {
        if(timer == null)
            timer = new ElapsedTime();
        if(currentStep == null)
            currentStep = step.START;



        numberCalled++;
        if(numberCalled == 1) {
            referPoint = timer.milliseconds();
        }

        if (timer.milliseconds() - referPoint >= milliseconds) {
            numberCalled = 0;
            currentStep = getNext();
        }
    }

    void nextStepStop(int milliseconds) {

        if(timer == null)
            timer = new ElapsedTime();
        if(currentStep == null)
            currentStep = step.START;



        numberCalled++;
        if(numberCalled == 1) {
            referPoint = timer.milliseconds();
        }

        if (timer.milliseconds() - referPoint >= milliseconds) {
            numberCalled = 0;
            currentStep = getNext();
        }
    }

    long delayRunTime;
    boolean delaying;
    public void wait(int delay, LinearOpMode opmode) {

        if(!delaying) {
            delaying = true;
            delayRunTime = System.currentTimeMillis();
        }
        while(System.currentTimeMillis() - delayRunTime <= delay && opmode.opModeIsActive()) {}

        delaying = false;
    }
}
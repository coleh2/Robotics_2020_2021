package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.AutoState;
import org.firstinspires.ftc.teamcode.auxilary.StateMachine;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

@Autonomous
public class StateMachineAutoOfficial extends OpMode {
    StateMachine stateMachine;
    MovementManager driver;
    ManipulationManager limbs;
    public void init() {
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        limbs = new ManipulationManager(
                new CRServo[] {
                        hardwareMap.get(CRServo.class, "shooterArm")
                },
                new String[] {
                        "shooterArm"
                },
                new Servo[] {},
                new String[] {},
                new DcMotor[] {
                        hardwareMap.get(DcMotor.class, "drum"),
                        hardwareMap.get(DcMotor.class, "intake"),
                        hardwareMap.get(DcMotor.class, "flywheelRight"),
                        hardwareMap.get(DcMotor.class, "flywheelLeft")
                },
                new String[] {
                        "drum",
                        "intake",
                        "flywheelRight",
                        "flywheelLeft"
                }
        );
        stateMachine = new StateMachine(new AutoState[] {
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 1), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {1f, -1f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 2), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {2f, 1f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 3000L, 3), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0.5f,0f,0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L, 4), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L,5), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L, 6), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L, 7), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L, 8), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L,9), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 500L, 10), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0.5f, 0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 11), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0f, 0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 12), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {1f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 12), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {2f, 0f}))

        });

        /*
            ... init limbs and driver, not shown ...
         */
    }

    public void loop() {
        StateMachine.StateMachineExportAction actionToDoNow = stateMachine.getNextFrameMovement();

        switch(actionToDoNow.type) {
            case DRIVE_OMNI:
                driver.driveOmni(actionToDoNow.action);
                break;
            case MANIP_SERVO:
                limbs.setServoPower((int)actionToDoNow.action[0], actionToDoNow.action[1]);
                break;
            case MANIP_MOTOR:
                limbs.setMotorPower((int)actionToDoNow.action[0], actionToDoNow.action[1]);
                break;
        }
    }
}

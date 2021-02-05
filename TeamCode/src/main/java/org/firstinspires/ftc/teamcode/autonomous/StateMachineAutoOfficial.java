package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.auxilary.AutoState;
import org.firstinspires.ftc.teamcode.auxilary.StateMachine;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
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
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 1), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {2f, -1f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 2), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {3f, 1f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 3000L, 3), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0.6f,0.6f,-0.6f,-0.6f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1001L, 4), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1000L,5), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1002L, 6), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1003L, 7), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1004L, 8), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1005L,9), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1006L, 10), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0.6f,0.6f,-0.6f,-0.6f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 11), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {0f, 0f, 0f,0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 12), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {2f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.INSTANT, 12), new AutoState.StateAction(AutoState.ActionType.MANIP_MOTOR, new float[] {3f, 0f}))

        });

        /*
            ... init limbs and driver, not shown ...
         */
    }

    public void loop() {
        StateMachine.StateMachineExportAction actionToDoNow = stateMachine.getNextFrameMovement();

        switch(actionToDoNow.type) {
            case DRIVE_OMNI:
                driver.driveRaw(actionToDoNow.action[0], actionToDoNow.action[1], actionToDoNow.action[2], actionToDoNow.action[3]);
                break;
            case MANIP_SERVO:
                limbs.setServoPower((int)actionToDoNow.action[0], actionToDoNow.action[1]);
                break;
            case MANIP_MOTOR:
                limbs.setMotorPower((int)actionToDoNow.action[0], actionToDoNow.action[1]);
                break;
        }
        telemetry.addData("StateType: ", actionToDoNow.type);
        telemetry.addData("StateIdex: ", stateMachine.currentState);
        telemetry.addData("Star Time: ", stateMachine.states[stateMachine.currentState].condition.startMs);
        telemetry.addData(" TimeMs: ", stateMachine.states[stateMachine.currentState].condition.timeMs);
        telemetry.addData(" Time Elalskljcbka s: ", System.currentTimeMillis() - stateMachine.states[2].condition.startMs);

        telemetry.addData(" Target: ", stateMachine.states[stateMachine.currentState].condition.target);




        telemetry.addData("Drum Power", limbs.getMotorPower("drum"));
        telemetry.addData("Intake Power", limbs.getMotorPower("intake"));
        telemetry.addData("Flywheel Right Power", limbs.getMotorPower("flywheelRight"));
        telemetry.addData("Flywheel Left Power", limbs.getMotorPower("flywheelLeft"));

        telemetry.addData("speed: ", driver.getScale());
        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());


    }
}

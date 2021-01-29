package org.firstinspires.ftc.teamcode.testOpmodes;

import org.firstinspires.ftc.teamcode.auxilary.AutoState;
import org.firstinspires.ftc.teamcode.auxilary.StateMachine;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

public class StateMachineAuto {
    StateMachine stateMachine;
    MovementManager driver;
    ManipulationManager limbs;

    public void init() {
        stateMachine = new StateMachine(new AutoState[] {
           new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1500L, 1), new AutoState.StateAction(AutoState.ActionType.DRIVE, new float[] {1f, 0f, 0f})),
                new AutoState(new AutoState.ContinueCondition(AutoState.ContinueType.TIME, 1500L, 1), new AutoState.StateAction(AutoState.ActionType.MANIP_SERVO, new float[] {0f, 0.7f}))
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
        }
    }
}

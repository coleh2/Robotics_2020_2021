package org.firstinspires.ftc.teamcode.auxilary;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadState {
    public float left_stick_x;
    public float left_stick_y;
    public float right_stick_x;
    public float right_stick_y;
    public float left_trigger;
    public float right_trigger;
    public boolean left_bumper;
    public boolean right_bumper;
    public boolean a;
    public boolean b;
    public boolean x;
    public boolean y;
    public boolean dpad_left;
    public boolean dpad_right;
    public boolean dpad_up;
    public boolean dpad_down;

    public long time;

    public GamepadState history;

    public GamepadState getHistory() {
        if(history == null) return GamepadState.initialState();
        else return history;
    }

    public GamepadState() {}

    public static GamepadState initialState() {
        GamepadState st = new GamepadState();

        st.left_stick_x = 0f;
        st.left_stick_y = 0f;
        st.right_stick_x = 0f;
        st.right_stick_y = 0f;
        st.left_trigger = 0f;
        st.right_trigger = 0f;
        st.left_bumper = false;
        st.right_bumper = false;
        st.a = false;
        st.b = false;
        st.x = false;
        st.y = false;
        st.dpad_left = false;
        st.dpad_right = false;
        st.dpad_up = false;
        st.dpad_down = false;

        st.time = 0;

        return st;
    }

    public GamepadState(Gamepad gamepad, GamepadState history) {
        this.left_stick_x = gamepad.left_stick_x;
        this.left_stick_y = gamepad.left_stick_y;
        this.right_stick_x = gamepad.right_stick_x;
        this.right_stick_y = gamepad.right_stick_y;
        this.left_trigger = gamepad.left_trigger;
        this.right_trigger = gamepad.right_trigger;
        this.left_bumper = gamepad.left_bumper;
        this.right_bumper = gamepad.right_bumper;
        this.a = gamepad.a;
        this.b = gamepad.b;
        this.x = gamepad.x;
        this.y = gamepad.y;
        this.dpad_left = gamepad.dpad_left;
        this.dpad_right = gamepad.dpad_right;
        this.dpad_up = gamepad.dpad_up;
        this.dpad_down = gamepad.dpad_down;

        this.time = System.currentTimeMillis();

        this.history = history;
        history.history = null;
    }

    /**
     * Get the current state of a button on the gamepad
     * @param button The name of the button to get the state of
     * @return The current state of the button
     */
    public float getButtonState(String button) {
        switch(button) {
            case "left_stick_x":
                return this.left_stick_x;
            case "left_stick_y":
                return this.left_stick_y;

            case "right_stick_x":
                return this.right_stick_x;
            case "right_stick_y":
                return this.right_stick_y;

            case "left_trigger":
                return this.left_trigger;
            case "right_trigger":
                return this.right_trigger;

            case "left_bumper":
                return this.left_bumper?1f:0f;
            case "right_bumper":
                return this.right_bumper?1f:0f;

            case "a":
                return this.a?1f:0f;
            case "b":
                return this.b?1f:0f;
            case "x":
                return this.x?1f:0f;
            case "y":
                return this.y?1f:0f;

            case "dpad_left":
                return this.dpad_left?1f:0f;
            case "dpad_right":
                return this.dpad_right?1f:0f;
            case "dpad_up":
                return this.dpad_up?1f:0f;
            case "dpad_down":
                return this.dpad_down?1f:0f;

            default:
                return 0f;
        }
    }
}
package org.firstinspires.ftc.teamcode.managers;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.auxilary.ControlModel;
import org.firstinspires.ftc.teamcode.auxilary.GamepadState;
import org.firstinspires.ftc.teamcode.auxilary.controlmaps.ControlMap;
import org.firstinspires.ftc.teamcode.teleop.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handle input (button combos, keybinds, etc.) for gamepads.
 */
public class InputManager extends FeatureManager {
    public Gamepad gamepad;
    public ControlModel controlModel;
    public GamepadState gamepadHistory;

    public static String lastKey;

    public float currentSpeed = 0.6f;

    public boolean dpad_leftPress = false;
    public boolean dpad_rightPress = false;
    public boolean dpad_leftBumper = false;

    public HashMap<String,Float> lastPresses = new HashMap<>();
    public HashMap<String,Boolean> togglePresses = new HashMap<>();

    public InputManager(Gamepad _gamepad, ControlMap _controlMap) {
        this.gamepad = _gamepad;
        this.controlModel = new ControlModel(_controlMap);
        this.gamepadHistory = new GamepadState(gamepad, null);
    }

    public Gamepad getGamepad() {
        return this.gamepad;
    }

    public void update() {
        gamepadHistory = new GamepadState(gamepad, gamepadHistory);
    }

    public float[] getVector(String name) {
        return controlModel.get(name).res(gamepadHistory);
    }

    public boolean getKey(String name) {
        return controlModel.get(name).res(gamepadHistory)[0] != 0;
    }

    public float getScalar(String name) {
        return controlModel.get(name).res(gamepadHistory)[0];
    }


}


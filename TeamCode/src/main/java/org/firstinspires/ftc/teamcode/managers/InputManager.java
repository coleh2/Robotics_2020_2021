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
    public Gamepad gamepad2;

    public ControlModel controlModel;
    public GamepadState gamepadHistory;

    public static String lastKey;

    public float currentSpeed = 0.6f;

    public boolean dpad_leftPress = false;
    public boolean dpad_rightPress = false;
    public boolean dpad_leftBumper = false;

    private long explosion;

    public HashMap<String,Float> lastPresses = new HashMap<>();
    public HashMap<String,Boolean> togglePresses = new HashMap<>();

    public InputManager(Gamepad _gamepad, ControlMap _controlMap) {
        this.gamepad = _gamepad;
        this.controlModel = new ControlModel(_controlMap);
        this.gamepadHistory = new GamepadState(gamepad, null);

        this.explosion = System.currentTimeMillis() + 60_000;
    }

    public InputManager(Gamepad _gamepad, Gamepad _gamepad2, ControlMap _controlMap) {
        this.gamepad = _gamepad;
        this.gamepad2 = _gamepad2;

        this.controlModel = new ControlModel(_controlMap);
        this.gamepadHistory = new GamepadState(gamepad, null);

        this.explosion = System.currentTimeMillis() + 60_000;
    }

    public Gamepad getGamepad() {
        return this.gamepad;
    }
    public Gamepad getGamepad2() {
        return this.gamepad2;
    }

    public void update() {
        if(gamepad2 != null) {
            FeatureManager.logger.log("if this prints, input updating 2 gamepad");
            gamepadHistory = new GamepadState(gamepad, gamepad2, gamepadHistory);
            FeatureManager.logger.log("if this doesn't print, input not updating 2 gamepads problems");
        }
        else {
            FeatureManager.logger.log("if this prints, input updating only 1 gamepad");
            gamepadHistory = new GamepadState(gamepad, gamepadHistory);
            FeatureManager.logger.log("if this doesn't prints, input updating only 1 gamepad problems");
        }

    }

    public float[] getVector(String name) {
        if(controlModel.get(name) == null) throw new IllegalArgumentException("Unknown control name " + name);
        return controlModel.get(name).res(gamepadHistory);

    }

    public boolean getBoolean(String name) {
        if(controlModel.get(name) == null) throw new IllegalArgumentException("Unknown control name " + name);
        return controlModel.get(name).res(gamepadHistory)[0] != 0;
    }

    public float getScalar(String name) {
        if(controlModel.get(name) == null) throw new IllegalArgumentException("Unknown control name " + name);
        return controlModel.get(name).res(gamepadHistory)[0];
    }

    public ControlModel.Control getControl(String name) {
        if(controlModel.get(name) == null) throw new IllegalArgumentException("Unknown control name " + name);
        return controlModel.get(name);
    }


}


package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.keyboard.NetKeyboard;
import com.stuypulse.stuylib.input.LambdaButton;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class takes data from a Network Keyboard and puts it into a gamepad
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetKeyGamepad extends Gamepad {

    /**
     * Underlying Network Keyboard
     */
    private NetKeyboard mKeyboard;

    /**
     * Opens Network Keyboard Gamepad
     * 
     * @param port virtual port
     */
    public NetKeyGamepad(int port) {
        mKeyboard = new NetKeyboard(port);
    }

    /**
     * Get key name as WPI Button
     * 
     * @param key key name
     * @return WPI Button
     */
    public final Button getKey(String key) {
        return new LambdaButton(() -> this.getRawKey(key));
    }

    /**
     * Get boolean for if key is pressed
     * 
     * @param key key name
     * @return if key is pressed
     */
    public final boolean getRawKey(String key) {
        return mKeyboard.getKey(key);
    }

    /**********************/
    /*** CONTROL STICKS ***/
    /**********************/
    @Override
    public double getLeftX() {
        return (getRawKey("d") ? 1 : 0) + (getRawKey("a") ? -1 : 0);
    }

    @Override
    public double getLeftY() {
        return (getRawKey("w") ? 1 : 0) + (getRawKey("s") ? -1 : 0);
    }

    @Override
    public double getRightX() {
        return (getRawKey("l") ? 1 : 0) + (getRawKey("j") ? -1 : 0);
    }

    @Override
    public double getRightY() {
        return (getRawKey("i") ? 1 : 0) + (getRawKey("k") ? -1 : 0);
    }

    /**********************/
    /*** D-PAD CONTROLS ***/
    /**********************/
    @Override
    public boolean getRawDPadUp() {
        return getRawKey("up");
    }

    @Override
    public boolean getRawDPadDown() {
        return getRawKey("down");
    }

    @Override
    public boolean getRawDPadLeft() {
        return getRawKey("left");
    }

    @Override
    public boolean getRawDPadRight() {
        return getRawKey("right");
    }
}
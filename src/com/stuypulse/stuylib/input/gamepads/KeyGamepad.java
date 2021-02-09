/**
 * Test script for a gamepad. See what your gamepad does.
 *
 * public static void main(String... args) throws Exception {
        Gamepad gamepad = new Gamepad();

        while (true) {
            System.out.println(gamepad.getLeftStick());
            System.out.println(gamepad.getLeftTrigger());
            System.out.println(gamepad.getRawLeftBumper());

            System.out.println(gamepad.getRightStick());
            System.out.println(gamepad.getRightTrigger());
            System.out.println(gamepad.getRawRightBumper());

            System.out.println(gamepad.getRawDPadLeft());
            System.out.println(gamepad.getRawDPadRight());
            System.out.println(gamepad.getRawDPadUp());
            System.out.println(gamepad.getRawDPadDown());

            System.out.println(gamepad.getRawLeftButton());
            System.out.println(gamepad.getRawRightButton());
            System.out.println(gamepad.getRawTopButton());
            System.out.println(gamepad.getRawBottomButton());

            System.out.println(gamepad.getRawSelectButton());
            System.out.println(gamepad.getRawStartButton());
            System.out.println(gamepad.getRawOptionButton());

            Thread.sleep(100);
        }
    }
 */
package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

/**
 * <p>
 * This is a base class for a gamepad controlled via keyboard. This class controls the keymappings.
 * <i>All a child controller needs to do is provide a way to check if any keyboard button is
 * pressed.</i>
 * </p>
 *
 * <ul>
 * <li><b>NOTE:</b> all input types are handled except rumble</li>
 * <li><b>NOTE:</b> it is suggested to use this with filtered because your values will always be -1,
 * 0, 1. Filtering is not included for simplicity</li>
 * </ul>
 * <br>
 *
 * @see IFilter
 * @author Myles Pasetsky (selym3)
 */
public class KeyGamepad extends Gamepad {

    KeyConfig config;

    public KeyGamepad() {
        this(new KeyConfig());
    }

    public KeyGamepad(KeyConfig config) {
        this.config = config;
    }
    
    public KeyGamepad setKeyConfig(KeyConfig config) {
        this.config = config;
        return this;
    }

    protected boolean getKey(String name) {
        return false;
    }

    private final int getDirection(String pos, String neg) {
        return (getKey(pos) ? 1 : 0) - (getKey(neg) ? 1 : 0);
    }

    public double getLeftX() {
        return(getDirection(config.get(0), config.get(1)));
    }

    public double getLeftY() {
        return(getDirection(config.get(2), config.get(3)));
    }

    // Right Stick //
    public double getRightX() {
        return(getDirection(config.get(4), config.get(5)));
    }

    public double getRightY() {
        return(getDirection(config.get(6), config.get(7)));
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return getKey(config.get(8));
    }

    public boolean getRawDPadDown() {
        return getKey(config.get(9));
    }

    public boolean getRawDPadLeft() {
        return getKey(config.get(10));
    }

    public boolean getRawDPadRight() {
        return getKey(config.get(11));
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return getKey(config.get(12));
    }

    public boolean getRawRightBumper() {
        return getKey(config.get(13));
    }

    // Triggers //
    public double getLeftTrigger() {
        return(getKey(config.get(14)) ? 1 : 0);
    }

    public double getRightTrigger() {
        return(getKey(config.get(15)) ? 1 : 0);
    }

    // Face Buttons //
    public boolean getRawLeftButton() {
        return getKey(config.get(16));
    }

    public boolean getRawRightButton() {
        return getKey(config.get(17));
    }

    public boolean getRawTopButton() {
        return getKey(config.get(18));
    }

    public boolean getRawBottomButton() {
        return getKey(config.get(19));
    }

    // Start / Select / Option //
    public boolean getRawSelectButton() {
        return getKey(config.get(20));
    }

    public boolean getRawStartButton() {
        return getKey(config.get(21));
    }

    public boolean getRawOptionButton() {
        return getKey(config.get(22));
    }

    // Analog Stick Buttons //
    public boolean getRawLeftAnalogButton() {
        return getKey(config.get(23));
    }

    public boolean getRawRightAnalogButton() {
        return getKey(config.get(24));
    }
}
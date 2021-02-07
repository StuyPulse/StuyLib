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

    public KeyGamepad() {
    }

    protected boolean getKey(String name) {
        return false;
    }

    private final int getDirection(String pos, String neg) {
        return (getKey(pos) ? 1 : 0) - (getKey(neg) ? 1 : 0);
    }

    public double getLeftX() {
        return(getDirection("d", "a"));
    }

    public double getLeftY() {
        return(getDirection("w", "s"));
    }

    // Right Stick //
    public double getRightX() {
        return(getDirection("l", "j"));
    }

    public double getRightY() {
        return(getDirection("i", "k"));
    }

    // D-Pad //
    public boolean getRawDPadUp() {
        return getKey("numpad-8") || getKey("up");
    }

    public boolean getRawDPadDown() {
        return getKey("numpad-2") || getKey("down");
    }

    public boolean getRawDPadLeft() {
        return getKey("numpad-4") || getKey("left");
    }

    public boolean getRawDPadRight() {
        return getKey("numpad-6") || getKey("right");
    }

    // Bumpers //
    public boolean getRawLeftBumper() {
        return getKey("e");
    }

    public boolean getRawRightBumper() {
        return getKey("u");
    }

    // Triggers //
    public double getLeftTrigger() {
        return(getKey("q") ? 1 : 0);
    }

    public double getRightTrigger() {
        return(getKey("o") ? 1 : 0);
    }

    // Face Buttons //
    public boolean getRawLeftButton() {
        return getKey("z");
    }

    public boolean getRawRightButton() {
        return getKey("x");
    }

    public boolean getRawTopButton() {
        return getKey("c");
    }

    public boolean getRawBottomButton() {
        return getKey("v");
    }

    // Start / Select / Option //
    public boolean getRawSelectButton() {
        return getKey("r");
    }

    public boolean getRawStartButton() {
        return getKey("t");
    }

    public boolean getRawOptionButton() {
        return getKey("y");
    }

    // Analog Stick Buttons //
    public boolean getRawLeftAnalogButton() {
        return getKey("f");
    }

    public boolean getRawRightAnalogButton() {
        return getKey("h");
    }

}

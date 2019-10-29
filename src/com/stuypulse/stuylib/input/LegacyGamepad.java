package com.stuypulse.stuylib.input;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Class for both the Logitech Dual Action 2 Gamepad and the Logitech Gamepad
 * F310. This class also works with the Logitech Wireless Gamepad F710, and on
 * the PS4 Wireless controller (the PS4 also comes with an extra button and 2
 * extra axis for the bumpers)
 *
 * @author wangmeister
 */

@Deprecated
public class LegacyGamepad extends Joystick {
    // Threshold for whether we're "pressing" the trigger
    private static final double RAW_TRIGGER_PRESS_AXIS_THRESHOLD = 0.1;

    // On the back of the logitech controller there is a switch.
    // "X" gives you access to the axis inputs
    public enum GamepadSwitchMode {
        SWITCH_X, SWITCH_D, PS4, // PS4 Controller Enum.
        AUTO_DETECT
    }

    private GamepadSwitchMode switchMode;
    private int port;
    private int controllerType;

    public LegacyGamepad(int port, GamepadSwitchMode switchMode) {
        super(port);
        this.port = port;
        if (switchMode == GamepadSwitchMode.AUTO_DETECT) {
            resetGamepadType();
        } else {
            this.switchMode = switchMode;
        }
    }

    // Defaults to D mode (for backwards compatibility)
    public LegacyGamepad(int port) {
        this(port, GamepadSwitchMode.SWITCH_D);
    }

    /**
     * Resets the type of the gamepad (Not expensive)
     */
    public void resetGamepadType() {
        controllerType = DriverStation.getInstance().getJoystickType(port);
        switch (controllerType) {
            case 1:
                switchMode = GamepadSwitchMode.SWITCH_X;
                break;
            case 21:
                switchMode = GamepadSwitchMode.PS4;
                break;
        default:
            // Make sure the code doesn't break when this gamepad is called
            // SWITCH_D has a controller type of 20
            switchMode = GamepadSwitchMode.SWITCH_D;
        }
    }

    /**
     * The left analog stick x-axis.
     *
     * @return value of left analog x-axis
     */
    public double getLeftX() {
        return getRawAxis(0);
    }

    /**
     * The left analog stick y-axis.
     *
     * @return value of left analog y-axis (pushing stick up is positive)
     */
    public double getLeftY() {
        return -getRawAxis(1);
    }

    /**
     * The right analog stick x-axis.
     *
     * @return value of right analog x-axis
     */
    public double getRightX() {
        switch (switchMode) {
        case SWITCH_D:
            return getRawAxis(4);
        case SWITCH_X:
            return getRawAxis(4);
        case PS4:
            return getRawAxis(2);
        default:
            return 0;
        }
    }

    /**
     * The right analog stick y-axis.
     *
     * @return value of right analog y-axis (pushing stick up is positive)
     */
    public double getRightY() {
        switch (switchMode) {
        case SWITCH_D:
            return -getRawAxis(3);
        case SWITCH_X:
        case PS4:
            return -getRawAxis(5);
        default:
            return 0;
        }
    }

    /**
     * The upper d-pad button.
     *
     * @return if upper d-pad button is pressed
     */
    public boolean getRawDPadUp() {
        return getPOV() == 0;
    }

    public DPadButton getDPadUp() {
        return new DPadButton(this, DPadButton.Direction.UP);
    }

    /**
     * The lower d-pad button.
     *
     * @return if the lower d-pad button is pressed
     */
    public boolean getRawDPadDown() {
        return getPOV() == 180;
    }

    public DPadButton getDPadDown() {
        return new DPadButton(this, DPadButton.Direction.DOWN);
    }

    /**
     * The left d-pad button.
     *
     * @return if the left d-pad button is pressed
     */
    public boolean getRawDPadLeft() {
        return getPOV() == 270;
    }

    public DPadButton getDPadLeft() {
        return new DPadButton(this, DPadButton.Direction.LEFT);
    }

    /**
     * The right d-pad button.
     *
     * @return if the right d-pad button is pressed
     */
    public boolean getRawDPadRight() {
        return getPOV() == 90;
    }

    public DPadButton getDPadRight() {
        return new DPadButton(this, DPadButton.Direction.RIGHT);
    }

    /**
     * The left bumper.
     *
     * @return if the left bumper is pressed
     */
    public boolean getRawLeftBumper() {
        return getRawButton(5);
    }

    public JoystickButton getLeftBumper() {
        return new JoystickButton(this, 5);
    }

    /**
     * The right bumper.
     *
     * @return if the right bumper is pressed
     */
    public boolean getRawRightBumper() {
        return getRawButton(6);
    }

    public JoystickButton getRightBumper() {
        return new JoystickButton(this, 6);
    }

    /**
     * The left trigger: analog
     * 
     * @return how far we've pushed the trigger down (0: none, 1: all the way)
     */
    public double getRawLeftTriggerAxis() {
        switch (switchMode) {
        case SWITCH_D:
            // Turn digital input into on/off analog
            return getRawLeftTrigger() ? 1 : 0;
        case SWITCH_X:
            return getRawAxis(2);
        case PS4:
            return (getRawAxis(3) + 1) / 2;
        default:
            return 0;
        }
    }

    /**
     * The left trigger: digital
     *
     * @return if the left trigger is pressed
     */
    public boolean getRawLeftTrigger() {
        switch (switchMode) {
        case SWITCH_D:
            return getRawButton(7);
        case SWITCH_X:
        case PS4:
            return getRawLeftTriggerAxis() > RAW_TRIGGER_PRESS_AXIS_THRESHOLD;
        default:
            return false;
        }
    }

    public Button getLeftTrigger() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 7);
        case SWITCH_X:
            return new LeftTriggerButton(this);
        default:
            return null;
        }
    }

    /**
     * The right trigger: analog
     * 
     * @return how far we've pushed the trigger down (0: none, 1: all the way)
     */
    public double getRawRightTriggerAxis() {
        switch (switchMode) {
        case SWITCH_D:
            // Turn digital input into on/off analog
            return getRawLeftTrigger() ? 1 : 0;
        case SWITCH_X:
            return getRawAxis(3);
        case PS4:
            return (getRawAxis(4) + 1) / 2;
        default:
            return 0;
        }
    }

    /**
     * The left trigger: digital
     *
     * @return if the left trigger is pressed
     */
    public boolean getRawRightTrigger() {
        switch (switchMode) {
        case SWITCH_D:
            return getRawButton(8);
        case SWITCH_X:
        case PS4:
            return getRawRightTriggerAxis() > RAW_TRIGGER_PRESS_AXIS_THRESHOLD;
        default:
            return false;
        }
    }

    public Button getRightTrigger() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 8);
        case SWITCH_X:
            return new RightTriggerButton(this);
        default:
            return null;
        }
    }

    /**
     * The left button of the button group. On some gamepads this is X.
     *
     * @return if the left button is pressed
     */
    public boolean getRawLeftButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(1);
        case SWITCH_X:
            return getRawButton(3);
        default:
            return false;
        }
    }

    public JoystickButton getLeftButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 1);
        case SWITCH_X:
            return new JoystickButton(this, 3);
        default:
            return null;
        }
    }

    /**
     * The bottom button of the button group. On some gamepads this is A.
     *
     * @return if the bottom button is pressed
     */
    public boolean getRawBottomButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(2);
        case SWITCH_X:
            return getRawButton(1);
        default:
            return false;
        }
    }

    public JoystickButton getBottomButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 2);
        case SWITCH_X:
            return new JoystickButton(this, 1);
        default:
            return null;
        }
    }

    /**
     * The right button of the button group. On some gamepads this is B.
     *
     * @return if the right button is pressed
     */
    public boolean getRawRightButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(3);
        case SWITCH_X:
            return getRawButton(2);
        default:
            return false;
        }
    }

    public JoystickButton getRightButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 3);
        case SWITCH_X:
            return new JoystickButton(this, 2);
        default:
            return null;
        }
    }

    /**
     * The top button of the button group. On some gamepads this is Y.
     *
     * @return if the top button is pressed
     */
    public boolean getRawTopButton() {
        return getRawButton(4);
    }

    public JoystickButton getTopButton() {
        return new JoystickButton(this, 4);
    }

    /**
     * The central left button. On some gamepads this is the select button.
     *
     * @return if the back button is pressed
     */
    public boolean getRawSelectButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(9);
        case SWITCH_X:
            return getRawButton(7);
        default:
            return false;
        }
    }

    public JoystickButton getSelectButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 9);
        case SWITCH_X:
            return new JoystickButton(this, 7);
        default:
            return null;
        }
    }

    /**
     * The central right button. On some gamepads this is the start button.
     *
     * @return if the start button is pressed
     */
    public boolean getRawStartButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(10);
        case SWITCH_X:
            return getRawButton(8);
        default:
            return false;
        }
    }

    public JoystickButton getStartButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 10);
        case SWITCH_X:
            return new JoystickButton(this, 8);
        default:
            return null;
        }
    }

    /**
     * The click-function of the left analog stick.
     *
     * @return if the left analog stick is being clicked down
     */
    public boolean getRawLeftAnalogButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(11);
        case SWITCH_X:
            return getRawButton(9);
        default:
            return false;
        }
    }

    public JoystickButton getLeftAnalogButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 11);
        case SWITCH_X:
            return new JoystickButton(this, 9);
        default:
            return null;
        }
    }

    /**
     * The click-function of the right analog stick.
     *
     * @return if the right analog stick is being clicked down
     */
    public boolean getRawRightAnalogButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return getRawButton(12);
        case SWITCH_X:
            return getRawButton(10);
        default:
            return false;
        }
    }

    public JoystickButton getRightAnalogButton() {
        switch (switchMode) {
        case SWITCH_D:
        case PS4:
            return new JoystickButton(this, 12);
        case SWITCH_X:
            return new JoystickButton(this, 10);
        default:
            return null;
        }
    }

    public boolean getRawOptionButton() {
        switch (switchMode) {
        case PS4:
            return getRawButton(10);
        case SWITCH_D:
        case SWITCH_X:
        default:
            return false;
        }
    }

    /**
     * DPadButton Class
     * 
     * Lets us treat the D-Pad axis as individual buttons
     */

    @Deprecated
    public static class DPadButton extends Button {
        public static enum Direction {
            UP, DOWN, LEFT, RIGHT
        }

        private LegacyGamepad gamepad;
        private Direction direction;

        public DPadButton(LegacyGamepad gamepad, Direction direction) {
            this.gamepad = gamepad;
            this.direction = direction;
        }

        @Override
        public boolean get() {
            switch (direction) {
            case UP:
                return gamepad.getRawDPadUp();
            case DOWN:
                return gamepad.getRawDPadDown();
            case LEFT:
                return gamepad.getRawDPadLeft();
            case RIGHT:
                return gamepad.getRawDPadRight();
            default: // Never reached
                return false;
            }
        }
    }

    /**
     * LeftTriggerButton Class
     * 
     * Lets us treat the Left trigger as an individual button in SWITCH_X mode
     */

    @Deprecated
    public static class LeftTriggerButton extends Button {
        private LegacyGamepad gamepad;

        public LeftTriggerButton(LegacyGamepad gamepad) {
            this.gamepad = gamepad;
        }

        @Override
        public boolean get() {
            return gamepad.getRawLeftTrigger();
        }
    }

    /**
     * RightTriggerButton Class
     * 
     * Lets us treat the right trigger as an individual button in SWITCH_X mode
     */

    @Deprecated
    public static class RightTriggerButton extends Button {
        private LegacyGamepad gamepad;

        public RightTriggerButton(LegacyGamepad gamepad) {
            this.gamepad = gamepad;
        }

        @Override
        public boolean get() {
            return gamepad.getRawRightTrigger();
        }
    }
    
    public void rumble(double intensity){
        setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
        setRumble(GenericHID.RumbleType.kRightRumble, intensity);
    }
}
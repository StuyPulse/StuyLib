package stuylib.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

import stuylib.util.gamepads.Logitech;
import stuylib.util.gamepads.NullGamepad;
import stuylib.util.gamepads.PS4;

/**
 * An abstract class for using gamepads with different interfaces.
 * By using inheritance, you can implement a new gamepad without
 * messing with switch statements. You do not have to document the
 * implementation as everything is already here
 * 
 * @author Sam B.
 */

public class Gamepad {

    /******************************/
    /*** TRIGGER AXIS THRESHOLD ***/
    /******************************/
    protected static final double TRIGGER_AXIS_THRESHOLD = 2.0 / 16.0;


    /**
     * Initialize Gamepad with joystick
     * @param joy WPI Joystick
     */
    public Gamepad(Joystick joy) {
        this.mJoy = joy;
    }    
    

    /**
    * Initialize Gamepad with port
    * @param joy Joystick port
    */
    public Gamepad(int port) {
        this.mJoy = new Joystick(port);
    }


    public static Gamepad openPort(int port) {
        switch (DriverStation.getInstance().getJoystickType(port)) {
            case 1:
                return new Logitech.XMode(port);
            case 20:
                return new Logitech.DMode(port);
            case 21:
                return new PS4(port);
            default:
                return new NullGamepad(port);
        }
    }


    /**
     * Underlying Joystick Class
     */
    protected Joystick mJoy;


    /***********************/
    /*** GETTERS/SETTERS ***/
    /***********************/

    /**
     * Get underlying joystick
     * @return Underlying joystick
     */
    public final Joystick getJoystick() {
        return this.mJoy;
    }

    /**
     * Get button from underlying joystick
     * @param button Joystick button id
     * @return the value of the button
     */
    public final boolean getRawButton(int button) {
        return getJoystick().getRawButton(button);
    }

    /**
     * Get button from underlying joystick
     * @param button Joystick button id
     * @return the value of the button
     */
    public final Button getButton(int button) {
        return new GamepadButton(() -> getRawButton(button));
    }

    /**
     * Get axis from underlying joystick
     * @param axis Joystick axis id
     * @return the value of the axis
     */
    public final double getRawAxis(int axis) {
        return getJoystick().getRawAxis(axis);
    }


    /**************************/
    /*** LEFT CONTROL STICK ***/
    /**************************/

    /**
     * The left analog stick x-axis.
     * @return value of left analog x-axis
     */
    public double getLeftX() { return 0.0; }

    /**
     * The left analog stick y-axis.
     * @return value of left analog y-axis (pushing stick up is positive)
     */
    public double getLeftY() { return 0.0; }


    /***************************/
    /*** RIGHT CONTROL STICK ***/
    /***************************/

    /**
     * The right analog stick x-axis.
     * @return value of right analog x-axis
     */
    public double getRightX() { return 0.0; }


    /**
     * The right analog stick y-axis.
     * @return value of right analog y-axis (pushing stick up is positive)
     */
    public double getRightY() { return 0.0; }
    

    /********************/
    /*** ENTIRE D-PAD ***/
    /********************/

    /**
     * The upper d-pad button.
     * @return if upper d-pad button is pressed
     */
    public boolean getRawDPadUp() {
        return getJoystick().getPOV() == 0;
    }

    public final Button getDPadUp() {
        return new GamepadButton(() -> this.getRawDPadUp());
    }
    
    /**
     * The lower d-pad button.
     * @return if lower d-pad button is pressed
     */
    public boolean getRawDPadDown() {
        return getJoystick().getPOV() == 180;
    }

    public final Button getDPadDown() {
        return new GamepadButton(() -> this.getRawDPadDown());
    }
    
    /**
     * The left d-pad button.
     * @return if left d-pad button is pressed
     */
    public boolean getRawDPadLeft() {
        return getJoystick().getPOV() == 270;
    }

    public final Button getDPadLeft() {
        return new GamepadButton(() -> this.getRawDPadLeft());
    }
    

    /**
     * The right d-pad button.
     * @return if right d-pad button is pressed
     */
    public boolean getRawDPadRight() {
        return getJoystick().getPOV() == 90;
    }

    public final Button getDPadRight() {
        return new GamepadButton(() -> this.getRawDPadRight());
    }


    /*******************/
    /*** LEFT BUMPER ***/
    /*******************/
    public boolean getRawLeftBumper() { return false; }
    public final Button getLeftBumper() {
        return new GamepadButton(() -> this.getRawLeftBumper());
    }


    /********************/
    /*** RIGHT BUMPER ***/
    /********************/
    public boolean getRawRightBumper() { return false; }
    public final Button getRightBumper() {
        return new GamepadButton(() -> this.getRawRightBumper());
    }


    /********************/
    /*** LEFT TRIGGER ***/
    /********************/
    public double getRawLeftTriggerAxis() { return 0.0; }

    public boolean getRawLeftTrigger() {
        return getRawLeftTriggerAxis() > TRIGGER_AXIS_THRESHOLD;
    }

    public final Button getLeftTrigger() { 
        return new GamepadButton(() -> this.getRawLeftTrigger());
    } 


    /*********************/
    /*** RIGHT TRIGGER ***/
    /*********************/
    public double getRawRightTriggerAxis() { return 0; }

    public boolean getRawRightTrigger() {
        return getRawRightTriggerAxis() > TRIGGER_AXIS_THRESHOLD;
    }

    public final Button getRightTrigger() { 
        return new GamepadButton(() -> this.getRawRightTrigger());
    } 


    /*******************/
    /*** LEFT BUTTON ***/
    /*******************/
    public boolean getRawLeftButton() { return false; }

    public final Button getLeftButton() { 
        return new GamepadButton(() -> this.getRawLeftButton());
    } 


    /********************/
    /*** RIGHT BUTTON ***/
    /********************/
    public boolean getRawRightButton() { return false; }

    public final Button getRightButton() { 
        return new GamepadButton(() -> this.getRawRightButton());
    } 


    /******************/
    /*** TOP BUTTON ***/
    /******************/
    public boolean getRawTopButton() { return false; }

    public final Button getTopButton() { 
        return new GamepadButton(() -> this.getRawTopButton());
    } 


    /*********************/
    /*** BOTTOM BUTTON ***/
    /*********************/
    public boolean getRawBottomButton() { return false; }

    public final Button getBottomButton() { 
        return new GamepadButton(() -> this.getRawBottomButton());
    } 


    /*********************/
    /*** SELECT BUTTON ***/
    /*********************/
    public boolean getRawSelectButton() { return false; }

    public final Button getSelectButton() { 
        return new GamepadButton(() -> this.getRawSelectButton());
    } 


    /********************/
    /*** START BUTTON ***/
    /********************/
    public boolean getRawStartButton() { return false; }

    public final Button getStartButton() { 
        return new GamepadButton(() -> this.getRawStartButton());
    } 


    /**************************/
    /*** LEFT ANALOG BUTTON ***/
    /**************************/
    public boolean getRawLeftAnalogButton() { return false; }

    public final Button getLeftAnalogButton() { 
        return new GamepadButton(() -> this.getRawLeftAnalogButton());
    } 


    /***************************/
    /*** RIGHT ANALOG BUTTON ***/
    /***************************/
    public boolean getRawRightAnalogButton() { return false; }

    public final Button getRightAnalogButton() { 
        return new GamepadButton(() -> this.getRawRightAnalogButton());
    } 


    /*********************/
    /*** OPTION BUTTON ***/
    /*********************/
    public boolean getRawOptionButton() { return false; }

    public final Button getOptionButton() { 
        return new GamepadButton(() -> this.getRawOptionButton());
    } 


    /****************************/
    /*** GAMEPAD BUTTON CLASS ***/
    /****************************/
    /**
     * Lets us treat any function as an individual button
     */
    public static class GamepadButton extends Button {
        /**
         * Lets us recieve a function to use as a button
         */
        public interface RawButton {
            public boolean check();
        }

        // Stores Function
        private RawButton mButton;

        /**
         * Gives class lambda that will be used in get()
         * @param lambda Boolean function used as button
         */
        public GamepadButton(RawButton lambda) {
            this.mButton = lambda;
        }

        @Override
        public boolean get() {
            return mButton.check();
        }
    }
}
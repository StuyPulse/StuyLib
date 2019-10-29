package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Implementation of Logitech Controller and 
 * its 2 Modes for the Gamepad Class
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class Logitech extends Gamepad {
    
    /********************/
    /*** CONSTRUCTORS ***/
    /********************/
    public Logitech(Joystick joy) {
        super(joy);
    }    


    public Logitech(int port) {
        super(port);
    }


    /**********************/
    /*** D-PAD CONTROLS ***/
    /**********************/
    @Override
    public boolean getRawDPadUp() {
        return getJoystick().getPOV() == 0;
    }
    
    @Override
    public boolean getRawDPadDown() {
        return getJoystick().getPOV() == 180;
    }
    
    @Override
    public boolean getRawDPadLeft() {
        return getJoystick().getPOV() == 270;
    }
    
    @Override
    public boolean getRawDPadRight() {
        return getJoystick().getPOV() == 90;
    }
    
    
    /**
     * There is a switch on the back of the controller
     * this is for when the switch is in the D position
     */
    public static class DMode extends Logitech {
    
        /********************/
        /*** CONSTRUCTORS ***/
        /********************/
        public DMode(Joystick joy) {
            super(joy);
        }    
    
    
        public DMode(int port) {
            super(port);
        }

        /**********************/
        /*** CONTROL STICKS ***/
        /**********************/
        @Override
        public double getLeftX() {
            return getRawAxis(0);
        }

        @Override
        public double getLeftY() {
            return -getRawAxis(1);
        }

        @Override
        public double getRightX() {
            return getRawAxis(4);
        }

        @Override
        public double getRightY() {
            return -getRawAxis(3);
        }


        /*******************/
        /*** LEFT BUMPER ***/
        /*******************/
        @Override
        public boolean getRawLeftBumper() { 
            return getRawButton(5); 
        }
    
    
        /********************/
        /*** RIGHT BUMPER ***/
        /********************/
        @Override
        public boolean getRawRightBumper() { 
            return getRawButton(6); 
        }


        /********************/
        /*** LEFT TRIGGER ***/
        /********************/
        @Override
        public double getRawLeftTriggerAxis() {
            return getRawButton(7) ? 0.0 : 1.0;
        }


        /*********************/
        /*** RIGHT TRIGGER ***/
        /*********************/
        @Override
        public double getRawRightTriggerAxis() {
            return getRawButton(8) ? 0.0 : 1.0;
        }


        /*******************/
        /*** LEFT BUTTON ***/
        /*******************/
        @Override
        public boolean getRawLeftButton() {
            return getRawButton(1);
        }


        /********************/
        /*** RIGHT BUTTON ***/
        /********************/
        @Override
        public boolean getRawRightButton() {
            return getRawButton(3);
        }


        /******************/
        /*** TOP BUTTON ***/
        /******************/
        @Override
        public boolean getRawTopButton() {
            return getRawButton(4);
        }


        /*********************/
        /*** BOTTOM BUTTON ***/
        /*********************/
        @Override
        public boolean getRawBottomButton() {
            return getRawButton(2);
        }


        /*********************/
        /*** SELECT BUTTON ***/
        /*********************/
        @Override
        public boolean getRawSelectButton() {
            return getRawButton(9);
        }


        /********************/
        /*** START BUTTON ***/
        /********************/
        @Override
        public boolean getRawStartButton() {
            return getRawButton(10);
        }


        /**************************/
        /*** LEFT ANALOG BUTTON ***/
        /**************************/
        @Override
        public boolean getRawLeftAnalogButton() {
            return getRawButton(11);
        }


        /***************************/
        /*** RIGHT ANALOG BUTTON ***/
        /***************************/
        @Override
        public boolean getRawRightAnalogButton() {
            return getRawButton(12);
        }


        /**************/
        /*** RUMBLE ***/
        /**************/
        @Override
        public void rumble(double intensity){
            getJoystick().setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
            getJoystick().setRumble(GenericHID.RumbleType.kRightRumble, intensity);
        }
    }


    /**
     * There is a switch on the back of the controller
     * this is for when the switch is in the X position
     */
    public static class XMode extends Logitech {
    
        /********************/
        /*** CONSTRUCTORS ***/
        /********************/
        public XMode(Joystick joy) {
            super(joy);
        }    
    
    
        public XMode(int port) {
            super(port);
        }


        /**********************/
        /*** CONTROL STICKS ***/
        /**********************/
        @Override
        public double getLeftX() {
            return getRawAxis(0);
        }

        @Override
        public double getLeftY() {
            return -getRawAxis(1);
        }

        @Override
        public double getRightX() {
            return getRawAxis(4);
        }

        @Override
        public double getRightY() {
            return -getRawAxis(5);
        }


        /*******************/
        /*** LEFT BUMPER ***/
        /*******************/
        @Override
        public boolean getRawLeftBumper() { 
            return getRawButton(5); 
        }


        /********************/
        /*** RIGHT BUMPER ***/
        /********************/
        @Override
        public boolean getRawRightBumper() { 
            return getRawButton(6); 
        }


        /********************/
        /*** LEFT TRIGGER ***/
        /********************/
        @Override
        public double getRawLeftTriggerAxis() {
            return getRawAxis(2);
        }


        /*********************/
        /*** RIGHT TRIGGER ***/
        /*********************/
        @Override
        public double getRawRightTriggerAxis() {
            return getRawAxis(3);
        }


        /*******************/
        /*** LEFT BUTTON ***/
        /*******************/
        @Override
        public boolean getRawLeftButton() {
            return getRawButton(3);
        }


        /********************/
        /*** RIGHT BUTTON ***/
        /********************/
        @Override
        public boolean getRawRightButton() {
            return getRawButton(2);
        }


        /******************/
        /*** TOP BUTTON ***/
        /******************/
        @Override
        public boolean getRawTopButton() {
            return getRawButton(4);
        }

        /*********************/
        /*** BOTTOM BUTTON ***/
        /*********************/
        @Override
        public boolean getRawBottomButton() {
            return getRawButton(1);
        }


        /*********************/
        /*** SELECT BUTTON ***/
        /*********************/
        @Override
        public boolean getRawSelectButton() {
            return getRawButton(7);
        }


        /********************/
        /*** START BUTTON ***/
        /********************/
        @Override
        public boolean getRawStartButton() {
            return getRawButton(8);
        }


        /**************************/
        /*** LEFT ANALOG BUTTON ***/
        /**************************/
        @Override
        public boolean getRawLeftAnalogButton() {
            return getRawButton(9);
        }


        /***************************/
        /*** RIGHT ANALOG BUTTON ***/
        /***************************/
        @Override
        public boolean getRawRightAnalogButton() {
            return getRawButton(10);
        }


        /**************/
        /*** RUMBLE ***/
        /**************/
        @Override
        public void rumble(double intensity){
            getJoystick().setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
            getJoystick().setRumble(GenericHID.RumbleType.kRightRumble, intensity);
        }
    }
}
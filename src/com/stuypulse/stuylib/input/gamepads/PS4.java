package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Implementation of the PS4 for the Gamepad Class
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class PS4 extends Gamepad {
    
    /********************/
    /*** CONSTRUCTORS ***/
    /********************/
    public PS4(Joystick joy) {
        super(joy);
    }    


    public PS4(int port) {
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
        return getRawAxis(2);
    }

    @Override
    public double getRightY() {
        return -getRawAxis(5);
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
        return (getRawAxis(3) + 1.0) / 2.0;
    }


    /*********************/
    /*** RIGHT TRIGGER ***/
    /*********************/
    @Override
    public double getRawRightTriggerAxis() {
        return (getRawAxis(4) + 1.0) / 2.0;
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


    /*********************/
    /*** OPTION BUTTON ***/
    /*********************/
    @Override
    public boolean getRawOptionButton() {
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
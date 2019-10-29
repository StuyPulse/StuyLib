package com.stuypulse.stuylib.input.gamepads;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Implementation of a Dummy Controller for the Gamepad Class
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NullGamepad extends Gamepad {
    
    /********************/
    /*** CONSTRUCTORS ***/
    /********************/
    public NullGamepad(Joystick joy) {
        super(joy);
    }    


    public NullGamepad(int port) {
        super(port);
    }
    
    /****************************************/
    /*** GAMEPAD CLASS IS NULL BY DEFAULT ***/
    /****************************************/
}
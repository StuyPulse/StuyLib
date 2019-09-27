package stuylib.util.gamepads;

import edu.wpi.first.wpilibj.Joystick;

import stuylib.util.Gamepad;

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

    /**************************/
    /*** LEFT CONTROL STICK ***/
    /**************************/
    @Override
    public double getLeftX() { return 0.0; }

    @Override
    public double getLeftY() { return 0.0; }

    @Override
    public double getRightX() { return 0.0; }

    @Override
    public double getRightY() { return 0.0; }


    /**********************/
    /*** D-PAD CONTROLS ***/
    /**********************/
    public boolean getRawDPadUp() {
        return false;
    }
    
    public boolean getRawDPadDown() {
        return false;
    }
    
    public boolean getRawDPadLeft() {
        return false;
    }
    
    public boolean getRawDPadRight() {
        return false;
    }


    /*******************/
    /*** LEFT BUMPER ***/
    /*******************/
    @Override
    public boolean getRawLeftBumper() { return false; }


    /********************/
    /*** RIGHT BUMPER ***/
    /********************/
    @Override
    public boolean getRawRightBumper() { return false; }


    /********************/
    /*** LEFT TRIGGER ***/
    /********************/
    @Override
    public double getRawLeftTriggerAxis() { return 0.0; }

    public boolean getRawLeftTrigger() {
        return getRawLeftTriggerAxis() > TRIGGER_AXIS_THRESHOLD;
    }


    /*********************/
    /*** RIGHT TRIGGER ***/
    /*********************/
    @Override
    public double getRawRightTriggerAxis() { return 0; }

    public boolean getRawRightTrigger() {
        return getRawRightTriggerAxis() > TRIGGER_AXIS_THRESHOLD;
    }


    /*******************/
    /*** LEFT BUTTON ***/
    /*******************/
    @Override
    public boolean getRawLeftButton() { return false; }


    /********************/
    /*** RIGHT BUTTON ***/
    /********************/
    @Override
    public boolean getRawRightButton() { return false; }


    /******************/
    /*** TOP BUTTON ***/
    /******************/
    @Override
    public boolean getRawTopButton() { return false; }


    /*********************/
    /*** BOTTOM BUTTON ***/
    /*********************/
    @Override
    public boolean getRawBottomButton() { return false; }


    /*********************/
    /*** SELECT BUTTON ***/
    /*********************/
    @Override
    public boolean getRawSelectButton() { return false; }


    /********************/
    /*** START BUTTON ***/
    /********************/
    @Override
    public boolean getRawStartButton() { return false; }


    /**************************/
    /*** LEFT ANALOG BUTTON ***/
    /**************************/
    @Override
    public boolean getRawLeftAnalogButton() { return false; }


    /***************************/
    /*** RIGHT ANALOG BUTTON ***/
    /***************************/
    @Override
    public boolean getRawRightAnalogButton() { return false; }


    /*********************/
    /*** OPTION BUTTON ***/
    /*********************/
    @Override
    public boolean getRawOptionButton() { return false; }
}
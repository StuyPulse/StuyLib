package stuylib.util.gamepads;

import edu.wpi.first.wpilibj.Joystick;

import stuylib.util.Gamepad;

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

    // @Override
    // public double getRightY() {}


    /*******************/
    /*** LEFT BUMPER ***/
    /*******************/
    public boolean getRawLeftBumper() { 
        return getRawButton(5); 
    }


    /********************/
    /*** RIGHT BUMPER ***/
    /********************/
    public boolean getRawRightBumper() { 
        return getRawButton(6); 
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
        public double getRightY() {
            return -getRawAxis(3);
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
    }


    /**
     * There is a switch on the back of the controller
     * this is for when the switch is in the D position
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
        public double getRightY() {
            return -getRawAxis(5);
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
    }
}
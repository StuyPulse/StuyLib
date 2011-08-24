/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stuylib.io;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Alejandro Carrillo
 */
public class UserInput{

    DriverStationEnhancedIO enhancedIO;
    Joystick left_stick;
    Joystick right_stick;
    Joystick extra_stick_1;
    Joystick extra_stick_2;

    DriveConstants config;

    public int ATTACK_3_BUTTONS = 11;


    public UserInput(DriveConstants cfg) {
        config = cfg;
        left_stick = new Joystick(cfg.getLeftStickPort());
        right_stick = new Joystick(cfg.getRightStickPort());
        extra_stick_1 = new Joystick(cfg.getExtraStickPort1());
        extra_stick_2 = new Joystick(cfg.getExtraStickPort2());
    }
    public double getX() {
        return 0.0;
    }

    public double getY() {
        return 0.0;
    }



}

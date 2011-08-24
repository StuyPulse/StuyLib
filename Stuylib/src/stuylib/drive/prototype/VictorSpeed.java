package stuylib.drive.prototype;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Blake
 */
public class VictorSpeed implements SpeedController{

    Encoder e;
    Victor v;
    PIDController c;
    DriveConfig _d;

    double lastTime;

    /**
     * Make an actual speed controller complete with a Victor, Encoder and PIDController
     * @param victorChannel The PWM chanel for the victor.
     * @param encoderAChannel Digital in for the encoder.
     * @param encoderBChannel Input for the other encoder.
     * @param reverse Not used.  Was for reversing encoder direction.
     */
    public VictorSpeed(int victorChannel, int encoderAChannel, int encoderBChannel, boolean reverse, DriveConfig d) {
        _d = d;
        v = new Victor(victorChannel);

        e= new Encoder(encoderAChannel,encoderBChannel,reverse,CounterBase.EncodingType.k2X);
        e.setDistancePerPulse(d.getEncoderRadPerPulse());
        e.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate); // use e.getRate() for feedback
        e.start();

        c = new PIDController(d.getPIDSpeedConstants()[0], d.getPIDSpeedConstants()[1], d.getPIDSpeedConstants()[2], e, this);
        c.setOutputRange(-1, 1);
        if(d.getUseSpeedControl()){
            c.enable();
        }
    }

    public void pidWrite(double output) {
        v.set(output);
    }

    /**
     * Set a wheel's speed setpoint.
     * @param speed If in speed control mode as specified in DriveConfig object,
     *               the desired wheel speed in radPerSec (revolutions per minute).
     *              Otherwise, direct PWM output.
     */
    public void set(double speed) {
        if(_d.getUseSpeedControl()){
            c.setSetpoint(speed);
        }else{
            v.set(speed);
        }
    }

    /**
     * Never call this method.  We need to have one in order to implement the
     * SpeedController interface and pass this class into a DriveTrain, etc.,
     * but we are using Victors which do not use syncGroups.
     */
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    public double get() {
        return v.get();
    }

    public void disable() {
        v.disable();
    }
}


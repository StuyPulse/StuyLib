package com.stuypulse.stuylib.util;

import com.revrobotics.CANEncoder;

public final class NEOEncoder {
    // Stores value of encoder when reseting.
    private double encoderResetValue;
    private CANEncoder encoder;

    /* NOTE: This class only works in a single gear.
       If there was gear shifting while using this
       encoder, it would set the result of 
       encoder.getPosition() to zero. This would
       result in incorrect encoder values.

       TLDR: Don't use this class if you plan to gear shift
       while using these methods.                 
    */

    public NEOEncoder(CANEncoder encoder) {
        this.encoder = encoder;
        encoderResetValue = encoder.getPosition();
    }

    public void resetEncoder() {
        encoderResetValue = encoder.getPosition();
    }

    public double getPosition() {
        return encoder.getPosition() - encoderResetValue;
    }
}

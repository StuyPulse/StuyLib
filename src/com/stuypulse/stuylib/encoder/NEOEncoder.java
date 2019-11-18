package com.stuypulse.stuylib.encoder;

import com.revrobotics.CANEncoder;

/**
 * A NEOEncoder class that lets you discard 
 * movement in the event of a gear switch, 
 * while keeping distance traveled
 * 
 * @author Sam (sam.belliveau@gmail.com)
 * @author Ivan (iwei20@stuy.edu)
 */

public final class NEOEncoder {
    
    // Store distance traveled
    private double mTotalDistanceMoved;

    // Store last location and take the difference
    private double mLastPosition;

    // Encoder variable
    private CANEncoder mEncoder;

    /**
     * @param encoder Encoder used to track distance
     */
    public NEOEncoder(CANEncoder encoder) {
        mEncoder = encoder;
        reset();
    }

    /**
     * Reset encoder and make distance traveled 0
     */
    public void reset() {
        mTotalDistanceMoved = 0;
        disgard();
    }

    /**
     * Put encoder in the same state as the last getPosition call.
     * This will let you ignore errors that come up when shifting gears,
     * but still get total distance moved
     */
    public void disgard() {
        mLastPosition = mEncoder.getPosition();
    }

    /**
     * Update the encoder state and get position
     * @return Total distance moved
     */
    public double getPosition() {
        double currentPosition = mEncoder.getPosition();
        mTotalDistanceMoved += currentPosition - mLastPosition;
        mLastPosition = currentPosition;
        return mTotalDistanceMoved;
    }
}

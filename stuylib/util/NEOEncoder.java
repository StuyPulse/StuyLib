package stuylib.util;

import com.revrobotics.CANEncoder;

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
        mTotalDistanceMoved = 0;
        mLastPosition = encoder.getPosition();
    }

    /**
     * Reset encoder and make distance traveled 0
     */
    public void resetEncoder() {
        mTotalDistanceMoved = 0;
        disgardMovement();
    }

    /**
     * Put encoder in the same state as the last getPosition call.
     * This will let you ignore errors that come up when shifting gears,
     * but still get total distance moved
     */
    public void disgardMovement() {
        mLastPosition = encoder.getPosition();
    }

    /**
     * Update the encoder state and get position
     * @return Total distance moved
     */
    public double getPosition() {
        double currentPosition = encoder.getPosition();
        mTotalDistanceMoved = currentPosition - mLastPosition;
        mLastPosition = currentPosition;
        return mTotalDistanceMoved;
    }
}
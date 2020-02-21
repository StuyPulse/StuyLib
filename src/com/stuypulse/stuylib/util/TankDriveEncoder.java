package com.stuypulse.stuylib.util;

import edu.wpi.first.wpilibj.Encoder;

/**
 * The tank drive encoder manages the left and right encoder for the drivetrain.
 * It does math to get the distance the entire robot has moved, and it can also
 * detect if an encoder has disconnected, taking that into consideration.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class TankDriveEncoder {

    // Encoders to record drivetrain movement from
    private Encoder mLeftEncoder;
    private Encoder mRightEncoder;

    // Distances recorded throughout every update
    private double mLeftDistance;
    private double mRightDistance;
    private double mTotalDistance;

    /**
     * @param leftEncoder  encoder on left side to record from
     * @param rightEncoder encoder on right side to record from
     */
    public TankDriveEncoder(Encoder leftEncoder, Encoder rightEncoder) {
        mLeftEncoder = leftEncoder;
        mRightEncoder = rightEncoder;

        reset();
    }

    /**
     * @return get encoder for the left side
     */
    public Encoder getLeftEncoder() {
        return mLeftEncoder;
    }

    /**
     * @return get encoder for the left side
     */
    public Encoder getRightEncoder() {
        return mRightEncoder;
    }

    /**
     * Reset all of the distance values
     */
    public void reset() {
        mLeftDistance = 0;
        mRightDistance = 0;
        mTotalDistance = 0;

        getLeftEncoder().reset();
        getRightEncoder().reset();
    }

    /**
     * Record values from the drivetrain
     */
    private void update() {
        double left = mLeftEncoder.getDistance();
        double right = mRightEncoder.getDistance();

        if(!mLeftEncoder.getStopped()) {
            mLeftDistance += left;
            mLeftEncoder.reset();
        }

        if(!mRightEncoder.getStopped()) {
            mRightDistance += right;
            mRightEncoder.reset();
        }

        if(mRightEncoder.getStopped() && mLeftEncoder.getStopped()) {
            mTotalDistance += 0;
        } else if(mLeftEncoder.getStopped()) {
            mTotalDistance += right;
        } else if(mRightEncoder.getStopped()) {
            mTotalDistance += left;
        } else {
            mTotalDistance += (left + right) / 2.0;
        }
    }

    /**
     * @return get distance that the left side of the drivetrain has moved
     */
    public double getLeftDistance() {
        update();
        return mLeftDistance;
    }

    /**
     * @return get distance that the right side of the drivetrain has moved
     */
    public double getRightDistance() {
        update();
        return mRightDistance;
    }

    /**
     * @return get distance that the entire drivetrain has moved
     */
    public double getDistance() {
        update();
        return mTotalDistance;
    }
}

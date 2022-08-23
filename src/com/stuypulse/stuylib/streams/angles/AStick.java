/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.VStream;

/**
 * When controlling angle with a joystick that has a deadzone filter, the angle will snap to 0 degrees
 * while in the deadzone. This class prevents the angle snapping and will return the most recent
 * angle from outside of the deadzone.
 * 
 * @author Benjamin Goldfisher
 */
public class AStick implements AStream {

    private final VStream mStick;
    private final Number mDeadzone;

    private Angle mPrevious;

    public AStick(VStream stick, Number deadzone) {
        mStick = stick;
        mDeadzone = deadzone;

        mPrevious = Angle.kNull;
    }

    @Override
    public Angle get() {
        final Vector2D stick = mStick.get();

        if (mDeadzone.doubleValue() < stick.magnitude()) {
            mPrevious = stick.getAngle();
        }

        return mPrevious;
    }
    
}

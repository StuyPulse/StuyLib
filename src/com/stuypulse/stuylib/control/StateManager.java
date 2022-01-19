/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

import com.stuypulse.stuylib.math.Matrix;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.IFilter;
import com.stuypulse.stuylib.streams.filters.IFilterGroup;
import com.stuypulse.stuylib.streams.filters.TimedMovingAverage;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.util.sendable.SendableBuilder;

/**
 * A StateManager
 *
 * @author Ivan (ivanw8288@gmail.com)
 */
public class StateManager {
    public class State extends Matrix {
        public State(int size) {
            super(size, 1);
        }

        public State(double[] vals) {
            super(vals.length, 1);
            for (int i = 0; i < vals.length; ++i) {
                setEntry(i, vals[i]);
            }
        }

        public double getEntry(int i) {
            return super.get(i, 0);
        }

        public void setEntry(int i, double val) {
            super.set(i, 0, val);
        }
    }
}

/* Copyright (c) 2023 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.filters;

/**
 * A simple low-pass filter for smoothing joystick input values in robot driving code.
 *
 * <p>The filter helps robot drivers drive more smoothly, by smoothing out noisy input values from a
 * controller, by filtering out high-frequency components, while perserving low-frequency
 * components.
 *
 * <p>The filter is implemented as a first-order IIR filter with a configurable filter coefficient.
 * The closer this value is to one, the more the filter represents a simple averaging filter, the
 * closer this value is to zero, the more it represents a simple delay filter. Not every robot or
 * driver should use the same coefficient, the team should determine the best coefficient for their
 * driver through trial-and-error.
 *
 * <p>The filter state is initialized to zero when a new instance is created, and the filter
 * function should be called repeatedly with new input values to obtain the filtered output. The
 * filter function returns the filtered output value, which can be used as the input to the robot
 * driving code.
 *
 * @author Mansour Quddus (mansourquddus@protonmail.com) (@devmanso on github)
 */
public class DriveFilter {
    private double last;

    /**
     * This implements a simple low-pass filter, it is meant to be used in your driving code. It
     * helps robot drivers drive more smoothly, by smoothing out noisy input values from a
     * controller, by filtering out high-frequency components, while perserving low-frequency
     * components.
     */
    public DriveFilter() {
        last = 0.0;
    }

    /**
     * @param next - this should be the joystick input axis, i.e: the left or right axis of an xBox
     *     game controller
     * @param coefficient - the constant value that represents the filter coefficient and determines
     *     the amount of filtering applied to the input value.
     *     <p>Not every robot or driver should use the same coefficient, your team should determine
     *     the best coefficient for your driver through trial-and-error.
     *     <p>The closer this value is to one, the more the filter represents a simple averaging
     *     filter, the closer this value is to zero, the more it represents a simple delay filter.
     * @return the filtered value
     */
    public double get(double next, double coefficient) {
        double sign = (next > 0) ? 1 : -1;
        next *= next * sign;
        return last += (next - last) * (1 - coefficient);
    }
}

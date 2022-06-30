/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control;

/**
 * A simple implementation of a BangBangController for the Stuylib Controller
 *
 * <p>When the error is positive, it returns a fixed positive value.
 *
 * <p>When the error is negative, it returns a fixed negative value.
 *
 * <p>It is not really a good controller but is shown for educational purposes.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class BangBangController extends Controller {

    private double mPositive;
    private double mNegative;

    /**
     * Creates a BangBangController with a specified positive and negative value
     *
     * @param positive value to return when error is positive
     * @param negative value to return when error is negative
     */
    public BangBangController(double positive, double negative) {
        mPositive = positive;
        mNegative = negative;
    }

    /**
     * Creates a BangBangController with a specified positive and an inverted negative value
     *
     * @param positive value returned when the error is positive (the negative of this value will be
     *     returned when the error is negative)
     */
    public BangBangController(double positive) {
        this(positive, -positive);
    }

    /**
     * Create a BangBangController that returns 1.0 when error is positive and -1.0 when the error
     * is negative
     */
    public BangBangController() {
        this(1.0);
    }

    /**
     * @param error the error given to the controller
     * @return the positive value when the error is positive and the negative value when the error
     *     is negative
     */
    @Override
    protected double calculate(double setpoint, double measurement) {
        if (setpoint > measurement) {
            return mPositive;
        } else {
            return mNegative;
        }
    }

}

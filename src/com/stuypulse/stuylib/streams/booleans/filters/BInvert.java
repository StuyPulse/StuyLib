/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.booleans.filters;

/** @author Sam (sam.belliveau@gmail.com) */
public class BInvert implements BFilter {

    public BInvert() {}

    public boolean get(boolean next) {
        return !next;
    }
}

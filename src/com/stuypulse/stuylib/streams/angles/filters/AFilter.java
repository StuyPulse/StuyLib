package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;

public interface AFilter {
    Angle get(Angle next);
}

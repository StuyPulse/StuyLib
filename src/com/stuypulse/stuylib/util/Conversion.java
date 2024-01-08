/* Copyright (c) 2024 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

import java.util.function.Function;

/**
 * Represents a conversion from a type to another type.
 *
 * <p>Useful when wanting to package two related type/unit conversions functions together.
 *
 * @author Myles Pasetsky
 */
public interface Conversion<From, To> {
    static <In, Out> Conversion<In, Out> make(Function<In, Out> to, Function<Out, In> from) {
        return new Conversion<In, Out>() {
            public Out to(In value) {
                return to.apply(value);
            }

            public In from(Out value) {
                return from.apply(value);
            }
        };
    }

    default Conversion<To, From> invert() {
        return make(this::from, this::to);
    }

    To to(From value);

    From from(To value);
}

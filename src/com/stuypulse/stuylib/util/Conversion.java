package com.stuypulse.stuylib.util;

import java.util.function.Function;

public interface Conversion<From, To> {
    static <In, Out> Conversion<In, Out> make(Function<In, Out> to, Function<Out, In> from) {
        return new Conversion<In, Out>() {
            public Out to(In value) { return to.apply(value); }
            public In from(Out value) { return from.apply(value); }
        };
    }
    
    default Conversion<To, From> invert() {
        return make(this::from, this::to);
    }

    To to(From value);
    From from(To value);
}

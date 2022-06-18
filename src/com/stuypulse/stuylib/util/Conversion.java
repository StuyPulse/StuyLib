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
        Conversion<From, To> o = this;
        return new Conversion<To, From>() {
            public From to(To value) { return o.from(value); }
            public To from(From value) { return o.to(value); }
        };
    }

    To to(From value);
    From from(To value);
}

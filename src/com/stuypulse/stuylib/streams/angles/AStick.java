package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.streams.booleans.BStream;
import com.stuypulse.stuylib.streams.vectors.VStream;

public class AStick implements AStream {

    private final AStream stream;
    private final BStream deadzone;

    private Angle prev;

    public AStick(VStream stick, Number deadzone) {
        this.stream = AStream.create(stick);
        this.deadzone = BStream.create(() -> {
            return stick.get().magnitude() <= deadzone.doubleValue();
        });

        prev = Angle.kNull;
    }

    @Override
    public Angle get() {
        if (deadzone.get()) {
            return prev;
        }

        prev = stream.get();
        return prev;
    }
    
}

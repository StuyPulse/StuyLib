package com.stuypulse.stuylib.streams.angles;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.Vector2D;
import com.stuypulse.stuylib.streams.vectors.VStream;

public class AStick implements AStream {

    private final VStream stream;
    private final Number deadzone;

    private Angle prev;

    public AStick(VStream stick, Number deadzone) {
        this.stream = stick;
        this.deadzone = deadzone;

        prev = Angle.kNull;
    }

    @Override
    public Angle get() {
        Vector2D out = stream.get();

        if (out.magnitude() <= deadzone.doubleValue()) {
            return prev;
        }

        prev = out.getAngle();
        return prev;
    }
    
}

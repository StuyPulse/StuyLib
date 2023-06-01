package com.stuypulse.stuylib.led;

import com.stuypulse.stuylib.util.Color;
import com.stuypulse.stuylib.util.StopWatch;

public class LEDPulse implements LEDInstruction {

    private Color color;
    private Color alternativeColor;
    private double pulseLength;
    private StopWatch pulseTime;

    public LEDPulse(Color color) {
        this(color, 1);
    }

    public LEDPulse(Color color, double pulseTime) {
        this(color, Color.OFF, pulseTime);
    }

    public LEDPulse(Color color, Color alternativeColor, double pulseLength) {
        this.color = color;
        this.alternativeColor = alternativeColor;
        this.pulseLength = pulseLength;
        pulseTime = new StopWatch();
    }

    @Override
    public void setStrip(LEDStrip strip) {
        double time = pulseTime.getTime();

        if (time < pulseLength / 2) {
            strip.setColor(color);
        } else if (time < pulseLength) {
            strip.setColor(alternativeColor);
        } else {
            pulseTime.reset();
        }
    }
}

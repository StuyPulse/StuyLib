package com.stuypulse.stuylib.led;

import com.stuypulse.stuylib.util.Color;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDStrip extends SubsystemBase {
    
    private AddressableLED strip;
    private AddressableLEDBuffer buffer;

    public LEDStrip(int port, int length, int pulseTime) {
        strip = new AddressableLED(port);
        buffer = new AddressableLEDBuffer(length);

        strip.setLength(length);
        strip.setData(buffer);
        strip.start();
    }

    public int getLength() {
        return buffer.getLength();
    }

    public AddressableLEDBuffer getBuffer() {
        return buffer;
    }

    public void setColors(Color[] colors) {
        if (buffer.getLength() != colors.length) return;

        for (int i = 0; i < colors.length; i++) {
            buffer.setRGB(i, colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue());
        }
    }

    public void setColor(Color color) {
        for (int i = 0; i < buffer.getLength(); i++) {
            buffer.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
        }
    }

    private void setStrip() {
        strip.setData(buffer);
    }

    @Override
    public void periodic() {
        setStrip();
    }
}

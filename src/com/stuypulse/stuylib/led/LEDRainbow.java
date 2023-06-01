package com.stuypulse.stuylib.led;

import com.stuypulse.stuylib.util.Color;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LEDRainbow implements LEDInstruction {

    private StopWatch timer;
    private Color[] rainbow;
    private Color[] currentColors;
    private int counter;
    private double updateTime;

    public LEDRainbow() {
        this(0.02);
    }
    
    public LEDRainbow(double updateTime) {
        timer = new StopWatch();
        rainbow = new Color[]{
            new Color (218,165,32), 
            new Color(255, 83, 73), 
            new Color(255, 128, 0),
            new Color(255, 255, 0), 
            new Color(102, 204, 0), 
            new Color(0, 255, 0), 
            new Color(0, 153, 0), 
            new Color(0, 255, 128), 
            new Color(0, 128, 255), 
            new Color(0, 0, 204), 
            new Color(51, 51, 255), 
            new Color(127, 0, 255), 
            new Color(160, 32, 240), 
            new Color(255, 192, 203)};
        currentColors = null;
        counter = 0;
        this.updateTime = updateTime;
    }

    @Override
    public void setStrip(LEDStrip strip) {

        if (currentColors == null) {
            currentColors = new Color[strip.getLength()];
            for (int i = 0; i < currentColors.length; i++) {
                currentColors[i] = Color.OFF;
            }
        }

        if (timer.getTime() >= updateTime) {
            for (int i = 0; i < currentColors.length; i++) {
                currentColors[i] = rainbow[counter++];
                if (counter >= strip.getLength()) {
                    counter = 0;
                }
            }
            strip.setColors(currentColors);
            timer.reset();
        }
    }
}

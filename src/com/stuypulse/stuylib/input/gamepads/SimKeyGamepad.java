package com.stuypulse.stuylib.input.gamepads;

import java.awt.Color;
import javax.swing.JFrame;

import com.stuypulse.stuylib.util.chart.KeyTracker;

/**
 * This class opens a window to accept keyboard input that acts as a gamepad. It is meant to be used
 * with simulation code, in which the code is running on a computer. It will not work if the robot
 * code is being run on the robot (e.g. RoboRIO)
 *
 * @author Myles Pasetsky (selym3)
 */
public class SimKeyGamepad extends KeyGamepad {

    private JFrame inputArea;
    private KeyTracker keyboard;

    private static final int DEFAULT_WIDTH = 300, DEFAULT_HEIGHT = 300;

    public SimKeyGamepad() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public SimKeyGamepad(int width, int height) {
        inputArea = new JFrame("Simulation Gamepad");
        inputArea.setSize(width, height);
        inputArea.setResizable(false);
        inputArea.setDefaultCloseOperation(
                // JFrame.DISPOSE_ON_CLOSE
                JFrame.EXIT_ON_CLOSE);

        keyboard = new KeyTracker();
        inputArea.addKeyListener(keyboard);

        inputArea.setLocationRelativeTo(null);
        inputArea.setVisible(true);
    }

    protected boolean getKey(String name) {
        return keyboard.hasKey(name);
    }

    // Rumble //
    public void setRumble(double intensity) {
        Color color = Color.getHSBColor(0.0f, (float) intensity, 1.0f);
        inputArea.getContentPane().setBackground(color);
    }

}

/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input.gamepads.keyboard;

import com.stuypulse.stuylib.util.chart.KeyTracker;

import java.awt.Color;
import javax.swing.JFrame;

/**
 * This class opens a window to accept keyboard input that acts as a gamepad. It is meant to be used
 * with simulation code, in which the code is running on a computer. It will not work if the robot
 * code is being run on the robot (e.g. RoboRIO)
 *
 * @author Myles Pasetsky (selym3)
 */
public class SimKeyGamepad extends KeyGamepad {

    /** Default size of an input area */
    private static final int DEFAULT_WIDTH = 300, DEFAULT_HEIGHT = 300;

    /** Opens up a screen in which keyboard input is accepted. */
    private JFrame inputArea;

    /** This keytracker is added to the input area to allow us to read key inputs */
    private KeyTracker keyboard;

    /** Creates a sim gamepad and opens an input area with the default width and height */
    public SimKeyGamepad() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Creates a sim gamepad and opens an input area with the given width and height
     *
     * @param width width of input area
     * @param height height of input area
     */
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

    /**
     * Gets if a key is being held
     *
     * @return whether or not the key is being pressed
     */
    @Override
    protected boolean getKey(String name) {
        return keyboard.hasKey(name);
    }

    /**
     * Sets the color of the input area based on rumble
     *
     * @param intensity the magnitude of rumble for the controller
     */
    public void setRumble(double intensity) {
        Color color = Color.getHSBColor(0.0f, (float) intensity, 1.0f);
        inputArea.getContentPane().setBackground(color);
    }
}

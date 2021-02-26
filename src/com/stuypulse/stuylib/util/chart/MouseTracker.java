/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.chart;

import java.awt.*;

/**
 * Tracks the mouse on a container without the need for a listener.
 *
 * @author Sam Belliveau (@Sam-Belliveau)
 * @author Myles Pasetsky (@selym3)
 */
public class MouseTracker {

    /** Panel for size reference */
    private final Container panel;

    /**
     * Initialize panel and x and y
     *
     * @param panel Panel to track on
     */
    public MouseTracker(Container panel) {
        this.panel = panel;
    }

    /**
     * Get mouse x
     *
     * @return mouse x position as a percentage of the screen width
     */
    public double getMouseX() {
        double x = MouseInfo.getPointerInfo().getLocation().x;
        x -= panel.getLocationOnScreen().x;
        x /= panel.getWidth();
        return x;
    }

    /**
     * Get mouse y
     *
     * @return mouse y position as a percentage of the screen height
     */
    public double getMouseY() {
        double y = MouseInfo.getPointerInfo().getLocation().y;
        y -= panel.getLocationOnScreen().y;
        y /= panel.getHeight();
        return 1.0 - y;
    }
}

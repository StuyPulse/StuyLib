package com.stuypulse.stuylib.util.chart;

import java.awt.event.MouseEvent;

import java.awt.event.*;
import java.awt.*;

/**
 * Tracks the mouse on a container
 * 
 * @author Myles Pasetsky (@selym3) 
 * @author Sam Belliveau (@Sam-Belliveau)
 */
public class MouseTracker implements MouseMotionListener {   

    private final Container panel;
    private double x, y;

    /**
     * Initialize panel and x and y
     * 
     * @param panel Panel to track on
     */
    public MouseTracker(Container panel) {
        this.panel = panel;
        this.x = 0;
        this.y = 0;       
    } 

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        x = ((double)e.getX()) / ((double)panel.getWidth());
        y = ((double)e.getY()) / ((double)panel.getHeight());
    }

    /**
     * Get mouse x
     * 
     * @return mouse x position as a percentage of the
     * screen width
     */
    public double getMouseX() { 
        return x;
    }

    /**
     * Get mouse y
     * 
     * @return mouse y position as a percentage of the
     * screen height
     */
    public double getMouseY() { 
        return 1.0 - y;
    }
}
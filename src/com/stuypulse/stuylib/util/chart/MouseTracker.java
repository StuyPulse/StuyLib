package com.stuypulse.stuylib.util.chart;

import java.awt.event.MouseEvent;

import java.awt.event.*;
import java.awt.*;

/**
 * @author Myles Pasetsky (@selym3) 
 * @author Sam Belliveau (@Sam-Belliveau)
 */
public class MouseTracker implements MouseMotionListener {    
    private final Container panel;
    private double x, y;

    // think component will work (swing object tree)
    public MouseTracker(Container panel) {
        this.panel = panel;
        this.x = 0;
        this.y = 0;       
    } 

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = ((double)e.getX()) / ((double)panel.getWidth());
        y = ((double)e.getY()) / ((double)panel.getHeight());
    }

    public double getMouseX() { 
        return x;
    }

    public double getMouseY() { 
        return 1.0 - y;
    }
}
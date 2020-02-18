package com.stuypulse.stuylib.util.chart;

import java.awt.event.*;

/**
 * @author Myles Pasetsky (selym3) 
 */
public class KeyTracker extends KeyAdapter {

    private final QuickWrapper area;

    public KeyTracker(QuickWrapper area) {
        this.area = area;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            area.getCardLayout().next(area.getContentPane());
        } else if (keyCode == KeyEvent.VK_LEFT) {
            area.getCardLayout().previous(area.getContentPane());
        }
    }
}
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

/**
 * A plot contains and manages the window to which any data is drawn.
 *
 * <p>It stores the Series that it is going draw. It also has methods that read the mouse's
 * position, which can be used as an input stream for a series.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class Plot {

    private Map<String, Tab> tabs;

    private String defaultTab;

    private JFrame frame;

    /** A utility for finding mouse positions */
    private MouseTracker mouse;

    /**
     * Creates a configured plot
     */
    public Plot() {
        tabs = new HashMap<String, Tab>();

        defaultTab = null;
    }
    
    public void build(String title, int width, int height) {
		frame = new JFrame(title);
		
        frame.getContentPane()
                .setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);

		// from SwingWrapper.java from XChart
		int rows = (int) (Math.sqrt(tabs.size()) + .5);
		int cols = (int) ((double) tabs.size() / rows + 1);

		frame.getContentPane().setLayout(new GridLayout(rows, cols));

		for (String tab : tabs.keySet()) {
			frame.add(tabs.get(tab).panel);
		}

		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
        
        mouse = new MouseTracker(frame);
    }

    public Plot addPlot(Settings settings) {
        Tab tab = new Tab(settings);

        tabs.put(settings.getTitle(), tab);

        if (defaultTab == null) defaultTab = settings.getTitle();

        return this;
    }

    /** @return mouse position */
    public Vector2D getMouse() {
        return mouse.getPosition();
    }

    /** @return mouse y position */
    public double getMouseY() {
        return mouse.getY();
    }

    /** @return mouse x position */
    public double getMouseX() {
        return mouse.getX();
    }

    /**
     * Adds series to be graphed
     *
     * @param series series to add
     * @return reference to self
     */
    public Plot addSeries(String tabId, Series... series) {
        if (!tabs.containsKey(tabId)) {
            System.err.println("Invalid tab ID \"" + tabId + "\" given");
            return this;
        }

        tabs.get(tabId).addSeries(series);
        return this;
    }

    public Plot addSeries(Series... series) {
        return addSeries(defaultTab, series);
    }

    public void update() {
        for (String key : tabs.keySet()) {
            Tab tab = tabs.get(key);
            
            if (tab.isRunning()) tab.update();
        }
    }
}

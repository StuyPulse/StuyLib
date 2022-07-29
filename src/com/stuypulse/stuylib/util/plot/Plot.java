/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util.plot;

import com.stuypulse.stuylib.math.Vector2D;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * A plot contains and manages the window to which any data is drawn.
 *
 * <p>It stores the Series that it is going draw. It also has methods that read the mouse's
 * position, which can be used as an input stream for a series.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class Plot {

    private List<Tab> tabs;
    private Map<String, Integer> names;

    private JFrame frame;
    private JTabbedPane pane;

    /** A utility for finding mouse positions */
    private MouseTracker mouse;

    /**
     * Creates a configured plot
     */
    public Plot() {
        tabs = new ArrayList<Tab>();
        names = new HashMap<String, Integer>();
    }
    
    public void build(String title, int width, int height) {
        pane = new JTabbedPane();

		frame = new JFrame(title);
		
        frame.getContentPane()
                .setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);

		for (Tab tab : tabs) {
            pane.addTab(tab.panel.getName(), tab.panel);
		}

        frame.getContentPane().add(pane);

		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
        
        mouse = new MouseTracker(frame);
    }

    public Plot addTab(Settings settings) {
        Tab tab = new Tab(settings);

        tabs.add(tab);
        names.put(settings.getTitle(), tabs.size() - 1);

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
        if (!names.containsKey(tabId)) {
            System.err.println("Invalid tab ID \"" + tabId + "\" given");
            return this;
        }

        tabs.get(names.get(tabId)).addSeries(series);
        return this;
    }

    public Plot addSeries(Series... series) {
        tabs.get(tabs.size() - 1).addSeries(series);
        return this;
    }

    public void update() {
        int selected = pane.getSelectedIndex();

        if (tabs.get(selected).isRunning())
            tabs.get(selected).update();
    }
}

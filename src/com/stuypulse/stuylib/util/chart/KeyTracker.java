package com.stuypulse.stuylib.util.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Myles Pasetsky (selym3) 
 */
public class KeyTracker extends KeyAdapter {

    private final ChartGroup area;

    private List<Integer> keys;
    private Map<Integer, Boolean> values;

    public KeyTracker(ChartGroup area) {
        this.area = area;

        keys = new ArrayList<Integer>();
        values = new HashMap<Integer, Boolean>();
    }

    public KeyTracker(ChartGroup area, int... e) {
        this(area);
        addKey(e);
    }

    /**
     * Sets the values to v if key is d
     * 
     * @param d Target key
     * @param v Target boolean
     */
    private void setValues(int d, boolean v) {
        for (Integer keyBinding : values.keySet()) {
            if (keyBinding == d) {
                values.replace(keyBinding, v);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // NECESSARY FUNCTIONS FOR A CHARTGROUP
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            area.getCardLayout().next(area.getContentPane());
        } else if (keyCode == KeyEvent.VK_LEFT) {
            area.getCardLayout().previous(area.getContentPane());
        }

        setValues(keyCode, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        setValues(keyCode, false);
    }

    public boolean getKey(int binding) {
        return values.get(binding);
    }

    public KeyTracker addKey(int... e) {
        for (int binding : e) {
            keys.add(binding);
        }
        for (Integer key : keys) {
            values.put(key,false);
        }
        return this;
    }

}
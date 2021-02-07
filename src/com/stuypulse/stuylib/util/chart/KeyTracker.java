package com.stuypulse.stuylib.util.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Key tracker for swing components with key bindings.
 *
 * @author Myles Pasetsky (selym3)
 */
public final class KeyTracker extends KeyAdapter {

    /**
     * Set of key codes that will contain keys currently pressed.
     */
    private Set<String> values;

    /**
     * Initialize set of key presses and key bindings.
     */
    public KeyTracker() {
        values = new HashSet<String>();
    }

    /**
     * @param e Key Event from KeyPress
     * @return sanitized key name
     */
    private static String getKeyName(KeyEvent e) {
        return sanatizeKeyName(KeyEvent.getKeyText(e.getKeyCode()));
    }

    /**
     * @param name input key name
     * @return the sanatized key name
     */
    private static String sanatizeKeyName(String name) {
        return name.strip().toUpperCase();
    }

    /**
     * Get the value of a key press.
     *
     * @param key name of the key to check
     * @return if a key is pressed
     */
    public boolean hasKey(String key) {
        return values.contains(sanatizeKeyName(key));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        values.add(getKeyName(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        values.remove(getKeyName(e));
    }

}

package com.stuypulse.stuylib.util.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Key tracker for swing components with key bindings.
 *
 * @author Myles Pasetsky (selym3)
 */
public class KeyTracker extends KeyAdapter {

    /**
     * Interface for basic key bindings.
     */
    public interface KeyFunction {

        void execute();
    }

    /**
     * Set of key codes that will contain keys currently pressed.
     */
    private Set<String> values;

    /**
     * Binding functions mapped to key codes.
     */
    private Map<String, KeyFunction> bindings;

    /**
     * Initialize set of key presses and key bindings.
     */
    public KeyTracker() {
        values = new HashSet<String>();
        bindings = new HashMap<String, KeyFunction>();
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
    public boolean getKey(String key) {
        return values.contains(sanatizeKeyName(key));
    }

    /**
     * Add a basic binding to a key code
     *
     * @param key      key to bind to
     * @param function function that gets run when key is pressed
     * @return Reference to key tracker
     */
    public KeyTracker addBind(String key, KeyFunction function) {
        bindings.put(key, function);
        return this;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Close window when the x button is pressed
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        final String keyName = getKeyName(e);

        if(bindings.containsKey(keyName)) {
            bindings.get(keyName).execute();
        }

        values.add(keyName);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        values.remove(getKeyName(e));
    }

}

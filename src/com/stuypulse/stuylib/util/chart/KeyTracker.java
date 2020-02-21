package com.stuypulse.stuylib.util.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Key tracker for swing components with key
 * bindings.
 * 
 * @author Myles Pasetsky (selym3)
 */
public class KeyTracker extends KeyAdapter {

    /**
     * Interface for basic key bindings.
     */
    public interface BindingFunction {
        void function();
    }

    private Set<Integer> values;
    private Map<Integer, BindingFunction> bindings;

    /**
     * Initialize set of key presses and
     * key bindings.
     */
    public KeyTracker() {
        values = new HashSet<Integer>();
        bindings = new HashMap<Integer, BindingFunction>();
    }

    /**
     * Get the value of a key press.
     * 
     * @param binding key code
     * @return if a key is pressed
     */
    public boolean getKey(int binding) {
        return values.contains(binding);
    }

    /**
     * Add a basic binding to a key code
     * 
     * @param keyCode key code 
     * @param bindingFunction Binding function
     */
    public KeyTracker addBinding(int keyCode, BindingFunction bindingFunction) {
        bindings.put(keyCode, bindingFunction);
        return this;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

        if(bindings.containsKey(keyCode)) {
            bindings.get(keyCode).function();
        }

        values.add(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        values.remove(keyCode);
    }

}
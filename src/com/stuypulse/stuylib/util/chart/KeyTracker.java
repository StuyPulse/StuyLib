package com.stuypulse.stuylib.util.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Myles Pasetsky (selym3)
 */
public class KeyTracker extends KeyAdapter {

    public interface BindingFunction {
        void function();
    }

    private Set<Integer> values;
    private Map<Integer, BindingFunction> bindings;

    public KeyTracker() {
        values = new HashSet<Integer>();
        bindings = new HashMap<Integer, BindingFunction>();
    }

    public boolean getKey(int binding) {
        return values.contains(binding);
    }

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

        for (Map.Entry<Integer, BindingFunction> entry : bindings.entrySet()) {
            if (entry.getKey() == keyCode) {
                entry.getValue().function();
            }
        }

        values.add(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        values.remove(keyCode);
    }

}
package edu.stuylib.input.keyboard;

import edu.stuylib.input.keyboard.NetworkKeyListener;

import java.awt.Frame;
import java.awt.Label;
import java.awt.FlowLayout;

/**
 * This is a simple class that
 * opens a Java AWT window, which
 * has a KeyListener that uploads
 * keyboard information to a 
 * network table
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class KeyboardInputWindow extends Frame {

    /**
     * Serial Version UID for Frame
     */
    private static final long serialVersionUID = -8251414743098271551L;

    /**
     * Create default KeyboardInputWindow (RECOMMENDED)
     */
    public KeyboardInputWindow() {
        this(NetworkKeyListener.DEFAULT_TABLE);
    }

    /**
     * Open custom table for KeyboardInputWindow
     * @param table table
     */
    public KeyboardInputWindow(String table) {
        // Set Title and Open Network Table
        super("Network Keyboard Input [" + table + "]");
        addKeyListener(new NetworkKeyListener(table));

        // Add Lable to Instruct User
        Label message = new Label("Focus This Window to\nCapture Key Strokes!");
        add(message);

        // Display Window
        setLayout(new FlowLayout());
        setSize(640, 480);
        setVisible(true);
    }

}
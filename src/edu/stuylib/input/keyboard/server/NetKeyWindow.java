package edu.stuylib.input.keyboard.server;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.input.keyboard.server.NetKeyListener;

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

public class NetKeyWindow extends Frame {

    /**
     * Serial Version UID for Frame
     */
    private static final long serialVersionUID = -8251414743098271551L;

    /**
     * Create default KeyboardInputWindow (RECOMMENDED)
     */
    public NetKeyWindow() {
        this(NetKeyboardInfo.DEFAULT_TABLE);
    }

    /**
     * Open custom table for KeyboardInputWindow
     * @param table table
     */
    public NetKeyWindow(String table) {
        // Set Title and Open Network Table
        super("Network Keyboard Input [" + table + "]");
        addKeyListener(new NetKeyListener(table));

        // Add Lable to Instruct User
        Label[] messages = { 
            new Label("Focus This Window To Capture Key Strokes!", Label.CENTER)
        };

        for(Label message : messages) {
            add(message);
        }

        // Display Window
        setLayout(new FlowLayout());
        setSize(640, 480);
        setVisible(true);
    }
}
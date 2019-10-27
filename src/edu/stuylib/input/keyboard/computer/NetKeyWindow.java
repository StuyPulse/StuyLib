package edu.stuylib.input.keyboard.computer;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.input.keyboard.computer.NetKeyListener;

import java.awt.Frame;
import java.awt.Label;
import java.awt.FlowLayout;
import javax.swing.JOptionPane;

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

        int team = -1;

        // Get team number from user
        while(team < 0) {
            try {
                String teamNum = JOptionPane.showInputDialog("Enter Team Number:");
                int conversion = Integer.parseInt(teamNum);
                team = conversion;
            } catch(Exception e) {}
        }

        addKeyListener(new NetKeyListener(team, table));
        add(new Label("Focus This Window To Capture Key Strokes!", Label.CENTER));
        setLayout(new FlowLayout());
        setSize(640, 480);
        pack();
        setVisible(true);
    }
}
package edu.stuylib.input.keyboard.computer;

import edu.stuylib.input.keyboard.NetKeyboardInfo;
import edu.stuylib.input.keyboard.computer.NetKeyListener;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This is a simple class that
 * opens a Java AWT window, which
 * has a KeyListener that uploads
 * keyboard information to a 
 * network table
 * 
 * @author Sam (sam.belliveau@gmail.com)
 */

public class NetKeyWindow extends JFrame {

    /**
     * Serial Version UID for Frame
     */
    private static final long serialVersionUID = -8251414743098271551L;

    /**
     * NetKeyListener for window
     */
    private NetKeyListener mListener;

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

        // Get team number from user
        int team = -1;
        while(team < 0) {
            try {
                String teamNum = JOptionPane.showInputDialog("Enter Team Number:");
                int conversion = Integer.parseInt(teamNum);
                team = conversion;
            } catch(Exception e) {}
        }

        // Connect NetKeyListener
        mListener = new NetKeyListener(team, table);
        addKeyListener(mListener);

        // Set Title
        setTitle("Network Keyboard Input [" + table + "]");

        // Message
        JLabel message = new JLabel("[Sending Keyboard Input to Robot Team " + team + "]");
        message.setBorder(new LineBorder(Color.BLACK, 4));

        // Message Panel
        final JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.add(message);
        getContentPane().add(messagePanel);

        // Pack and Set Size
        pack();

        // Set Visible
        setVisible(true);
    }
}
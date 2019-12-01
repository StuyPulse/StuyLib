package com.stuypulse.stuylib.input.keyboard.computer;

import com.stuypulse.stuylib.input.keyboard.computer.NetKeyListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This is a simple class that opens a Java AWT window, which has a KeyListener
 * that uploads keyboard information to a network table
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
     * Different Layouts for the different inputs 
     */
    private JPanel team_panel;
    private JPanel port_panel;
    private JPanel connect_panel;

    /**
     * Different items in the window that will be used
     */
    private JTextField team_input; 
    private JComboBox<Integer> port_input; 
    private JButton connect_button; 
    
    /**
     * Updates team listener with specified team # and port #
     * @param team team #
     * @param port port #
     */
    private void updateKeyListener(int team, int port) {
        // Remove old key listener
        if(mListener != null) {
            removeKeyListener(mListener);
        }

        // Add key listener
        mListener = new NetKeyListener(team, port);
        addKeyListener(mListener);

        // Set Title to Configuration
        setTitle("Network Keyboard Input [Team: " + team + ", Port: " + port + "]");
    }

    /**
     * Updates team listener with the values from the window
     */
    private void updateKeyListener() {
        int team = 0;
        int port = (Integer)port_input.getSelectedItem();

        try {
            team = Integer.valueOf(team_input.getText());
        } catch (NumberFormatException e) {
            team = 0;
        }

        updateKeyListener(team, port);
    }

    /**
     * Opens Network Keyboard Input Window
     */
    public NetKeyWindow() {
        // Set Title and Open Network Table
        super("Network Keyboard Input");

        // Setup the panels
        team_panel = new JPanel();
        port_panel = new JPanel();
        connect_panel = new JPanel();

        // Set the layout for each of the panels
        team_panel.setLayout(new FlowLayout());
        port_panel.setLayout(new FlowLayout());
        connect_panel.setLayout(new FlowLayout());
        
        // Say what each entry does
        team_panel.add(new JLabel("Team Number: "));
        port_panel.add(new JLabel("Selected Port: "));

        // Make text feild for team number
        team_input = new JTextField(5);
        team_input.setText("694");
        team_panel.add(team_input);

        // Port number combo box
        port_input = new JComboBox<Integer>(new Integer[]{0, 1, 2, 3});
        port_input.setSelectedIndex(0);
        port_panel.add(port_input);

        // Make a button that connects the user to that team number and port
        connect_button = new JButton("Connect To Network Keyboard");
        connect_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateKeyListener();
            }
        });
        connect_panel.add(connect_button);

        // Layout for entire window
        setLayout(new BorderLayout(4, 4));

        // Add each of the panels
        add(team_panel, BorderLayout.NORTH);
        add(port_panel, BorderLayout.CENTER);
        add(connect_panel, BorderLayout.SOUTH);

        // Pack
        pack();

        // Set Visible
        setVisible(true);
    }
}
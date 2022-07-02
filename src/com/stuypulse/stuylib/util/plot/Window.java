package com.stuypulse.stuylib.util.plot;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class Window {

	public static void createWindow(Plot... graphs) {

	}

	private List<Plot> graphs;

	public Window(String title, int width, int height, Plot... plots) {
		graphs = Arrays.asList(plots);
		
		JFrame frame = new JFrame(title);
		
        frame.getContentPane()
                .setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);

		// from SwingWrapper.java from XChart
		int rows = (int) (Math.sqrt(graphs.size()) + .5);
		int cols = (int) ((double) graphs.size() / rows + 1);

		frame.getContentPane().setLayout(new GridLayout(rows, cols));

		for (Plot plot : graphs) {
			frame.add(plot.getPanel());
		}

		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void update() {
		for (Plot plot : graphs) {
			if (plot.isRunning())
				plot.update();
		}
	}

}

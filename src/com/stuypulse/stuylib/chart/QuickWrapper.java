package com.stuypulse.stuylib.chart;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.stuypulse.stuylib.exception.ConstructionError;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import java.lang.Math;

public class QuickWrapper extends KeyAdapter {

    /**
     * TODO:
     * Test through using 
     * Make NChart.java which can handle mulitple lines (multiple series)
     * 
     * Think about way to make a constant stream, but it  def seems more practical to update a List<Double> that is constantly passed in
     * ^^^ consider how threading certain information would effect this
     */

    // consider how putting the same thing into varargs will work with .indexOf() and .remove() functions
    // should be fine because if you update it, it will refer to the same object

    // i have concerns about duplicates and multiple JFrames
    // just needs use testing for now

    public static final int FRAME_OFFSET = 0;
    public static final int MAX_CHARTS = 10;

    private static XYChart[] chartToXy(Chart... newCharts) {
        XYChart[] g2r = new XYChart[newCharts.length];
        for (int i = 0; i < newCharts.length;i++) {
            g2r[i] = newCharts[i].get();
        }
        return g2r;
    }

    private Map<Integer,Integer> bindings; // index of jframe, key event
    private List<XYChart> charts;
    private List<JFrame> frames;

    public QuickWrapper() {
        charts = new ArrayList<XYChart>();
        frames = new ArrayList<JFrame>();
        bindings = new HashMap<Integer,Integer>();
    }

    public QuickWrapper(XYChart... newCharts) throws ConstructionError {
        this();
        if (newCharts.length < 1 || newCharts.length > MAX_CHARTS) {
            throw new ConstructionError("QuickWrapper(XYChart... charts)", "Must contain between 1 and " + MAX_CHARTS + " charts.");
        }
        add(newCharts);

        show(0);
    }

    public QuickWrapper(Chart... newCharts) throws ConstructionError {
        // separate constructor so add(Chart...) is called, which will set bindings
        this();
        if (newCharts.length < 1 || newCharts.length > MAX_CHARTS) {
            throw new ConstructionError("QuickWrapper(XYChart... charts)", "Must contain between 1 and " + MAX_CHARTS + " charts.");
        }
        add(newCharts);

        show(0);
    }

    private JFrame getFrame(String title) {
        for (int i = 0;i < frames.size();i++) {
            if (frames.get(i).getTitle().equals(title)) {
                return frames.get(i);
            }
        }
        return null;
    }

    private JFrame getFrame(int index) {
        if (index > frames.size() - 1 || index < 0) return null;
        return frames.get(index);
    }

    private XYChart getChart(String title) {
        for (int i = 0;i < charts.size();i++) {
            if (charts.get(i).getTitle().equals(title)) {
                return charts.get(i);
            }
        }
        return null;
    }

    private XYChart getChart(int index) {
        if (index > charts.size() - 1 || index < 0) return null;
        return charts.get(index);
    }

    public void show(int index) {
        if (getFrame(index) != null) {
            getFrame(index).setVisible(true);
        }
    }

    public void show(String title) {
       if (getFrame(title) != null) {
           getFrame(title).setVisible(true);
       }
    }


    public void showAll() {
        for (JFrame frame : frames) {
            frame.setVisible(true);
        }
    }

    public void hide(int index) {
        if (getFrame(index) != null) {
            getFrame(index).setVisible(false);
        }
    }

    public void hide(String title) {
        if (getFrame(title) != null) {
            getFrame(title).setVisible(false);
        }
    }

    public void hideAll() {
        for (JFrame frame : frames) {
            frame.setVisible(false);
        }
    }

    public QuickWrapper add(XYChart... newCharts) {
        for (XYChart newChart : newCharts) {
            charts.add(newChart);
        }

        for (XYChart newChart : newCharts) { 
            JFrame newFrame = new JFrame(newChart.getTitle());

            javax.swing.SwingUtilities.invokeLater(() -> {
                  newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  XChartPanel<XYChart> panel = new XChartPanel<XYChart>(newChart);

                  newFrame.add(panel);
                  newFrame.addKeyListener(this);
                  newFrame.setResizable(false);

                  newFrame.setLocation(newFrame.getX() + FRAME_OFFSET , newFrame.getY() + FRAME_OFFSET);
                  newFrame.pack();
            });

            frames.add(newFrame);
        }

        return this;
    }

    public QuickWrapper add(Chart... newCharts) {
        add(chartToXy(newCharts));

        for (Chart newChart : newCharts) {
            if (newChart.getBinding() != -1) {
                setBinding(newChart, newChart.getBinding());
            }
        }

        return this;
    }

    public QuickWrapper remove(JFrame... rFrames) {
        for (JFrame rFrame : rFrames) {
            rFrame.setVisible(false);
            charts.remove(frames.indexOf(rFrame));
            frames.remove(rFrame);
        }

        return this;
    }

    public String toString() {
        return frames.size() + "\n" + charts.size();
    }

    public QuickWrapper setBinding(int index, int binding) {
        bindings.put(index, binding);
        return this;
    }

    public QuickWrapper setBinding(Chart chart, int binding) { 
        return setBinding(charts.indexOf(chart.get()), binding);
    }

    public QuickWrapper update(int index, double[] newXData, double[] newYData ) {
        getChart(index).updateXYSeries(charts.get(index).getTitle(), newXData, newYData, null);
        return this;
    }

    public QuickWrapper update(String title, double[] newXData, double[] newYData ) {
        getChart(title).updateXYSeries(title, newXData, newYData, null);
        return this;
    }

    public QuickWrapper update(int index, List<Double> newXData, List<Double> newYData ) {
        getChart(index).updateXYSeries(charts.get(index).getTitle(), newXData, newYData, null);
        return this;
    }

    public QuickWrapper update(String title, List<Double> newXData, List<Double> newYData) {
        getChart(title).updateXYSeries(title, newXData, newYData, null);
        return this;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            remove((JFrame) e.getSource());
            if (frames.size() == 0 || charts.size() == 0) {
                System.exit(1);
            }
        }

        for (Map.Entry<Integer,Integer> entry : bindings.entrySet()) {
            if (entry.getValue() == keyCode) {
                hideAll();
                show(entry.getKey());
            }
        }

        // TESTING PURPOSES this if can be deleted
        if (keyCode == KeyEvent.VK_SPACE) spacePressed = true;

    }

    // TESTING PURPOSES spacePressed can be deleted
    private boolean spacePressed;

    // TESTING PURPOSES getSpacePressed can be deleted
    private synchronized boolean getSpacePressed() {
        return spacePressed;
    }

    // TESTING PURPOES keyReleased can be deleted
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) spacePressed = false;
    }

    public static void main(String[] args) throws InterruptedException {
        final String x = "x";
        final String y = "y";

        //XYChart nativeChart = QuickChart.getChart(title, x, y,"default value", new double[] {0.0}, new double[] {0.0});
        
        Chart chart = new Chart("Chart 1",x,y,KeyEvent.VK_1);
        Chart chart1 = new Chart("Chart 2",x,y,KeyEvent.VK_2);
        Chart chart2 = new Chart("Chart 3",x,y,KeyEvent.VK_3);

        QuickWrapper qw = new QuickWrapper(chart,chart1,chart2);

        List<Double> xData = new ArrayList<Double>();
        List<Double> yData = new ArrayList<Double>();
        List<Double> xData1 = new ArrayList<Double>();
        List<Double> yData1 = new ArrayList<Double>();
        List<Double> xData2 = new ArrayList<Double>();
        List<Double> yData2 = new ArrayList<Double>();
        
        for (double i = 0;i < Math.PI * 10;i+= Math.PI/100) {
            xData.add(i);
            yData.add(Math.sin(i));
            xData1.add(i);
            yData1.add(Math.cos(i));
            xData2.add(i);
            yData2.add(i * Math.random() * Math.PI);
        
            qw.update("Chart 1", xData, yData);
            qw.update("Chart 2", xData1, yData1);
            qw.update("Chart 3", xData2, yData2);
        }

    }


}
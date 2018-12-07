/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javafx.scene.control.TabPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
public abstract class AbstractChartPanel extends AbstractPanel {

    protected JFreeChart chart;

    protected TimeSeries series;
    protected TabPane parent;
    protected JPanel toolbar = new JPanel();

    public AbstractChartPanel(TimeSeries series, TabPane parent) {
        this.series = series;
        this.parent = parent;
        init();
        initData();
        initUi();
    }

    protected void init() {

    }

    protected void initUi() {
        setLayout(new BorderLayout());
        initToolbar();
        addCharts();
    }

    protected void addCharts() {
        chartPanel = createChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }
    protected void removeCharts(){
        this.remove(chartPanel);
    }

    protected void initToolbar() {
        JButton bttRefresh = new JButton();
        bttRefresh.setText("Refresh");
        bttRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                refresh();
            }
        });
        //toolbar.add(bttRefresh);
        this.add(toolbar, BorderLayout.NORTH);
    }



    public void refresh() {
        removeCharts();
        initData();
        addCharts();
        repaint();
    }

    protected abstract void initData();

    public TimeSeries getSeries() {
        return series;
    }

     public void setSeries(TimeSeries series) {
        this.series = series;
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }
    
    


}

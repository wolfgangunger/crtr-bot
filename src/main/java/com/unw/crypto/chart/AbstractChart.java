/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;

/**
 *@deprecated 
 * @author UNGERW
 */
public abstract class AbstractChart {

    protected JFreeChart chart;
    protected TimeSeries series;

    public AbstractChart(TimeSeries series) {
        this.series = series;
        initData();
    }
    public void refresh(){
        initData();
    }

    protected abstract void initData();

    public JFreeChart getChart() {
        return chart;
    }

    protected JFreeChart createChart(TimeSeriesCollection dataset, String title, String xAxis, String yAxis) {
        chart = ChartFactory.createTimeSeriesChart(
                title, // title
                xAxis, // x-axis label
                yAxis, // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        return chart;
    }

}

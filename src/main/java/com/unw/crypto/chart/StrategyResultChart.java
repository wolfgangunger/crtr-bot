/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import java.text.SimpleDateFormat;
import javafx.scene.control.TabPane;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class StrategyResultChart extends AbstractPanel {

    protected TimeSeries series;
    protected TabPane parent;
    protected JFreeChart chart;

    public StrategyResultChart(TimeSeries series, TabPane parent) {
        this.series = series;
        this.parent = parent;
    }

    public StrategyResultChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        this.series = series;
        this.parent = parent;
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    protected void initData() {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, legend));
        chart = createChart(dataset, legend, "Date", "Price", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));
        //panel.set
    }
}

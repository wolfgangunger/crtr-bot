/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.Config;
import com.unw.crypto.ui.NumericTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javafx.scene.control.TabPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class EMAStoChart extends AbstractChartPanel {

    private JFreeChart chart2;
    private ChartPanel chartPanel2;
    private int timeFrame;
    private NumericTextField inputTimeframe;

    public EMAStoChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public EMAStoChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    @Override
    public void refresh() {
        timeFrame = Integer.valueOf(inputTimeframe.getText());
        super.refresh();
    }
    
    @Override
    public void reload(String currency, String exchange) {
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        refresh();
    }

    @Override
    protected void init() {
        timeFrame = 2;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        inputTimeframe = new NumericTextField();
        inputTimeframe.setText(String.valueOf(timeFrame));
        inputTimeframe.setColumns(10);
        toolbar.add(inputTimeframe);
    }

    @Override
    protected void addCharts() {
        this.add(chartPanel, BorderLayout.CENTER);
        this.add(chartPanel2, BorderLayout.SOUTH);
    }

    @Override
    protected void removeCharts() {
        this.remove(chartPanel);
        this.remove(chartPanel2);
    }

    @Override
    protected void initData() {

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        //test
        // EMAIndicator avg14 = new EMAIndicator(closePrice, 180);
        StochasticRSIIndicator stoRsi = new StochasticRSIIndicator(closePrice, timeFrame);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset2.addSeries(buildChartTimeSeries(series, stoRsi, "Sto RSI"));

        chart = createChart(dataset1, legend, "Date", "Price", true);
        chart2 = createChart(dataset2, "Stochastik", "Date", "100", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));

        chartPanel = new ChartPanel(chart);
        Dimension d1 = new Dimension(Config.WIDTH, (int) ((Config.HEIGHT - 110) * 0.7));
        chartPanel.setPreferredSize(d1);

        chartPanel2 = new ChartPanel(chart2);
        //chartPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        Dimension d2 = new Dimension(Config.WIDTH, (int) ((Config.HEIGHT - 110) * 0.3));
        chartPanel2.setPreferredSize(d2);
    }

}

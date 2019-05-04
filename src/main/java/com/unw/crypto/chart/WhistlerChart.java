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
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;

/**
 *
 * @author UNGERW
 */
public class WhistlerChart extends AbstractChartPanel {

    private JFreeChart chart2;
    private ChartPanel chartPanel2;
    private int timeFrame;
    private NumericTextField inputTimeframe;

    public WhistlerChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public WhistlerChart(TimeSeries series, TabPane parent, String currency, String exchange) {
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
//        EMAIndicator avg14 = new EMAIndicator(closePrice, 180); // 180 = 14 tage ?

        VWAPIndicator vwap1 = new VWAPIndicator(series, 500);
        VWAPIndicator vwap2 = new VWAPIndicator(series, 1000);

        CCIIndicator cci14 = new CCIIndicator(series, 14);
        CCIIndicator cci50 = new CCIIndicator(series, 50);
        CCIIndicator cci100 = new CCIIndicator(series, 100);
        CCIIndicator cci200 = new CCIIndicator(series, 200);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset1.addSeries(buildChartTimeSeries(series, vwap1, "VWAP 500"));
        dataset1.addSeries(buildChartTimeSeries(series, vwap2, "VWAP 1000"));

        dataset2.addSeries(buildChartTimeSeries(series, cci14, "CCI 14"));
        dataset2.addSeries(buildChartTimeSeries(series, cci50, "CCI 50"));
        dataset2.addSeries(buildChartTimeSeries(series, cci100, "CCI 100"));
        dataset2.addSeries(buildChartTimeSeries(series, cci200, "CCI 200"));

        chart = createChart(dataset1, legend, "Date", "Price & VWAP", true);
        chart2 = createChart(dataset2, "CCI", "Date", "100", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));

        chartPanel = new ChartPanel(chart);
        Dimension d1 = new Dimension(Config.WIDTH, (int) ((Config.HEIGHT - 110) * 0.6));
        chartPanel.setPreferredSize(d1);
        chartPanel2 = new ChartPanel(chart2);
        Dimension d2 = new Dimension(Config.WIDTH, (int) ((Config.HEIGHT - 110) * 0.4));
        chartPanel2.setPreferredSize(d2);
    }

}

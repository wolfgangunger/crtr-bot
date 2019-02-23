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
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class CCIChart extends AbstractChartPanel {

    private JFreeChart chart2;
    private ChartPanel chartPanel2;
    private int timeFrameCCI14;
    private int timeFrameCCI50;
    private int timeFrameCCI100;
    private int timeFrameCCI200;
    private NumericTextField inputTimeframeCCI14;
    private NumericTextField inputTimeframeCCI50;
    private NumericTextField inputTimeframeCCI100;
    private NumericTextField inputTimeframeCCI200;

    public CCIChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public CCIChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    @Override
    public void refresh() {
        timeFrameCCI14 = Integer.valueOf(inputTimeframeCCI14.getText());
        timeFrameCCI50 = Integer.valueOf(inputTimeframeCCI50.getText());
        timeFrameCCI100 = Integer.valueOf(inputTimeframeCCI100.getText());
        timeFrameCCI200 = Integer.valueOf(inputTimeframeCCI200.getText());
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
        timeFrameCCI14 = 14;
        timeFrameCCI50 = 50;
        timeFrameCCI100 = 100;
        timeFrameCCI200 = 200;

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        inputTimeframeCCI14 = new NumericTextField();
        inputTimeframeCCI14.setText(String.valueOf(timeFrameCCI14));
        inputTimeframeCCI14.setColumns(5);
        inputTimeframeCCI50 = new NumericTextField();
        inputTimeframeCCI50.setText(String.valueOf(timeFrameCCI50));
        inputTimeframeCCI50.setColumns(5);

        inputTimeframeCCI100 = new NumericTextField();
        inputTimeframeCCI100.setText(String.valueOf(timeFrameCCI100));
        inputTimeframeCCI100.setColumns(5);

        inputTimeframeCCI200 = new NumericTextField();
        inputTimeframeCCI200.setText(String.valueOf(timeFrameCCI200));
        inputTimeframeCCI200.setColumns(5);

        toolbar.add(inputTimeframeCCI14);
        toolbar.add(inputTimeframeCCI50);
        toolbar.add(inputTimeframeCCI100);
        toolbar.add(inputTimeframeCCI200);
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

        CCIIndicator cci1 = new CCIIndicator(series, timeFrameCCI14);
        CCIIndicator cci2 = new CCIIndicator(series, timeFrameCCI50);
        CCIIndicator cci3 = new CCIIndicator(series, timeFrameCCI100);
        CCIIndicator cci4 = new CCIIndicator(series, timeFrameCCI200);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));

        dataset2.addSeries(buildChartTimeSeries(series, cci1, "CCI 14"));
        dataset2.addSeries(buildChartTimeSeries(series, cci2, "CCI 50"));
        dataset2.addSeries(buildChartTimeSeries(series, cci3, "CCI 100"));
        dataset2.addSeries(buildChartTimeSeries(series, cci4, "CCI 200"));

//dataset2.addSeries(buildChartTimeSeriesWithFactor(series, stoRsi, "Sto RSI", 100d));
        chart = createChart(dataset1, legend, "Date", "Price", true);
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

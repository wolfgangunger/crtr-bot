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
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class EMARsiStoChart extends AbstractChartPanel {

    private JFreeChart chart2;
    private ChartPanel chartPanel2;
    private int timeFrameRSI;
    private int timeFrameSTO;
    private NumericTextField inputTimeframeRSI;
    private NumericTextField inputTimeframeSTO;

    public EMARsiStoChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public EMARsiStoChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    @Override
    public void refresh() {
        timeFrameRSI = Integer.valueOf(inputTimeframeRSI.getText());
        timeFrameSTO = Integer.valueOf(inputTimeframeSTO.getText());
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
        timeFrameRSI = 2;
        timeFrameSTO = 4;

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        inputTimeframeRSI = new NumericTextField();
        inputTimeframeRSI.setText(String.valueOf(timeFrameRSI));
        inputTimeframeRSI.setColumns(10);
        inputTimeframeSTO = new NumericTextField();
        inputTimeframeSTO.setText(String.valueOf(timeFrameSTO));
        inputTimeframeSTO.setColumns(10);
        toolbar.add(inputTimeframeRSI);
        toolbar.add(inputTimeframeSTO);
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
        EMAIndicator avg14 = new EMAIndicator(closePrice, 180); // 180 = 14 tage ?
        RSIIndicator rsi = new RSIIndicator(closePrice, timeFrameRSI);
        StochasticRSIIndicator stoRsi = new StochasticRSIIndicator(closePrice, timeFrameSTO);
        StochasticOscillatorKIndicator stoK = new StochasticOscillatorKIndicator(series, timeFrameSTO);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset2.addSeries(buildChartTimeSeries(series, rsi, "RSI"));
//        dataset2.addSeries(buildChartTimeSeriesWithFactor(series, stoRsi, "Sto RSI", 100d));
//        dataset2.addSeries(buildChartTimeSeriesWithFactor(series, stoK, "Sto K", 100d));
          dataset2.addSeries(buildChartTimeSeries(series, stoK, "Sto K"));

        chart = createChart(dataset1, legend, "Date", "Price", true);
        chart2 = createChart(dataset2, "RSI/STO", "Date", "100", true);
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

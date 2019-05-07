/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.ui.NumericTextField;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import javafx.scene.control.TabPane;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.indicators.volume.VWAPIndicator;

/**
 *
 * @author UNGERW
 */
public class VWAPChart extends AbstractChartPanel {

    private int timeFrame1;
    private int timeFrame2;
    private NumericTextField inputTimeframe1;
    private NumericTextField inputTimeframe2;

    public VWAPChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    @Override
    protected void refresh() {
        timeFrame1 = Integer.valueOf(inputTimeframe1.getText());
        timeFrame2 = Integer.valueOf(inputTimeframe2.getText());
        super.refresh(); //To change body of generated methods, choose Tools | Templates.
    }

    public VWAPChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
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
        timeFrame1 = 100;
        timeFrame2 = 200;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        inputTimeframe1 = new NumericTextField();
        inputTimeframe1.setText(String.valueOf(timeFrame1));
        inputTimeframe1.setColumns(5);
        toolbar.add(inputTimeframe1);
        inputTimeframe2 = new NumericTextField();
        inputTimeframe2.setText(String.valueOf(timeFrame1));
        inputTimeframe2.setColumns(5);
        toolbar.add(inputTimeframe2);
    }


    @Override
    protected void initData() {

        // Close price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator avg14 = new EMAIndicator(closePrice, 14);
        StandardDeviationIndicator sd14 = new StandardDeviationIndicator(closePrice, 14);

        VWAPIndicator vwap1 = new VWAPIndicator(series, timeFrame1);
        VWAPIndicator vwap2 = new VWAPIndicator(series, timeFrame2);

        /*
          Building chart dataset
         */
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, "Bitcoin"));
        dataset.addSeries(buildChartTimeSeries(series, vwap1, "VWAP 1"));
        dataset.addSeries(buildChartTimeSeries(series, vwap2, "VWAP 2"));

        chart = createChart(dataset, legend, "Date", "Price & VWAP", true);

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

    }

}

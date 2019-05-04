/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

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

    public VWAPChart(TimeSeries series, TabPane parent) {
        super(series, parent);
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
    protected void initData() {

        // Close price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator avg14 = new EMAIndicator(closePrice, 14);
        StandardDeviationIndicator sd14 = new StandardDeviationIndicator(closePrice, 14);

        VWAPIndicator vwap1 = new VWAPIndicator(series, 500);
        VWAPIndicator vwap2 = new VWAPIndicator(series, 1000);
        /*
          Building chart dataset
         */
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, "Bitcoin"));
        dataset.addSeries(buildChartTimeSeries(series, vwap1, "VWAP 500"));
        dataset.addSeries(buildChartTimeSeries(series, vwap2, "VWAP 1000"));

        chart = createChart(dataset, legend, "Date", "Price & VWAP", true);

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

    }

}

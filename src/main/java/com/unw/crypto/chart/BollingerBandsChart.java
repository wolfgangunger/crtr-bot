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
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

/**
 *
 * @author UNGERW
 */
public class BollingerBandsChart extends AbstractChartPanel {

    public BollingerBandsChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public BollingerBandsChart(TimeSeries series, TabPane parent, String currency, String exchange) {
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

        // Bollinger bands
        BollingerBandsMiddleIndicator middleBBand = new BollingerBandsMiddleIndicator(avg14);
        BollingerBandsLowerIndicator lowBBand = new BollingerBandsLowerIndicator(middleBBand, sd14);
        BollingerBandsUpperIndicator upBBand = new BollingerBandsUpperIndicator(middleBBand, sd14);

        /*
          Building chart dataset
         */
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, "Bitcoin"));
        dataset.addSeries(buildChartTimeSeries(series, lowBBand, "Low Bollinger Band"));
        dataset.addSeries(buildChartTimeSeries(series, upBBand, "High Bollinger Band"));

        chart = createChart(dataset, legend, "Date", "Price", true);

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

    }

}

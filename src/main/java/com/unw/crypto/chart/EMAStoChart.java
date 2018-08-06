/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.Config;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TabPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.Trade;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class EMAStoChart extends AbstractChartPanel {

    private  JFreeChart chart2;

    public EMAStoChart(TimeSeries series,TabPane parent) {
        super(series, parent);
    }

    @Override
    protected void initData() {

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        //test
        EMAIndicator avg14 = new EMAIndicator(closePrice, 180);
        StochasticRSIIndicator stoRsi = new StochasticRSIIndicator(closePrice, 200);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, "Bitstamp Bitcoin (BTC)"));
        //dataset1.addSeries(buildChartTimeSeries(series, avg14, "MA 14"));
        dataset2.addSeries(buildChartTimeSeries(series, stoRsi, "Sto RSI"));

        chart = createChart(dataset1, "Bitstamp BTC", "Date", "Price", true);
        chart2 = createChart(dataset2, "Stochastik", "Date", "100", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));

        ChartPanel chartPanel1 = new ChartPanel(chart);
        //chartPanel1.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        Dimension d1 = new Dimension(Config.WIDTH,(int)((Config.HEIGHT - 110)*0.7));
        chartPanel1.setPreferredSize(d1);
        ChartPanel chartPanel2 = new ChartPanel(chart2);
        //chartPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        Dimension d2 = new Dimension(Config.WIDTH,(int)((Config.HEIGHT - 110)*0.3));
        chartPanel2.setPreferredSize(d2);
        this.add(chartPanel1);
        this.add(chartPanel2);
    }

    /**
     * Runs a strategy over a time series and adds the value markers
     * corresponding to buy/sell signals to the plot.
     *
     * @param series a time series
     * @param strategy a trading strategy
     * @param plot the plot
     */
    private void addBuySellSignals(TimeSeries series, Strategy strategy, XYPlot plot) {
        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        List<Trade> trades = seriesManager.run(strategy).getTrades();
        // Adding markers to plot
        for (Trade trade : trades) {
            // Buy signal
            double buySignalBarTime = new Minute(Date.from(series.getBar(trade.getEntry().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker buyMarker = new ValueMarker(buySignalBarTime);
            buyMarker.setPaint(Color.GREEN);
            buyMarker.setLabel("B");
            plot.addDomainMarker(buyMarker);
            // Sell signal
            double sellSignalBarTime = new Minute(Date.from(series.getBar(trade.getExit().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker sellMarker = new ValueMarker(sellSignalBarTime);
            sellMarker.setPaint(Color.RED);
            sellMarker.setLabel("S");
            plot.addDomainMarker(sellMarker);
        }
    }
}

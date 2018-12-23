/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.model.ExtOrder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.util.Date;
import java.util.List;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.OHLCDataset;
import org.ta4j.core.Bar;
import org.ta4j.core.Order;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.Trade;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public final class ChartUtil {

    public static TimeSeriesCollection createAdditionalDataset(TimeSeries series, String legend) {
        ClosePriceIndicator indicator = new ClosePriceIndicator(series);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        org.jfree.data.time.TimeSeries chartTimeSeries = new org.jfree.data.time.TimeSeries(legend);
        for (int i = 0; i < series.getBarCount(); i++) {
            Bar bar = series.getBar(i);
            try {
                chartTimeSeries.add(new Second(new Date(bar.getEndTime().toEpochSecond() * 1000)), indicator.getValue(i).doubleValue());
                //chartTimeSeries.addAndOrUpdate(new Second(new Date(bar.getEndTime().toEpochSecond() * 1000)), indicator.getValue(i).toDouble());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dataset.addSeries(chartTimeSeries);
        return dataset;
    }

    public static OHLCDataset createOHLCDataset(TimeSeries series) {
        final int nbBars = series.getBarCount();

        Date[] dates = new Date[nbBars];
        double[] opens = new double[nbBars];
        double[] highs = new double[nbBars];
        double[] lows = new double[nbBars];
        double[] closes = new double[nbBars];
        double[] volumes = new double[nbBars];

        for (int i = 0; i < nbBars; i++) {
            Bar bar = series.getBar(i);
            dates[i] = new Date(bar.getEndTime().toEpochSecond() * 1000);
            opens[i] = bar.getOpenPrice().doubleValue();
            highs[i] = bar.getMaxPrice().doubleValue();
            lows[i] = bar.getMinPrice().doubleValue();
            closes[i] = bar.getClosePrice().doubleValue();
            volumes[i] = bar.getVolume().doubleValue();
        }

        return new DefaultHighLowDataset("btc", dates, highs, lows, opens, closes, volumes);
    }

    /**
     * Runs a strategy over a time series and adds the value markers
     * corresponding to buy/sell signals to the plot.
     *
     * @param series a time series
     * @param strategy a trading strategy
     * @param plot the plot
     */
    public static void addBuySellSignals(TimeSeries series, Strategy strategy, XYPlot plot) {
        // Running the strategy
        if (series == null || strategy == null) {
            // should not happen
            return;
        }
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        List<Trade> trades = seriesManager.run(strategy).getTrades();
        // Adding markers to plot
        for (Trade trade : trades) {
            // Buy signal
            double buySignalBarTime = new Minute(Date.from(series.getBar(trade.getEntry().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker buyMarker = new ValueMarker(buySignalBarTime);
            buyMarker.setPaint(Color.GREEN);
            buyMarker.setLabel("B");
            Stroke stroke = new BasicStroke(3);
            buyMarker.setStroke(stroke);
            plot.addDomainMarker(buyMarker);
            // Sell signal
            double sellSignalBarTime = new Minute(Date.from(series.getBar(trade.getExit().getIndex()).getEndTime().toInstant())).getFirstMillisecond();
            Marker sellMarker = new ValueMarker(sellSignalBarTime);
            sellMarker.setPaint(Color.RED);
            sellMarker.setLabel("S");
            sellMarker.setStroke(stroke);
            plot.addDomainMarker(sellMarker);
        }
    }

    public static void addBuySellSignals(List<ExtOrder> orders, XYPlot plot) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        Stroke stroke = new BasicStroke(3);
        for (ExtOrder o : orders) {
            // Buy order
            if (o.getType().equals(Order.OrderType.BUY)) {
                double buySignalBarTime = new Minute(Date.from(o.getTradeTime().toInstant())).getFirstMillisecond();
                Marker buyMarker = new ValueMarker(buySignalBarTime);
                buyMarker.setPaint(Color.GREEN);
                buyMarker.setLabel("B");
                buyMarker.setStroke(stroke);
                plot.addDomainMarker(buyMarker);
            }

            // Sell order
            if (o.getType().equals(Order.OrderType.SELL)) {
                double sellSignalBarTime = new Minute(Date.from(o.getTradeTime().toInstant())).getFirstMillisecond();
                Marker sellMarker = new ValueMarker(sellSignalBarTime);
                sellMarker.setPaint(Color.RED);
                sellMarker.setLabel("S");
                sellMarker.setStroke(stroke);
                plot.addDomainMarker(sellMarker);
            }

        }

    }

}

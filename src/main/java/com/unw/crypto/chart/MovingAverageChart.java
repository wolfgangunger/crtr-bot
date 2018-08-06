/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.ui.NumericTextField;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TabPane;
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
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class MovingAverageChart extends AbstractChartPanel {

    private int timeFrame ;
    private NumericTextField inputTimeframe ;
    
    public MovingAverageChart(TimeSeries series,TabPane parent) {
        super(series, parent);
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
    public void refresh() {
        timeFrame = Integer.valueOf(inputTimeframe.getText());
        System.out.println(timeFrame);
        super.refresh(); 
    }

    @Override
    protected void init() {
        timeFrame = 14;
    }
    
    @Override
    protected void initData() {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        //test
        EMAIndicator avg = new EMAIndicator(closePrice, timeFrame);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, "Bitstamp Bitcoin (BTC)"));
        dataset.addSeries(buildChartTimeSeries(series, avg, "MA " + timeFrame));

        chart = createChart(dataset, "Bitstamp BTC", "Date", "Price", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));
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

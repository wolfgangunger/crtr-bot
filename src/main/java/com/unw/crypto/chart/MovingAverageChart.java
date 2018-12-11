/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.ui.NumericTextField;
import java.text.SimpleDateFormat;
import javafx.scene.control.TabPane;
import javax.swing.JLabel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class MovingAverageChart extends AbstractChartPanel {

    private int timeFrameLong;
    private NumericTextField inputTimeframeLong;
    private int timeFrameShort;
    private NumericTextField inputTimeframeShort;

    public MovingAverageChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public MovingAverageChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        inputTimeframeLong = new NumericTextField();
        inputTimeframeLong.setText(String.valueOf(timeFrameLong));
        inputTimeframeLong.setColumns(10);
        inputTimeframeShort = new NumericTextField();
        inputTimeframeShort.setText(String.valueOf(timeFrameShort));
        inputTimeframeShort.setColumns(10);
        JLabel lblShortMA = new JLabel("Short MA");
        JLabel lblLongMA = new JLabel("Long MA");
        toolbar.add(lblShortMA);
        toolbar.add(inputTimeframeShort);
        toolbar.add(lblLongMA);
        toolbar.add(inputTimeframeLong);
    }

    @Override
    public void refresh() {
        timeFrameLong = Integer.valueOf(inputTimeframeLong.getText());
        timeFrameShort = Integer.valueOf(inputTimeframeShort.getText());
        super.refresh();
    }

    @Override
    protected void init() {
        timeFrameLong = 3;
        timeFrameShort = 1;
    }

    @Override
    protected void initData() {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        // multiplicator depends on candle size : candle size 60 min = 1 ; 5 min candle = 12
        int shortMa =  timeFrameShort * getMAMultiplicator();
        int longMa = timeFrameLong * getMAMultiplicator();
        EMAIndicator avgShort = new EMAIndicator(closePrice, shortMa);
        EMAIndicator avgLong = new EMAIndicator(closePrice, longMa);
        // time frame
        int ma200 = 200 * getMAMultiplicator();
        int ma314 = 314 * getMAMultiplicator();
        EMAIndicator avg200 = new EMAIndicator(closePrice, ma200);
        EMAIndicator avg314 = new EMAIndicator(closePrice, ma314);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset.addSeries(buildChartTimeSeries(series, avgShort, "MA Short" + shortMa));
        dataset.addSeries(buildChartTimeSeries(series, avgLong, "MA Long" + longMa));
        // fix ma
        dataset.addSeries(buildChartTimeSeries(series, avg200, "MA 200"));
        dataset.addSeries(buildChartTimeSeries(series, avg314, "MA 314"));

        chart = createChart(dataset, legend, "Date", "Price", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));
    }

    @Override
    public void reload(String currency, String exchange) {
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        refresh();
    }
}

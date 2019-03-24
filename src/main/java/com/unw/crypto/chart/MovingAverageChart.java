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
import org.ta4j.core.indicators.SMAIndicator;
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
        JLabel lblShortMA = new JLabel("Short MA (days)");
        JLabel lblLongMA = new JLabel("Long MA (days)");
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
        timeFrameLong = 14;
        timeFrameShort = 8;
    }

    @Override
    protected void initData() {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        // multiplicator depends on candle size : candle size 60 min = 1 ; 5 min candle = 12
//        int shortMa =  (int)(timeFrameShort * getMAMultiplicator());
//        int longMa = (int)(timeFrameLong * getMAMultiplicator());
        int shortMa = timeFrameShort;
        int longMa = timeFrameLong;
        SMAIndicator avgShort = new SMAIndicator(closePrice, shortMa);
        SMAIndicator avgLong = new SMAIndicator(closePrice, longMa);
        // time frame
//        int ma50 = (int)(50 * getMAMultiplicator());
//        int ma100 = (int)(100 * getMAMultiplicator());
        int ma50 = 50;
        int ma100 = 100;
        SMAIndicator avg50 = new SMAIndicator(closePrice, ma50);
        SMAIndicator avg100 = new SMAIndicator(closePrice, ma100);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset.addSeries(buildChartTimeSeries(series, avgShort, "MA Short" + timeFrameShort));
        dataset.addSeries(buildChartTimeSeries(series, avgLong, "MA Long" + timeFrameLong));
        // fix ma
        dataset.addSeries(buildChartTimeSeries(series, avg50, "MA 50"));
        dataset.addSeries(buildChartTimeSeries(series, avg100, "MA 100"));

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

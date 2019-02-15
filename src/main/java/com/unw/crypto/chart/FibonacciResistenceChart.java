/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import java.text.SimpleDateFormat;
import javafx.scene.control.TabPane;
import javax.swing.JComboBox;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.pivotpoints.FibonacciReversalIndicator;
import org.ta4j.core.indicators.pivotpoints.PivotPointIndicator;
import org.ta4j.core.indicators.pivotpoints.TimeLevel;

/**
 *
 * @author UNGERW
 */
public class FibonacciResistenceChart extends AbstractChartPanel {

    private JComboBox<TimeLevel> cmTimeframe;
    private TimeLevel timeLevel = TimeLevel.DAY;

    public FibonacciResistenceChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public FibonacciResistenceChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        timeLevel = TimeLevel.DAY;
    }

    @Override
    public void refresh() {
        timeLevel = (TimeLevel) cmTimeframe.getSelectedItem();
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
    protected void initToolbar() {
        super.initToolbar();
  
        cmTimeframe = new JComboBox<>();
        for (TimeLevel t : TimeLevel.values()) {
            cmTimeframe.addItem(t);
        }
        cmTimeframe.setSelectedItem(TimeLevel.DAY);
        toolbar.add(cmTimeframe);
        timeLevel = (TimeLevel) cmTimeframe.getSelectedItem();
    }

    @Override
    protected void init() {
        timeLevel = TimeLevel.DAY;
    }

    @Override
    protected void initData() {

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        PivotPointIndicator fib = new PivotPointIndicator(series, timeLevel);
        FibonacciReversalIndicator fibRevResistance1 = new FibonacciReversalIndicator(fib, FibonacciReversalIndicator.FibonacciFactor.Factor1, FibonacciReversalIndicator.FibReversalTyp.RESISTANCE);
        FibonacciReversalIndicator fibRevResistance2 = new FibonacciReversalIndicator(fib, FibonacciReversalIndicator.FibonacciFactor.Factor2, FibonacciReversalIndicator.FibReversalTyp.RESISTANCE);
        FibonacciReversalIndicator fibRevResistance3 = new FibonacciReversalIndicator(fib, FibonacciReversalIndicator.FibonacciFactor.Factor3, FibonacciReversalIndicator.FibReversalTyp.RESISTANCE);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset1.addSeries(buildChartTimeSeries(series, fib, "FIB"));
        dataset1.addSeries(buildChartTimeSeries(series, fibRevResistance1, "FIB F1"));
        dataset1.addSeries(buildChartTimeSeries(series, fibRevResistance2, "FIB F2"));
        dataset1.addSeries(buildChartTimeSeries(series, fibRevResistance3, "FIB F3"));
        chart = createChart(dataset1, legend, "Date", "Price", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));


    }

}

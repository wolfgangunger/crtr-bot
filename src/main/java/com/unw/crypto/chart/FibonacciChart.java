/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.ui.NumericTextField;
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
public class FibonacciChart extends AbstractChartPanel {

    private int timeFrame;
    private NumericTextField inputTimeframe;
    private JComboBox<TimeLevel> cmTimeframe;
    private JComboBox<FibonacciReversalIndicator.FibonacciFactor> cmbFactor;
    private FibonacciReversalIndicator.FibonacciFactor factor = FibonacciReversalIndicator.FibonacciFactor.Factor1;
    private TimeLevel timeLevel = TimeLevel.DAY;

    public FibonacciChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public FibonacciChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        factor = FibonacciReversalIndicator.FibonacciFactor.Factor1;
        timeLevel = TimeLevel.DAY;
    }

    @Override
    public void refresh() {
        timeFrame = Integer.valueOf(inputTimeframe.getText());
        timeLevel = (TimeLevel) cmTimeframe.getSelectedItem();
        factor = (FibonacciReversalIndicator.FibonacciFactor) cmbFactor.getSelectedItem();
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
        inputTimeframe = new NumericTextField();
        inputTimeframe.setText(String.valueOf(timeFrame));
        inputTimeframe.setColumns(10);

        cmTimeframe = new JComboBox<>();
        for (TimeLevel t : TimeLevel.values()) {
            cmTimeframe.addItem(t);
        }
        cmTimeframe.setSelectedItem(TimeLevel.DAY);

        cmbFactor = new JComboBox<>();
        for (FibonacciReversalIndicator.FibonacciFactor f : FibonacciReversalIndicator.FibonacciFactor.values()) {
            cmbFactor.addItem(f);
        }
        cmbFactor.setSelectedItem(FibonacciReversalIndicator.FibonacciFactor.Factor1);

        toolbar.add(inputTimeframe);
        toolbar.add(cmTimeframe);
        toolbar.add(cmbFactor);

        timeLevel = (TimeLevel) cmTimeframe.getSelectedItem();
        factor = (FibonacciReversalIndicator.FibonacciFactor) cmbFactor.getSelectedItem();
    }

    @Override
    protected void init() {
        factor = FibonacciReversalIndicator.FibonacciFactor.Factor1;
        timeLevel = TimeLevel.DAY;
    }

    
    
    @Override
    protected void initData() {

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        PivotPointIndicator fib = new PivotPointIndicator(series, timeLevel);
        FibonacciReversalIndicator fibRevResistance = new FibonacciReversalIndicator(fib, factor, FibonacciReversalIndicator.FibReversalTyp.RESISTANCE);
        FibonacciReversalIndicator fibRevSupport = new FibonacciReversalIndicator(fib, factor, FibonacciReversalIndicator.FibReversalTyp.SUPPORT);

        TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        dataset1.addSeries(buildChartTimeSeries(series, closePrice, legend));
        dataset1.addSeries(buildChartTimeSeries(series, fib, "FIB"));
        dataset1.addSeries(buildChartTimeSeries(series, fibRevResistance, "FIB Res"));
        dataset1.addSeries(buildChartTimeSeries(series, fibRevSupport, "FIB Support"));
        chart = createChart(dataset1, legend, "Date", "Price", true);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));

//        
//              chartPanel = new ChartPanel(chart);
//        Dimension d1 = new Dimension(Config.WIDTH, (int) ((Config.HEIGHT - 110) * 0.6));
//        chartPanel.setPreferredSize(d1);
    }

}

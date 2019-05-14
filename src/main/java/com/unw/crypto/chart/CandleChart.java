/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import java.awt.Color;
import javafx.scene.control.TabPane;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import org.ta4j.core.Bar;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
public class CandleChart extends AbstractChartPanel {

    public CandleChart(TimeSeries series, TabPane parent) {
        super(series, parent);
    }

    public CandleChart(TimeSeries series, TabPane parent, String currency, String exchange) {
        super(series, parent);
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
    }

    @Override
    protected void initData() {

//        int last = series.getEndIndex();
//        int first = last - 100;
//        for (int i = first; i <= last; i++) {
//            Bar b = series.getBar(i);
//            System.out.print(b.getOpenPrice() + " , ");
//            System.out.print(b.getMaxPrice()+ " , ");
//            System.out.print(b.getMinPrice()+ " , ");
//            System.out.print(b.getClosePrice()+ " , ");
//            System.out.print(b.getVolume() + "  ");
//            System.out.println("---");
//        }
        
        OHLCDataset ohlcDataset = ChartUtil.createOHLCDataset(series);
        TimeSeriesCollection xyDataset = ChartUtil.createAdditionalDataset(series, legend);

        chart = createCandleStickChart(ohlcDataset, legend, "Date", "Price");
        CandlestickRenderer renderer = new CandlestickRenderer();
        renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(renderer);
        // Additional dataset
        int index = 1;
        plot.setDataset(index, xyDataset);
        plot.mapDatasetToRangeAxis(index, 0);
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(index, Color.blue);
        plot.setRenderer(index, renderer2);
        // Misc
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setBackgroundPaint(Color.white);
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

    }

    @Override
    public void reload(String currency, String exchange) {
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        refresh();
    }

}

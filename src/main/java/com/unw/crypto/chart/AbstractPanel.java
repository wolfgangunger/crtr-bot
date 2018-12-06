/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.Config;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataset;
import org.ta4j.core.Bar;
import org.ta4j.core.Decimal;
import org.ta4j.core.Indicator;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
public class AbstractPanel extends JPanel {

    protected ChartPanel chartPanel;

    protected ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel result = new ChartPanel(chart);
        result.setBorder(BorderFactory.createEtchedBorder());
        Dimension d = new Dimension(Config.WIDTH, Config.HEIGHT - 110);
        result.setPreferredSize(d);
        return result;
    }

    protected ChartPanel createChartPanelForStrategy(JFreeChart chart) {
        ChartPanel result = new ChartPanel(chart);
        result.setBorder(BorderFactory.createEtchedBorder());
        //Dimension d = new Dimension(Config.WIDTH / 2, Config.HEIGHT - 110);
        //result.setPreferredSize(d);
        return result;
    }

    protected JFreeChart createChart(TimeSeriesCollection dataset, String title, String xAxis, String yAxis, boolean generateLegend) {
        JFreeChart result = ChartFactory.createTimeSeriesChart(
                title, // title
                xAxis, // x-axis label
                yAxis, // y-axis label
                dataset, // data
                generateLegend, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        return result;
    }

    protected JFreeChart createCandleStickChart(OHLCDataset dataset, String title, String xAxis, String yAxis) {
        JFreeChart result = ChartFactory.createCandlestickChart(
                title, // title
                xAxis, // x-axis label
                yAxis, // y-axis label
                dataset, // data
                true // create legend?
        );
        return result;
    }

    protected org.jfree.data.time.TimeSeries buildChartTimeSeries(TimeSeries barseries, Indicator<Decimal> indicator, String name) {
        org.jfree.data.time.TimeSeries chartTimeSeries = new org.jfree.data.time.TimeSeries(name);
        for (int i = 0; i < barseries.getBarCount(); i++) {
            Bar bar = barseries.getBar(i);
            try {
                chartTimeSeries.add(new Minute(Date.from(bar.getEndTime().toInstant())), indicator.getValue(i).doubleValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chartTimeSeries;
    }

}

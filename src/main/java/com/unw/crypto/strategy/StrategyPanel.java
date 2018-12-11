/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.chart.AbstractPanel;
import com.unw.crypto.chart.ChartUtil;
import com.unw.crypto.ui.NumericTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.ta4j.core.Decimal;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
public class StrategyPanel extends AbstractPanel {

    private TimeSeries series;
    private TimeSeriesCollection dataset = new TimeSeriesCollection();
    private RSI2Strategy rsi2 = new RSI2Strategy();
    private MovingMomentumStrategy mm = new MovingMomentumStrategy();
    private MovingMomentumStrategyUnw mmUnger = new MovingMomentumStrategyUnw();
    private CCICorrectionStrategy cciCorrectionStrategy = new CCICorrectionStrategy();
    private GlobalExtremaStrategy globalExtremaStrategy = new GlobalExtremaStrategy();
    private TestStrategy testStrategy = new TestStrategy();
    private AbstractStrategy currentStrategy;
    private TradingRecord tradingRecord;
    private JTextArea textArea;
    private JSplitPane mainPanel;
    protected JFreeChart chart;
    private NumericTextField maShort;
    private NumericTextField maLong;
    private int iMAShort = 1;
    private int iMALong = 3;
    private XYPlot plot;
    private static final String LB = "\n";
    private static final String TAB = "\t";

    public StrategyPanel(TimeSeries series) {
        this.series = series;
        this.setLayout(new BorderLayout());
        initData();
        initUi();
    }

    private void initData() {
        currentStrategy = rsi2;
        updateChart();
    }

    public void reload(String currency, String exchange) {
        this.currency = currency;
        this.exchange = exchange;
        legend = exchange + " " + currency;
        iMAShort = Integer.valueOf(maShort.getText())*12;
        iMALong = Integer.valueOf(maLong.getText())*12;
        update();
    }

    private void update() {
        mainPanel.remove(chartPanel);
        updateChart();
        chartPanel = createChartPanelForStrategy(chart);
        mainPanel.add(chartPanel);
        chart.fireChartChanged();
        chartPanel.repaint();
    }

    private void updateChart() {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        dataset = new TimeSeriesCollection();
        dataset.addSeries(buildChartTimeSeries(series, closePrice, legend));
        chart = createChart(dataset, legend, "Date", "Price", true);
        plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));
        Strategy str = currentStrategy.buildStrategy(series);
        ChartUtil.addBuySellSignals(series, str, plot);
    }

    private void initToolbar() {
        
        JPanel toolbar = new JPanel();
        toolbar.setBorder(BorderFactory.createEtchedBorder());

        JButton bttRSI2 = new JButton();
        bttRSI2.setText("RSI2 Strategy");
        bttRSI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = rsi2;
                tradingRecord = rsi2.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttRSI2);

        JButton bttMm = new JButton();
        bttMm.setText("Moving Momentumg Strategy");
        bttMm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mm;
                iMAShort = Integer.valueOf(maShort.getText())* getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText())* getMAMultiplicator();
                mm.setiMALong(iMALong);
                mm.setiMALong(iMALong);
                tradingRecord = mm.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttMm);

        JButton bttCCI = new JButton();
        bttCCI.setText("CCI Strategy");
        bttCCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = cciCorrectionStrategy;
                tradingRecord = cciCorrectionStrategy.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttCCI);

        JButton bttGE = new JButton();
        bttGE.setText("Global Extrema Strategy");
        bttGE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = globalExtremaStrategy;
                tradingRecord = globalExtremaStrategy.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttGE);

        JButton bttMmUnger = new JButton();
        bttMmUnger.setText("Moving Momentumg Strategy Unger");
        bttMmUnger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mmUnger;
                iMAShort = Integer.valueOf(maShort.getText())* getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText())* getMAMultiplicator();
                mmUnger.setiMALong(iMALong);
                mmUnger.setiMAShort(iMAShort);
                tradingRecord = mmUnger.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttMmUnger);

        JButton bttTestStrategy = new JButton();
        bttTestStrategy.setText("Test Strategie");
        bttTestStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = testStrategy;
                iMAShort = Integer.valueOf(maShort.getText())* getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText())* getMAMultiplicator();
                testStrategy.setiMALong(iMALong);
                testStrategy.setiMAShort(iMAShort);
                tradingRecord = testStrategy.execute(series);
                updateLog(tradingRecord);
                update();
            }
        });
        toolbar.add(bttTestStrategy);

        // input fields
        maShort = new NumericTextField();
        maShort.setText(String.valueOf(iMAShort));
        maShort.setColumns(10);

        maLong = new NumericTextField();
        maLong.setText(String.valueOf(iMALong));
        maLong.setColumns(10);

        JLabel lblShortMA = new JLabel("Short MA");
        JLabel lblLongMA = new JLabel("Long MA");

        toolbar.add(lblShortMA);
        toolbar.add(maShort);
        toolbar.add(lblLongMA);
        toolbar.add(maLong);

        this.add(toolbar, BorderLayout.NORTH);
    }

    private void initMainPanel() {
        //JPanel mainPanel = new JPanel();
        mainPanel = new JSplitPane();
        //mainPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setLayout(new FlowLayout());
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createRaisedBevelBorder());
        //scrollpanel for log
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        //StackPane sp1 = new StackPane(textArea);
        //mainPanel.add(scrollPane);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        chartPanel = createChartPanelForStrategy(chart);
        mainPanel.add(chartPanel);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void initUi() {
        initToolbar();
        initMainPanel();
    }

    private void updateLog(TradingRecord tradingRecord) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of trades for the strategy: " + tradingRecord.getTradeCount() + LB);
        // Analysis
        sb.append("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord) + LB);
        //
        sb.append("" + LB);
        sb.append("Trades:" + LB);
        List<Trade> trades = tradingRecord.getTrades();
        Decimal sum = Decimal.ZERO;
        Decimal averagePercent = Decimal.ZERO;
        for (Trade trade : trades) {
            //sb.append( "trade amount " + trade.getEntry().getAmount() );
            System.out.println("index" + trade.getEntry().getIndex());
            sb.append("Entry " + trade.getEntry().getPrice() + TAB + " : Exit " + trade.getExit().getPrice() + TAB);
            Decimal diff = trade.getExit().getPrice().minus(trade.getEntry().getPrice());
            Decimal diffPercent = trade.getExit().getPrice().dividedBy(trade.getEntry().getPrice());
            diffPercent = diffPercent.minus(Decimal.ONE);
            diffPercent = diffPercent.multipliedBy(Decimal.HUNDRED);
            //Decimal diffPercent = diff.plus(trade.getEntry().getPrice());
            String percent = NumberFormat.getNumberInstance().format(diffPercent);
            sb.append("  Diff amount : " + diff + TAB + " Diff Percent : " + percent + " %  ");
            sb.append("Index-Diff " + (trade.getExit().getIndex() - trade.getEntry().getIndex()) + " ");
            sb.append(LB);
            sum = sum.plus(diff);
            averagePercent = averagePercent.plus(diffPercent);

        }
        sb.append("" + LB);
        sb.append("Total " + sum + LB);
        String percent = NumberFormat.getNumberInstance().format(averagePercent);
        sb.append("Total Percent " + percent + " % " + LB);

        averagePercent = averagePercent.dividedBy(trades.size());
        percent = NumberFormat.getNumberInstance().format(averagePercent);
        sb.append("Average Percent " + percent + " % " + LB);

        textArea.setText(sb.toString());
    }

    public TimeSeries getSeries() {
        return series;
    }

    public void setSeries(TimeSeries series) {
        this.series = series;
    }

}

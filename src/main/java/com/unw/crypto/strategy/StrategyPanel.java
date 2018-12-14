/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.Config;
import com.unw.crypto.chart.AbstractPanel;
import com.unw.crypto.chart.ChartUtil;
import com.unw.crypto.strategy.to.RuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;
import com.unw.crypto.ui.NumericTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private FinalTradingStrategy finalTradingStrategy = new FinalTradingStrategy();
    private TestStrategy testStrategy = new TestStrategy();
    private AbstractStrategy currentStrategy;
    private TradingRecord tradingRecord;
    private Strategy finalStrategy;
    private JTextArea textArea;
    private JSplitPane mainPanel;
    protected JFreeChart chart;
    private NumericTextField maShort;
    private NumericTextField maLong;
    private int iMAShort = 3;
    private int iMALong = 8;
    private XYPlot plot;
    private static final String LB = "\n";
    private static final String TAB = "\t";
    private NumericTextField tfMA8;
    private NumericTextField tfMA14;
    private NumericTextField tfMA200;
    private NumericTextField tfMA314;
    private NumericTextField tfShortSMA;
    private NumericTextField tfLongSMA;
    private NumericTextField tfShortEMA;
    private NumericTextField tfLongEMA;
    private NumericTextField tfRsiTimeframe;
    private NumericTextField tfRsiStoTimeframe;
    private NumericTextField tfStoOscKTimeframe;
    private NumericTextField tfEmaIndicatorTimeframe;
    private NumericTextField tfRsiThresholdLow;
    private NumericTextField tfRsiThresholdHigh;
    private JTextField tfStoThresholdLow;
    private JTextField tfStoThresholdHigh;
    private NumericTextField tfStoOscKThresholdLow;
    private NumericTextField tfStoOscKThresholdHigh;
    private JTextField tfStopLoss;
    private JTextField tfStopGain;
    private NumericTextField tfWaitBars;
    private JCheckBox chkRsiLow;
    private JCheckBox chkStoLow;
    private JCheckBox chkAboveSMA;
    private JCheckBox chkMaPointingUp;
    private JCheckBox chkBelow8MA;

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
        iMAShort = Integer.valueOf(maShort.getText()) * 12;
        iMALong = Integer.valueOf(maLong.getText()) * 12;
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
        //Strategy str = currentStrategy.buildStrategy(series, getBarDuration());
        Strategy str;
        if (currentStrategy instanceof FinalTradingStrategy) {
            str = finalStrategy;
        } else {
            str = currentStrategy.buildStrategy(series, getBarDuration());
        }

        ChartUtil.addBuySellSignals(series, str, plot);
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

    private void initToolbar() {

        JPanel toolbarComplete = new JPanel();
        JPanel toolbarTop = new JPanel();
        //JPanel toolbarFinalStrategy = new JPanel();        
        toolbarComplete.setLayout(new BorderLayout());
        toolbarComplete.setPreferredSize(new Dimension(Config.WIDTH, 120));
        toolbarComplete.setMinimumSize(new Dimension(960, 120));
        toolbarComplete.setBorder(BorderFactory.createEtchedBorder());
        toolbarTop.setBorder(BorderFactory.createEtchedBorder());

        JButton bttRSI2 = new JButton();
        bttRSI2.setText("RSI2 Strategy");
        bttRSI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = rsi2;
                tradingRecord = rsi2.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttRSI2);

        JButton bttMm = new JButton();
        bttMm.setText("Moving Momentumg Strategy");
        bttMm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mm;
                iMAShort = Integer.valueOf(maShort.getText()) * getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText()) * getMAMultiplicator();
                mm.setiMALong(iMALong);
                mm.setiMALong(iMALong);
                tradingRecord = mm.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttMm);

        JButton bttCCI = new JButton();
        bttCCI.setText("CCI Strategy");
        bttCCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = cciCorrectionStrategy;
                tradingRecord = cciCorrectionStrategy.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttCCI);

        JButton bttGE = new JButton();
        bttGE.setText("Global Extrema Strategy");
        bttGE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = globalExtremaStrategy;
                tradingRecord = globalExtremaStrategy.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttGE);

        JButton bttMmUnger = new JButton();
        bttMmUnger.setText("Moving Momentumg Strategy Unger");
        bttMmUnger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mmUnger;
                iMAShort = Integer.valueOf(maShort.getText()) * getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText()) * getMAMultiplicator();
                mmUnger.setiMALong(iMALong);
                mmUnger.setiMAShort(iMAShort);
                tradingRecord = mmUnger.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttMmUnger);

        JButton bttTestStrategy = new JButton();
        bttTestStrategy.setText("Test Strategy");
        bttTestStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = testStrategy;
                iMAShort = Integer.valueOf(maShort.getText()) * getMAMultiplicator();
                iMALong = Integer.valueOf(maLong.getText()) * getMAMultiplicator();
                testStrategy.setiMALong(iMALong);
                testStrategy.setiMAShort(iMAShort);
                tradingRecord = testStrategy.execute(series, getBarDuration());
                updateLog(tradingRecord);
                update();
            }
        });
        toolbarTop.add(bttTestStrategy);

        JButton bttFinalStrategy = new JButton();
        bttFinalStrategy.setText("Final Strategy");
        bttFinalStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                executeFinalStrategy();
            }
        });
        toolbarTop.add(bttFinalStrategy);

        // input fields
        maShort = new NumericTextField();
        maShort.setText(String.valueOf(iMAShort));
        maShort.setColumns(10);

        maLong = new NumericTextField();
        maLong.setText(String.valueOf(iMALong));
        maLong.setColumns(10);

        JLabel lblShortMA = new JLabel("Short MA");
        JLabel lblLongMA = new JLabel("Long MA");

        toolbarTop.add(lblShortMA);
        toolbarTop.add(maShort);
        toolbarTop.add(lblLongMA);
        toolbarTop.add(maLong);

        toolbarComplete.add(toolbarTop, BorderLayout.NORTH);
        JPanel finalStrategyToolbar = createFinalStrategyToolbar();
        toolbarComplete.add(finalStrategyToolbar, BorderLayout.CENTER);
        this.add(toolbarComplete, BorderLayout.NORTH);

    }

    private void executeFinalStrategy() {
        currentStrategy = finalTradingStrategy;
        StrategyInputParams params = createStrategyInputParams();
        finalStrategy = finalTradingStrategy.buildStrategyWithParams(series, params);
        tradingRecord = finalTradingStrategy.executeWithParams(series, params, finalStrategy);
        updateLog(tradingRecord);
        update();
    }

    private StrategyInputParams createStrategyInputParams() {
        StrategyInputParams result;
        int ma8 = Integer.valueOf(tfMA8.getText());
        int ma14 = Integer.valueOf(tfMA14.getText());
        int ma200 = Integer.valueOf(tfMA200.getText());
        int ma314 = Integer.valueOf(tfMA314.getText());
        int smaShort = Integer.valueOf(tfShortSMA.getText());
        int smaLong = Integer.valueOf(tfLongSMA.getText());
        int emaShort = Integer.valueOf(tfShortEMA.getText());
        int emaLong = Integer.valueOf(tfLongEMA.getText());
        int rsiTimeframe = Integer.valueOf(tfRsiTimeframe.getText());
        int rsiStoTimeframe = Integer.valueOf(tfRsiStoTimeframe.getText());
        int stoOscKTimeFrame = Integer.valueOf(tfStoOscKTimeframe.getText());
        int emaIndicatorTimeframe = Integer.valueOf(tfEmaIndicatorTimeframe.getText());
        int rsiThresholdLow = Integer.valueOf(tfRsiThresholdLow.getText());
        int rsiThresholdHigh = Integer.valueOf(tfRsiThresholdHigh.getText());
        double stoThresholdLow = Double.valueOf(tfStoThresholdLow.getText());
        double stoThresholdHigh = Double.valueOf(tfStoThresholdHigh.getText());
        int stoOscKThresholdLow = Integer.valueOf(tfStoOscKThresholdLow.getText());
        int stoOscKThresholdHigh = Integer.valueOf(tfStoOscKThresholdHigh.getText());
        double stopLoss = Double.valueOf(tfStopLoss.getText());
        double stopGain = Double.valueOf(tfStopGain.getText());
        int waitBars = Integer.valueOf(tfWaitBars.getText());
        RuleChain ruleChain = RuleChain.builder().rule1_rsiLow(chkRsiLow.isSelected()).rule2_stoLow(chkStoLow.isSelected()).rule3_priceAboveSMA200(chkAboveSMA.isSelected()).
                rule4_ma8PointingUp(chkMaPointingUp.isSelected()).rule5_priceBelow8MA(chkBelow8MA.isSelected()).rule7_emaBandsPointingUp(false).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration,ma8,ma14,ma200,ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, stopLoss, stopGain, waitBars, ruleChain);
        return result;
    }

    /**
     * params for the final strategy
     *
     * @return
     */
    private JPanel createFinalStrategyToolbar() {

        JPanel result = new JPanel();
        result.setLayout(new FlowLayout());
        result.setBorder(BorderFactory.createEtchedBorder());
        result.setName("Final Strategy Params");
        result.setToolTipText("Final Strategy Params");

        // ma - fix MAs
        JLabel lblMA8 = new JLabel("MA8");
        result.add(lblMA8);

        tfMA8 = new NumericTextField();
        tfMA8.setText(String.valueOf(8));
        tfMA8.setColumns(4);
        result.add(tfMA8);

        JLabel lblMA14 = new JLabel("MA14");
        result.add(lblMA14);

        tfMA14 = new NumericTextField();
        tfMA14.setText(String.valueOf(14));
        tfMA14.setColumns(4);
        result.add(tfMA14);

        JLabel lblMA200 = new JLabel("MA200");
        result.add(lblMA200);

        tfMA200 = new NumericTextField();
        tfMA200.setText(String.valueOf(200));
        tfMA200.setColumns(4);
        result.add(tfMA200);

        JLabel lblMA314 = new JLabel("MA314");
        result.add(lblMA314);

        tfMA314 = new NumericTextField();
        tfMA314.setText(String.valueOf(314));
        tfMA314.setColumns(4);
        result.add(tfMA314);
        //ma
        JLabel lblShortSMA = new JLabel("Short SMA");
        result.add(lblShortSMA);

        tfShortSMA = new NumericTextField();
        tfShortSMA.setText(String.valueOf(3));
        tfShortSMA.setColumns(4);
        result.add(tfShortSMA);

        JLabel lblLongSMA = new JLabel("Long SMA");
        result.add(lblLongSMA);

        tfLongSMA = new NumericTextField();
        tfLongSMA.setText(String.valueOf(10));
        tfLongSMA.setColumns(4);
        result.add(tfLongSMA);

        JLabel lblShortEMA = new JLabel("Short EMA");
        result.add(lblShortEMA);

        tfShortEMA = new NumericTextField();
        tfShortEMA.setText(String.valueOf(3));
        tfShortEMA.setColumns(4);
        result.add(tfShortEMA);

        JLabel lblLongEMA = new JLabel("Long EMA");
        result.add(lblLongEMA);

        tfLongEMA = new NumericTextField();
        tfLongEMA.setText(String.valueOf(10));
        tfLongEMA.setColumns(4);
        result.add(tfLongEMA);

        // RSI 
        JLabel lblRsiTimeFrame = new JLabel("RSI Timeframe");
        result.add(lblRsiTimeFrame);

        tfRsiTimeframe = new NumericTextField();
        tfRsiTimeframe.setText(String.valueOf(3));
        tfRsiTimeframe.setColumns(4);
        result.add(tfRsiTimeframe);

        JLabel lblStoRsiTimeframe = new JLabel("RSI-STO Timeframe");
        result.add(lblStoRsiTimeframe);

        tfRsiStoTimeframe = new NumericTextField();
        tfRsiStoTimeframe.setText(String.valueOf(18));
        tfRsiStoTimeframe.setColumns(4);
        result.add(tfRsiStoTimeframe);

        JLabel lblStoOscKTimeframe = new JLabel("STO-OscK Timeframe");
        result.add(lblStoOscKTimeframe);

        tfStoOscKTimeframe = new NumericTextField();
        tfStoOscKTimeframe.setText(String.valueOf(18));
        tfStoOscKTimeframe.setColumns(4);
        result.add(tfStoOscKTimeframe);

        //EMAIndicator
        JLabel lblEmaIndicatorTimeframe = new JLabel("EMA Indicatior Timeframe");
        result.add(lblEmaIndicatorTimeframe);

        tfEmaIndicatorTimeframe = new NumericTextField();
        tfEmaIndicatorTimeframe.setText(String.valueOf(18));
        tfEmaIndicatorTimeframe.setColumns(4);
        result.add(tfEmaIndicatorTimeframe);
        //RSI threshold
        JLabel lblRsiThresholdLow = new JLabel("RSI Threshold Low");
        result.add(lblRsiThresholdLow);

        tfRsiThresholdLow = new NumericTextField();
        tfRsiThresholdLow.setText(String.valueOf(20));
        tfRsiThresholdLow.setColumns(4);
        result.add(tfRsiThresholdLow);

        JLabel lblRsiThresholdHigh = new JLabel("RSI Threshold HIgh");
        result.add(lblRsiThresholdHigh);

        tfRsiThresholdHigh = new NumericTextField();
        tfRsiThresholdHigh.setText(String.valueOf(80));
        tfRsiThresholdHigh.setColumns(4);
        result.add(tfRsiThresholdHigh);
        // sto
        JLabel lblStoThresholdLow = new JLabel("STO Threshold Low");
        result.add(lblStoThresholdLow);

        tfStoThresholdLow = new JTextField();
        tfStoThresholdLow.setText(String.valueOf(0.18f));
        tfStoThresholdLow.setColumns(4);
        result.add(tfStoThresholdLow);

        JLabel lblStoThresholdHigh = new JLabel("STO Threshold HIgh");
        result.add(lblStoThresholdHigh);

        tfStoThresholdHigh = new JTextField();
        tfStoThresholdHigh.setText(String.valueOf(0.85f));
        tfStoThresholdHigh.setColumns(4);
        result.add(tfStoThresholdHigh);
        //stochasticOscillK
        JLabel lblStoOscKThresholdLow = new JLabel("STO OscK Threshold Low");
        result.add(lblStoOscKThresholdLow);

        tfStoOscKThresholdLow = new NumericTextField();
        tfStoOscKThresholdLow.setText(String.valueOf(20));
        tfStoOscKThresholdLow.setColumns(4);
        result.add(tfStoOscKThresholdLow);

        JLabel lblStoOscKThresholdHigh = new JLabel("STO OscK Threshold HIgh");
        result.add(lblStoOscKThresholdHigh);

        tfStoOscKThresholdHigh = new NumericTextField();
        tfStoOscKThresholdHigh.setText(String.valueOf(80));
        tfStoOscKThresholdHigh.setColumns(4);
        result.add(tfStoOscKThresholdHigh);

        // stopp loss
        JLabel lblstopLoss = new JLabel("Stop Loss");
        result.add(lblstopLoss);

        tfStopLoss = new JTextField();
        tfStopLoss.setText(String.valueOf(1));
        tfStopLoss.setColumns(4);
        result.add(tfStopLoss);
        // stop gain
        JLabel lblstopGain = new JLabel("Stop Gain");
        result.add(lblstopGain);

        tfStopGain = new JTextField();
        tfStopGain.setText(String.valueOf(-1));
        tfStopGain.setColumns(4);
        result.add(tfStopGain);
        // wait rule
        JLabel lblWaitBars = new JLabel("Wait Bars");
        result.add(lblWaitBars);

        tfWaitBars = new NumericTextField();
        tfWaitBars.setText(String.valueOf(50));
        tfWaitBars.setColumns(4);
        result.add(tfWaitBars);

        // rule chain
        JPanel rules = new JPanel();
        rules.setLayout(new FlowLayout());
        rules.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel lblRuleRSI = new JLabel("RSI Low");
        rules.add(lblRuleRSI);
        chkRsiLow = new JCheckBox();
        chkRsiLow.setSelected(true);
        rules.add(chkRsiLow);

        JLabel lblRuleSTO = new JLabel("STO Low");
        rules.add(lblRuleSTO);
        chkStoLow = new JCheckBox();
        chkStoLow.setSelected(true);
        rules.add(chkStoLow);

        JLabel lblAboveSMA = new JLabel("Above SMA200");
        rules.add(lblAboveSMA);
        chkAboveSMA = new JCheckBox();
        chkAboveSMA.setSelected(false);
        rules.add(chkAboveSMA);

        JLabel lblMaUp = new JLabel("8MA pointing up");
        rules.add(lblMaUp);
        chkMaPointingUp = new JCheckBox();
        chkMaPointingUp.setSelected(false);
        rules.add(chkMaPointingUp);

        JLabel lblBelow8MA = new JLabel("Below 8MA");
        rules.add(lblBelow8MA);
        chkBelow8MA = new JCheckBox();
        chkBelow8MA.setSelected(true);
        rules.add(chkBelow8MA);

        result.add(rules);
        return result;
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

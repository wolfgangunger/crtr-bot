/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.chart.BarUtil;
import com.unw.crypto.Config;
import com.unw.crypto.chart.AbstractPanel;
import com.unw.crypto.chart.ChartUtil;
import com.unw.crypto.model.AddOrderInfo;
import com.unw.crypto.model.ExtOrder;
import com.unw.crypto.model.Tick;
import com.unw.crypto.service.MarketAnalyzer;
import com.unw.crypto.strategy.to.AbstractStrategyInputParams;
import com.unw.crypto.strategy.to.EntryRuleChain;
import com.unw.crypto.strategy.to.ExitRuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;
import com.unw.crypto.strategy.to.StrategyInputParamsQuadCCI;
import com.unw.crypto.ui.NumericTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ProgressBar;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Order;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.DoubleNum;

/**
 *
 * @author UNGERW
 */
public class StrategyPanel extends AbstractPanel {

    private MarketAnalyzer marketAnalyzer;

    private ConfigurableApplicationContext context;

    // the series for the selected period
    private TimeSeries series;
    // the ticks for the selected period
    private List<Tick> ticks;
    // the series 2 month before the period ( to be able to create a MA200 for 10 min bars)
    private TimeSeries preSeries;
    // the complete series for the forward testing
    private TimeSeries completeSeries;
    // progress bar of the main window
    private ProgressBar progressBar;
    // required for the chart
    private TimeSeriesCollection dataset = new TimeSeriesCollection();
    // list of orders by forward testing
    private List<ExtOrder> forwardTestOrders = new ArrayList<>();
    // the strategies
    private RSI2Strategy rsi2 = new RSI2Strategy();
    private MovingMomentumStrategy mm = new MovingMomentumStrategy();
    private MovingMomentumStrategyUnw mmUnger = new MovingMomentumStrategyUnw();
    private CCICorrectionStrategy cciCorrectionStrategy = new CCICorrectionStrategy();
    private QuadCCIStrategy quadCCIStrategy = new QuadCCIStrategy();
    private GlobalExtremaStrategy globalExtremaStrategy = new GlobalExtremaStrategy();
    private FinalTradingStrategy finalTradingStrategyLongV1 = new FinalTradingStrategy();
    private FinalTradingStrategyV2 finalTradingStrategyLongV2 = new FinalTradingStrategyV2();
    private FinalTradingStrategyShort finalTradingStrategyShort = new FinalTradingStrategyShort();
    private FinalTradingStrategyShortV2 finalTradingStrategyShortV2 = new FinalTradingStrategyShortV2();
    private ITradingStrategy finalTradingStrategy;
    private TestStrategy testStrategy = new TestStrategy();
    private AbstractStrategy currentStrategy;
    // the trading record for the output
    private TradingRecord tradingRecord;
    // the current selected strategy to execute in tests
    private Strategy tradingStrategy;
    private JTextArea textArea;
    private JSplitPane mainPanel;
    protected JFreeChart chart;
    private NumericTextField maShort;
    private NumericTextField maLong;
    private int iMAShort = 3;
    private int iMALong = 8;
    private XYPlot plot;

    // backward or forward testing
    private JCheckBox chkForwardTesting = new JCheckBox();
    // the final strategy input params
    private JCheckBox chkBarMultiplikator;
    private JCheckBox chkExtraMultiplikator;
    private JTextField tfExtraMultiplikator;
    private NumericTextField tfMA8;
    private NumericTextField tfMA14;
    private NumericTextField tfMA200;
    private NumericTextField tfMA314;
    private NumericTextField tfShortSMA;
    private NumericTextField tfLongSMA;
    private NumericTextField tfShortEMA;
    private NumericTextField tfLongEMA;
    private NumericTextField tfRsiTimeframeBuy;
    private NumericTextField tfRsiStoTimeframeBuy;
    private NumericTextField tfRsiTimeframeSell;
    private NumericTextField tfRsiStoTimeframeSell;
    private NumericTextField tfStoOscKTimeframe;
    private NumericTextField tfEmaIndicatorTimeframe;
    private NumericTextField tfSmaIndicatorTimeframe;
    private NumericTextField tfPriceTimeframeBuy;
    private NumericTextField tfPriceTimeframeSell;
    private NumericTextField tfRsiThresholdLow;
    private NumericTextField tfRsiThresholdHigh;
    private JTextField tfStoThresholdLow;
    private JTextField tfStoThresholdHigh;
    private NumericTextField tfStoOscKThresholdLow;
    private NumericTextField tfStoOscKThresholdHigh;
    private JTextField tfFallingStrenght;
    private JTextField tfRisingStrenght;

    private JTextField tfTrailingStopLoss;
    private JTextField tfStopLoss;
    private JTextField tfStopGain;
    private NumericTextField tfWaitBars;

    // quad cci
    private NumericTextField cci14;
    private NumericTextField cci50;
    private NumericTextField cci100;
    private NumericTextField cci200;
    private JTextField cci14ThresholdBuy;
    private JTextField cci50ThresholdBuy;
    private JTextField cci100ThresholdBuy;
    private JTextField cci200ThresholdBuy;

    private JTextField cci14ThresholdSell;
    private JTextField cci50ThresholdSell;
    
    private JTextField cci50FallingTimeframe;
    private JTextField cciFallingStrenght;

    private JTextField cciStopLoss;
    private JTextField cciTrStopLoss;
    private JCheckBox cciStopLossActive;
    private JCheckBox cciTrStopLossActive;

    //entry rules
    private JCheckBox chkRsiLow;
    private JCheckBox chkStoLow;
    private JCheckBox chkAboveSMA200;
    private JCheckBox chkAboveSMA314;
    private JCheckBox chkMaPointingUp;
    private JCheckBox chkBelow8MA;
    private JCheckBox chkMAsUp;
    private JCheckBox chkRsiUp;
    private JCheckBox chkStoUp;
    private JCheckBox chkMovMom;
    //exit rules
    private JCheckBox chkExitRsiHigh;
    private JCheckBox chkExitStoHigh;
    private JCheckBox chkExit8MaDown;
    private JCheckBox chkExitRsiDown;
    private JCheckBox chkExitStoDown;
    private JCheckBox chkExitPriceDown;
    private JCheckBox chkExitTrailingStopLoss;
    private JCheckBox chkExitStopLoss;
    private JCheckBox chkExitStopGain;

    private static final String LB = "\n";
    private static final String TAB = "\t";

    public StrategyPanel(TimeSeries series, TimeSeries preSeries, ConfigurableApplicationContext context) {
        this.series = series;
        this.preSeries = preSeries;
        this.context = context;
        marketAnalyzer = context.getBean(MarketAnalyzer.class);
        this.setLayout(new BorderLayout());
        initData();
        initUi();
    }

    public StrategyPanel(List<Tick> ticks, TimeSeries series, TimeSeries preSeries, ProgressBar progressBar, ConfigurableApplicationContext context) {
        this.ticks = ticks;
        this.series = series;
        this.preSeries = preSeries;
        this.progressBar = progressBar;
        this.context = context;
        marketAnalyzer = context.getBean(MarketAnalyzer.class);
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
        dataset = new TimeSeriesCollection();
        // must be done here to avoid nullpointer on first call when launching
        chart = createChart(dataset, legend, "Date", "Price", true);
        // is called on start when ui elements are not initialized yet, we don't need to update the chart in this case
        if (chkBarMultiplikator == null) {
            return;
        }
        // in case series are not initialized proper
        if ((completeSeries == null && chkForwardTesting.isSelected())) {
            return;
        }
        // no update the chart with real data
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        dataset.addSeries(buildChartTimeSeries(series, closePrice, legend));
        chart = createChart(dataset, legend, "Date", "Price", true);

        plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-dd HH:mm"));
        AbstractStrategyInputParams params;
        if (currentStrategy instanceof QuadCCIStrategy) {
            params = createStrategyInputParamsQuadCCI();
        } else {
            params = createStrategyInputParams();
        }

        if (chkForwardTesting.isSelected()) {
            ChartUtil.addBuySellSignals(forwardTestOrders, plot);
        } else { // backward
            if (currentStrategy instanceof ITradingStrategy) {
                tradingStrategy = ((ITradingStrategy) currentStrategy).buildStrategyWithParams(series, params);
            } else {
                tradingStrategy = currentStrategy.buildStrategy(series, barDuration);
            }
            ChartUtil.addBuySellSignals(series, tradingStrategy, plot);
        }
    }

    private void initMainPanel() {
        mainPanel = new JSplitPane();
        mainPanel.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createRaisedBevelBorder());
        //scrollpanel for log
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 700));
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
        toolbarComplete.setLayout(new BorderLayout());
//        toolbarComplete.setPreferredSize(new Dimension(Config.WIDTH, 192));
//        toolbarComplete.setMinimumSize(new Dimension(960, 160));
        toolbarComplete.setPreferredSize(new Dimension(Config.WIDTH, 232));
        toolbarComplete.setMinimumSize(new Dimension(960, 160));
        toolbarComplete.setBorder(BorderFactory.createEtchedBorder());
        toolbarTop.setBorder(BorderFactory.createEtchedBorder());

        // input fields
        maShort = new NumericTextField();
        maShort.setText(String.valueOf(iMAShort));
        maShort.setColumns(3);

        maLong = new NumericTextField();
        maLong.setText(String.valueOf(iMALong));
        maLong.setColumns(3);

        JLabel lblShortMA = new JLabel("Short MA");
        JLabel lblLongMA = new JLabel("Long MA");

        toolbarTop.add(lblShortMA);
        toolbarTop.add(maShort);
        toolbarTop.add(lblLongMA);
        toolbarTop.add(maLong);

        // buttons for strategies
        JButton bttRSI2 = new JButton();
        bttRSI2.setText("RSI2 Strategy");
        bttRSI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = rsi2;
                executeTest();
            }
        });
        toolbarTop.add(bttRSI2);

        JButton bttMm = new JButton();
        bttMm.setText("Mov Mom Strategy");
        bttMm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mm;
                executeTest();
            }
        });
        toolbarTop.add(bttMm);

        JButton bttCCI = new JButton();
        bttCCI.setText("CCI Strat");
        bttCCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = cciCorrectionStrategy;
                executeTest();
            }
        });
        toolbarTop.add(bttCCI);

        JButton bttQuadCCI = new JButton();
        bttQuadCCI.setText("Quad CCI Strat");
        bttQuadCCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = quadCCIStrategy;
                executeTest();
            }
        });
        toolbarTop.add(bttQuadCCI);

        JButton bttGE = new JButton();
        bttGE.setText("Global Extrema Strat");
        bttGE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = globalExtremaStrategy;
                executeTest();
            }
        });
        toolbarTop.add(bttGE);

        JButton bttMmUnger = new JButton();
        bttMmUnger.setText("Mov Mom Strat Unw");
        bttMmUnger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = mmUnger;
                executeTest();
            }
        });
        toolbarTop.add(bttMmUnger);

        JButton bttTestStrategy = new JButton();
        bttTestStrategy.setText("Test Strat");
        bttTestStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = testStrategy;
                executeTest();
            }
        });
        toolbarTop.add(bttTestStrategy);

        JButton bttFinalStrategy = new JButton();
        bttFinalStrategy.setText("Final Strat V1");
        bttFinalStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = finalTradingStrategyLongV1;
                finalTradingStrategy = finalTradingStrategyLongV1;
                executeTest();
            }
        });
        toolbarTop.add(bttFinalStrategy);

        JButton bttFinalStrategyV2 = new JButton();
        bttFinalStrategyV2.setText("Final Strat V2");
        bttFinalStrategyV2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = finalTradingStrategyLongV2;
                finalTradingStrategy = finalTradingStrategyLongV2;
                executeTest();
            }
        });
        toolbarTop.add(bttFinalStrategyV2);

        JButton bttFinalStrategyShort = new JButton();
        bttFinalStrategyShort.setText("Final Strat Short");
        bttFinalStrategyShort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = finalTradingStrategyShort;
                finalTradingStrategy = finalTradingStrategyShort;
                executeTest();
            }
        });
        toolbarTop.add(bttFinalStrategyShort);

        JButton bttFinalStrategyShortV2 = new JButton();
        bttFinalStrategyShortV2.setText("Final Strat Short V2");
        bttFinalStrategyShortV2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentStrategy = finalTradingStrategyShortV2;
                finalTradingStrategy = finalTradingStrategyShortV2;
                executeTest();
            }
        });
        toolbarTop.add(bttFinalStrategyShortV2);

        JLabel lblForwardTesting = new JLabel("Forward Test");
        toolbarTop.add(lblForwardTesting);
        // false by default, takes long time you have a long period
        chkForwardTesting.setSelected(false);
        toolbarTop.add(chkForwardTesting);

        toolbarComplete.add(toolbarTop, BorderLayout.NORTH);
        JPanel finalStrategyToolbar = createFinalStrategyToolbar();
        toolbarComplete.add(finalStrategyToolbar, BorderLayout.CENTER);
        this.add(toolbarComplete, BorderLayout.NORTH);

    }

    private void executeTest() {
        iMAShort = Integer.valueOf((int) (Float.valueOf(maShort.getText()) * getMAMultiplicator()));
        iMALong = Integer.valueOf((int) (Float.valueOf(maLong.getText()) * getMAMultiplicator()));
        currentStrategy.setiMALong(iMALong);
        currentStrategy.setiMAShort(iMAShort);
        if (chkForwardTesting.isSelected()) {
            executeForwardTest();
        } else {
            executeBackwardTest();
        }
    }

    private void executeBackwardTest() {
        if (currentStrategy instanceof ITradingStrategy) {
            if (currentStrategy instanceof QuadCCIStrategy) {
                StrategyInputParamsQuadCCI params = createStrategyInputParamsQuadCCI();
                tradingStrategy = quadCCIStrategy.buildStrategyWithParams(series, params);
                tradingRecord = quadCCIStrategy.executeWithParams(series, params, tradingStrategy);
            } else {
                StrategyInputParams params = createStrategyInputParams();
                StrategyUtil.printParams(params);
                tradingStrategy = finalTradingStrategy.buildStrategyWithParams(series, params);
                tradingRecord = finalTradingStrategy.executeWithParams(series, params, tradingStrategy);
            }
        } else {
            tradingStrategy = currentStrategy.buildStrategy(series, barDuration);
            tradingRecord = currentStrategy.execute(series, getBarDuration());
        }
        updateLog(tradingRecord);
        update();
    }

    private void executeForwardTest() {
        AbstractStrategyInputParams params;
        if (currentStrategy instanceof QuadCCIStrategy) {
            params = createStrategyInputParamsQuadCCI();
        } else {
            params = createStrategyInputParams();
            StrategyUtil.printParams((StrategyInputParams) params);
        }

        forwardTestOrders = new ArrayList<>();
        completeSeries = StrategyUtil.copySeries(preSeries);
        //completeSeries = preSeries;
        boolean entered = false;
        double ticksSize = ticks.size();
        double progressBarCounter = 0;
        progressBar.setProgress(0d);
        ZonedDateTime beginTimeCurrentBar = completeSeries.getLastBar().getEndTime();
        // for trailing stop loss
        TradingRecord tr = new BaseTradingRecord();
        for (Tick tick : ticks) {
            if (beginTimeCurrentBar.isAfter(ZonedDateTime.of(LocalDateTime.ofInstant(tick.getTradeTime().toInstant(), ZoneId.systemDefault()), ZoneId.systemDefault()))) {
                System.out.println("overlap " + tick.getTradeTime());
                progressBarCounter++;
                continue;
            }
            if (beginTimeCurrentBar.plusMinutes(barDuration.getIntValue())
                    .isBefore(ZonedDateTime.of(LocalDateTime.ofInstant(tick.getTradeTime().toInstant(), ZoneId.systemDefault()), ZoneId.systemDefault()))) {
                completeSeries.addBar(BarUtil.createNewBar(tick));
                beginTimeCurrentBar = completeSeries.getLastBar().getEndTime();
                progressBar.setProgress(progressBarCounter / ticksSize);
                //System.out.println((progressBarCounter / ticksSize) * 100 + " %");
            } else {
                completeSeries.getLastBar().addTrade(DoubleNum.valueOf(tick.getAmount()),
                        DoubleNum.valueOf(tick.getPrice()));
            }
            // now lets check if we execute the strategy ( once in a minute)
            int time = StrategyUtil.getTimeInHoursAndMinutes(tick);
            int prevTime = progressBarCounter > 1 ? StrategyUtil.getTimeInHoursAndMinutes(ticks.get((int) progressBarCounter - 1)) : StrategyUtil.getTimeInHoursAndMinutes(tick);
            boolean lastTick = tick.getId().equals(ticks.get(ticks.size() - 1).getId());
            if (time == prevTime) {
                // we execute the strategy only once in a minute, if there are more ticks, we do nothing
            } else {

                // execute the strategy at least every minute
                if (currentStrategy instanceof ITradingStrategy) {
                    if (currentStrategy instanceof QuadCCIStrategy) {
                        tradingStrategy = quadCCIStrategy.buildStrategyWithParams(completeSeries, params);
                    } else {
                        tradingStrategy = finalTradingStrategy.buildStrategyWithParams(completeSeries, params);
                    }
                } else {
                    tradingStrategy = currentStrategy.buildStrategy(completeSeries, barDuration);
                }
                // needed for stop loss

                if (tradingStrategy.shouldEnter(completeSeries.getEndIndex(), tr) && !entered) {
                    ExtOrder order = new ExtOrder(Order.buyAt(completeSeries.getEndIndex(), completeSeries));
                    order.setTradeTime(tick.getTradeTime());
                    tr.enter(order.getIndex(), order.getPrice(), order.getAmount());
                    AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params);
                    order.setAddOrderInfo(info);
                    forwardTestOrders.add(order);
                    entered = true;
                } else if (tradingStrategy.shouldExit(completeSeries.getEndIndex(), tr) && entered) {
                    ExtOrder order = new ExtOrder(Order.sellAt(completeSeries.getEndIndex(), completeSeries));
                    order.setTradeTime(tick.getTradeTime());
                    AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params);
                    order.setAddOrderInfo(info);
                    forwardTestOrders.add(order);
                    tr.exit(order.getIndex());
                    entered = false;
                }

            }
            if (lastTick && entered) {
                // if there is still a trade open in last tick, sell anyway
                System.out.println("Trade open on last tick- sell anyway");
                ExtOrder order = new ExtOrder(Order.sellAt(completeSeries.getEndIndex(), completeSeries));
                order.setTradeTime(tick.getTradeTime());
                AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params);
                order.setAddOrderInfo(info);
                forwardTestOrders.add(order);
                tr.exit(order.getIndex());
                entered = false;
            }

            progressBarCounter++;
        }
        progressBar.setProgress(100d);
        TradingRecord record = StrategyUtil.buildTradingRecord(forwardTestOrders);
        updateLog(record);
        printAddOrderInfo(forwardTestOrders);
        update();
    }

    private StrategyInputParams createStrategyInputParams() {
        StrategyInputParams result;
        boolean barMultiplikator = chkBarMultiplikator.isSelected();
        boolean extraMultiplikator = chkExtraMultiplikator.isSelected();
        float extraMultiplikatorValue = Float.valueOf(tfExtraMultiplikator.getText());
        int ma8 = Integer.valueOf(tfMA8.getText());
        int ma14 = Integer.valueOf(tfMA14.getText());
        int ma200 = Integer.valueOf(tfMA200.getText());
        int ma314 = Integer.valueOf(tfMA314.getText());
        int smaShort = Integer.valueOf(tfShortSMA.getText());
        int smaLong = Integer.valueOf(tfLongSMA.getText());
        int emaShort = Integer.valueOf(tfShortEMA.getText());
        int emaLong = Integer.valueOf(tfLongEMA.getText());
        int rsiTimeframeBuy = Integer.valueOf(tfRsiTimeframeBuy.getText());
        int rsiStoTimeframeBuy = Integer.valueOf(tfRsiStoTimeframeBuy.getText());
        int rsiTimeframeSell = Integer.valueOf(tfRsiTimeframeSell.getText());
        int rsiStoTimeframeSell = Integer.valueOf(tfRsiStoTimeframeSell.getText());
        int stoOscKTimeFrame = Integer.valueOf(tfStoOscKTimeframe.getText());
        int emaIndicatorTimeframe = Integer.valueOf(tfEmaIndicatorTimeframe.getText());
        int smaIndicatorTimeframe = Integer.valueOf(tfSmaIndicatorTimeframe.getText());
        int priceTimeFrameBuy = Integer.valueOf(tfPriceTimeframeBuy.getText());
        int priceTimeFrameSell = Integer.valueOf(tfPriceTimeframeSell.getText());
        int rsiThresholdLow = Integer.valueOf(tfRsiThresholdLow.getText());
        int rsiThresholdHigh = Integer.valueOf(tfRsiThresholdHigh.getText());
        double stoThresholdLow = Double.valueOf(tfStoThresholdLow.getText());
        double stoThresholdHigh = Double.valueOf(tfStoThresholdHigh.getText());
        int stoOscKThresholdLow = Integer.valueOf(tfStoOscKThresholdLow.getText());
        int stoOscKThresholdHigh = Integer.valueOf(tfStoOscKThresholdHigh.getText());
        double isRisingStrenght = Double.valueOf(tfRisingStrenght.getText());
        double isFallingStrenght = Double.valueOf(tfFallingStrenght.getText());
        double stopLoss = Double.valueOf(tfStopLoss.getText());
        double trailingStopLoss = Double.valueOf(tfTrailingStopLoss.getText());
        double stopGain = Double.valueOf(tfStopGain.getText());
        int waitBars = Integer.valueOf(tfWaitBars.getText());
        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(chkRsiLow.isSelected()).rule2_stoLow(chkStoLow.isSelected()).
                rule3_priceAboveSMA200(chkAboveSMA200.isSelected()).rule3b_priceAboveSMA314(chkAboveSMA314.isSelected()).
                rule4_ma8PointingUp(chkMaPointingUp.isSelected()).rule5_priceBelow8MA(chkBelow8MA.isSelected()).rule7_emaBandsPointingUp(chkMAsUp.isSelected())
                .rule11_isRsiPointingUp(chkRsiUp.isSelected()).rule12_isStoPointingUp(chkStoUp.isSelected()).rule13_movingMomentum(chkMovMom.isSelected()).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(chkExitRsiHigh.isSelected()).rule2_stoHigh(chkExitStoHigh.isSelected())
                .rule3_8maDown(chkExit8MaDown.isSelected()).rule11_rsiPointingDown(chkExitRsiDown.isSelected())
                .rule12_StoPointingDown(chkExitStoDown.isSelected()).rule21_priceFalling(chkExitPriceDown.isSelected())
                .rule23_stopGain(chkExitStopGain.isSelected()).rule22_stopLoss(chkExitStopLoss.isSelected()).rule22b_trailingStopLoss(chkExitTrailingStopLoss.isSelected()).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong,
                rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, isRisingStrenght, isFallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI() {
        int icci14 = Integer.valueOf(this.cci14.getText());
        int icci14TBuy = Integer.valueOf(this.cci14ThresholdBuy.getText());
        int icci14TSell = Integer.valueOf(this.cci14ThresholdSell.getText());
        int icci50 = Integer.valueOf(this.cci50.getText());
        int icci50TBuy = Integer.valueOf(this.cci50ThresholdBuy.getText());
        int icci50TSell = Integer.valueOf(this.cci50ThresholdSell.getText());
        int icci100 = Integer.valueOf(this.cci100.getText());
        int icci100T = Integer.valueOf(this.cci100ThresholdBuy.getText());
        int icci200 = Integer.valueOf(this.cci200.getText());
        int icci200T = Integer.valueOf(this.cci200ThresholdBuy.getText());
        double fallingStrenght = Double.valueOf(this.cciFallingStrenght.getText());
        int cci50FallingTimeframe = Integer.valueOf(this.cci50FallingTimeframe.getText());
        int iccStopLoss = Integer.valueOf(this.cciStopLoss.getText());
        int iccTrStopLoss = Integer.valueOf(this.cciTrStopLoss.getText());
        
        return StrategyInputParamsQuadCCI.builder().cci14(icci14).cci14ThresholdBuy(icci14TBuy).cci50(icci50).cci50ThresholdBuy(icci50TBuy).cci100(icci100).cci100ThresholdBuy(icci100T)
                .cci200(icci200).cci200ThresholdBuy(icci200T).cci14ThresholdSell(icci14TSell).cci50ThresholdSell(icci50TSell)
                .cci50FallingTimeframe(cci50FallingTimeframe).fallingStrenght(fallingStrenght).stopLoss(iccStopLoss).trStopLoss(iccTrStopLoss).stopLossActive(cciStopLossActive.isSelected())
                .trStopLossActive(cciTrStopLossActive.isSelected()).build();
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

        // timeFrame Multiplikator
        JPanel timeframes = new JPanel();
        timeframes.setLayout(new FlowLayout());
        timeframes.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel lblBarMultiplikator = new JLabel("Bar Multiplikator");
        timeframes.add(lblBarMultiplikator);
        chkBarMultiplikator = new JCheckBox();
        chkBarMultiplikator.setSelected(false);
        timeframes.add(chkBarMultiplikator);

        JLabel lblExtraMultiplikator = new JLabel("Extra Multiplikator");
        timeframes.add(lblExtraMultiplikator);
        chkExtraMultiplikator = new JCheckBox();
        chkExtraMultiplikator.setSelected(false);
        timeframes.add(chkExtraMultiplikator);

        tfExtraMultiplikator = new JTextField();
        tfExtraMultiplikator.setText(String.valueOf(1.0f));
        tfExtraMultiplikator.setColumns(2);
        timeframes.add(tfExtraMultiplikator);

        result.add(timeframes);

        // ma - fix MAs - removed by now - wont be changed
        JLabel lblMA8 = new JLabel("MA8");
        result.add(lblMA8);

        tfMA8 = new NumericTextField();
        tfMA8.setText(String.valueOf(8));
        tfMA8.setColumns(2);
        result.add(tfMA8);

        JLabel lblMA14 = new JLabel("MA14");
        result.add(lblMA14);

        tfMA14 = new NumericTextField();
        tfMA14.setText(String.valueOf(14));
        tfMA14.setColumns(3);
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
        tfShortSMA.setColumns(2);
        result.add(tfShortSMA);

        JLabel lblLongSMA = new JLabel("Long SMA");
        result.add(lblLongSMA);

        tfLongSMA = new NumericTextField();
        tfLongSMA.setText(String.valueOf(10));
        tfLongSMA.setColumns(3);
        result.add(tfLongSMA);

        JLabel lblShortEMA = new JLabel("Short EMA");
        result.add(lblShortEMA);

        tfShortEMA = new NumericTextField();
        tfShortEMA.setText(String.valueOf(5));
        tfShortEMA.setColumns(2);
        result.add(tfShortEMA);

        JLabel lblLongEMA = new JLabel("Long EMA");
        result.add(lblLongEMA);

        tfLongEMA = new NumericTextField();
        tfLongEMA.setText(String.valueOf(12));
        tfLongEMA.setColumns(3);
        result.add(tfLongEMA);

        // RSI 
        JLabel lblRsiTimeFrameBuy = new JLabel("RSI Timeframe Buy");
        result.add(lblRsiTimeFrameBuy);
        tfRsiTimeframeBuy = new NumericTextField();
        tfRsiTimeframeBuy.setText(String.valueOf(2));
        tfRsiTimeframeBuy.setColumns(2);
        result.add(tfRsiTimeframeBuy);

        JLabel lblRsiTimeFrameSell = new JLabel("RSI Timeframe Sell");
        result.add(lblRsiTimeFrameSell);
        tfRsiTimeframeSell = new NumericTextField();
        tfRsiTimeframeSell.setText(String.valueOf(2));
        tfRsiTimeframeSell.setColumns(2);
        result.add(tfRsiTimeframeSell);

        JLabel lblStoRsiTimeframeBuy = new JLabel("RSI-STO Timeframe Buy");
        result.add(lblStoRsiTimeframeBuy);
        tfRsiStoTimeframeBuy = new NumericTextField();
        tfRsiStoTimeframeBuy.setText(String.valueOf(4));
        tfRsiStoTimeframeBuy.setColumns(2);
        result.add(tfRsiStoTimeframeBuy);

        JLabel lblStoRsiTimeframeSell = new JLabel("RSI-STO Timeframe Sell");
        result.add(lblStoRsiTimeframeSell);
        tfRsiStoTimeframeSell = new NumericTextField();
        tfRsiStoTimeframeSell.setText(String.valueOf(4));
        tfRsiStoTimeframeSell.setColumns(2);
        result.add(tfRsiStoTimeframeSell);

        JLabel lblStoOscKTimeframe = new JLabel("STO-OscK Timeframe");
        result.add(lblStoOscKTimeframe);

        tfStoOscKTimeframe = new NumericTextField();
        tfStoOscKTimeframe.setText(String.valueOf(4));
        tfStoOscKTimeframe.setColumns(2);
        result.add(tfStoOscKTimeframe);

        //SMAIndicator
        JLabel lblSmaIndicatorTimeframe = new JLabel("SMA Indicatior Timeframe");
        result.add(lblSmaIndicatorTimeframe);

        tfSmaIndicatorTimeframe = new NumericTextField();
        tfSmaIndicatorTimeframe.setText(String.valueOf(4));
        tfSmaIndicatorTimeframe.setColumns(2);
        result.add(tfSmaIndicatorTimeframe);

        //EMAIndicator
        JLabel lblEmaIndicatorTimeframe = new JLabel("EMA Indicatior Timeframe");
        result.add(lblEmaIndicatorTimeframe);
        tfEmaIndicatorTimeframe = new NumericTextField();
        tfEmaIndicatorTimeframe.setText(String.valueOf(4));
        tfEmaIndicatorTimeframe.setColumns(2);
        result.add(tfEmaIndicatorTimeframe);

        //Closed Price
        JLabel lblPriceTimeframeBuy = new JLabel("Price Timeframe Buy");
        result.add(lblPriceTimeframeBuy);
        tfPriceTimeframeBuy = new NumericTextField();
        tfPriceTimeframeBuy.setText(String.valueOf(1));
        tfPriceTimeframeBuy.setColumns(2);
        result.add(tfPriceTimeframeBuy);

        JLabel lblPriceTimeframeSell = new JLabel("Price Timeframe Sell");
        result.add(lblPriceTimeframeSell);
        tfPriceTimeframeSell = new NumericTextField();
        tfPriceTimeframeSell.setText(String.valueOf(1));
        tfPriceTimeframeSell.setColumns(2);
        result.add(tfPriceTimeframeSell);

        //RSI threshold
        JLabel lblRsiThresholdLow = new JLabel("RSI Threshold Low");
        result.add(lblRsiThresholdLow);

        tfRsiThresholdLow = new NumericTextField();
        tfRsiThresholdLow.setText(String.valueOf(30));
        tfRsiThresholdLow.setColumns(3);
        result.add(tfRsiThresholdLow);

        JLabel lblRsiThresholdHigh = new JLabel("RSI Threshold High");
        result.add(lblRsiThresholdHigh);

        tfRsiThresholdHigh = new NumericTextField();
        tfRsiThresholdHigh.setText(String.valueOf(70));
        tfRsiThresholdHigh.setColumns(3);
        result.add(tfRsiThresholdHigh);
        // sto
        JLabel lblStoThresholdLow = new JLabel("STO Threshold Low");
        result.add(lblStoThresholdLow);

        tfStoThresholdLow = new JTextField();
        tfStoThresholdLow.setText(String.valueOf(0.3f));
        tfStoThresholdLow.setColumns(4);
        result.add(tfStoThresholdLow);

        JLabel lblStoThresholdHigh = new JLabel("STO Threshold HIgh");
        result.add(lblStoThresholdHigh);

        tfStoThresholdHigh = new JTextField();
        tfStoThresholdHigh.setText(String.valueOf(0.7f));
        tfStoThresholdHigh.setColumns(4);
        result.add(tfStoThresholdHigh);

        //stochasticOscillK
        JLabel lblStoOscKThresholdLow = new JLabel("STO OscK Threshold Low");
        result.add(lblStoOscKThresholdLow);

        tfStoOscKThresholdLow = new NumericTextField();
        tfStoOscKThresholdLow.setText(String.valueOf(30));
        tfStoOscKThresholdLow.setColumns(3);
        result.add(tfStoOscKThresholdLow);

        JLabel lblStoOscKThresholdHigh = new JLabel("STO OscK Threshold HIgh");
        result.add(lblStoOscKThresholdHigh);

        tfStoOscKThresholdHigh = new NumericTextField();
        tfStoOscKThresholdHigh.setText(String.valueOf(70));
        tfStoOscKThresholdHigh.setColumns(3);
        result.add(tfStoOscKThresholdHigh);

        // is rising and is falling strenthg
        JLabel lblRiseStrength = new JLabel("Rising Strength");
        result.add(lblRiseStrength);

        tfRisingStrenght = new JTextField();
        tfRisingStrenght.setText(String.valueOf(0.5d));
        tfRisingStrenght.setColumns(4);
        result.add(tfRisingStrenght);

        JLabel lblFallingStrength = new JLabel("Falling Strength");
        result.add(lblFallingStrength);

        tfFallingStrenght = new JTextField();
        tfFallingStrenght.setText(String.valueOf(0.5d));
        tfFallingStrenght.setColumns(4);
        result.add(tfFallingStrenght);

        // stopp loss
        JLabel lblstopLoss = new JLabel("Stop Loss");
        result.add(lblstopLoss);
        tfStopLoss = new JTextField();
        tfStopLoss.setText(String.valueOf(2));
        tfStopLoss.setColumns(2);
        result.add(tfStopLoss);

        JLabel lblsTralingtopLoss = new JLabel("Tr. Stop Loss");
        result.add(lblsTralingtopLoss);
        tfTrailingStopLoss = new JTextField();
        tfTrailingStopLoss.setText(String.valueOf(5));
        tfTrailingStopLoss.setColumns(2);
        result.add(tfTrailingStopLoss);

        // stop gain
        JLabel lblstopGain = new JLabel("Stop Gain");
        result.add(lblstopGain);

        tfStopGain = new JTextField();
        tfStopGain.setText(String.valueOf(5));
        tfStopGain.setColumns(2);
        result.add(tfStopGain);
        // wait rule
        JLabel lblWaitBars = new JLabel("Wait Bars");
        result.add(lblWaitBars);

        tfWaitBars = new NumericTextField();
        tfWaitBars.setText(String.valueOf(35));
        tfWaitBars.setColumns(2);
        result.add(tfWaitBars);

        // entry rule chain
        JPanel entryRules = new JPanel();
        entryRules.setLayout(new FlowLayout());
        entryRules.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel lblRuleRSI = new JLabel("1 RSI Low");
        entryRules.add(lblRuleRSI);
        chkRsiLow = new JCheckBox();
        chkRsiLow.setSelected(true);
        entryRules.add(chkRsiLow);

        JLabel lblRuleSTO = new JLabel("2 STO Low");
        entryRules.add(lblRuleSTO);
        chkStoLow = new JCheckBox();
        chkStoLow.setSelected(true);
        entryRules.add(chkStoLow);

        JLabel lblAboveSMA = new JLabel("3 Above SMA200");
        entryRules.add(lblAboveSMA);
        chkAboveSMA200 = new JCheckBox();
        chkAboveSMA200.setSelected(true);
        entryRules.add(chkAboveSMA200);

        JLabel lblAboveSMA314 = new JLabel("3b Above SMA314");
        entryRules.add(lblAboveSMA314);
        chkAboveSMA314 = new JCheckBox();
        chkAboveSMA314.setSelected(false);
        entryRules.add(chkAboveSMA314);

        JLabel lblMaUp = new JLabel("4 8MA pointing up");
        entryRules.add(lblMaUp);
        chkMaPointingUp = new JCheckBox();
        chkMaPointingUp.setSelected(true);
        entryRules.add(chkMaPointingUp);

        JLabel lblBelow8MA = new JLabel("5 Below 8MA");
        entryRules.add(lblBelow8MA);
        chkBelow8MA = new JCheckBox();
        chkBelow8MA.setSelected(false);
        entryRules.add(chkBelow8MA);

        JLabel lblMAsUp = new JLabel("7 EMA-Bands ups");
        entryRules.add(lblMAsUp);
        chkMAsUp = new JCheckBox();
        chkMAsUp.setSelected(true);
        entryRules.add(chkMAsUp);

        JLabel lblRsiUp = new JLabel("11 RSI ->up");
        entryRules.add(lblRsiUp);
        chkRsiUp = new JCheckBox();
        chkRsiUp.setSelected(true);
        entryRules.add(chkRsiUp);

        JLabel lblStoUp = new JLabel("12 Sto ->up");
        entryRules.add(lblStoUp);
        chkStoUp = new JCheckBox();
        chkStoUp.setSelected(true);
        entryRules.add(chkStoUp);

        JLabel lblMovingMom = new JLabel("13 MovMom");
        entryRules.add(lblMovingMom);
        chkMovMom = new JCheckBox();
        chkMovMom.setSelected(false);
        entryRules.add(chkMovMom);

        result.add(entryRules);

        // exit rule chain
        JPanel exitRules = new JPanel();
        exitRules.setLayout(new FlowLayout());
        exitRules.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel lblRuleRSIDown = new JLabel("1 RSI High");
        exitRules.add(lblRuleRSIDown);
        chkExitRsiHigh = new JCheckBox();
        chkExitRsiHigh.setSelected(true);
        exitRules.add(chkExitRsiHigh);

        JLabel lblRuleStoDown = new JLabel("2 Sto High");
        exitRules.add(lblRuleStoDown);
        chkExitStoHigh = new JCheckBox();
        chkExitStoHigh.setSelected(true);
        exitRules.add(chkExitStoHigh);

        JLabel lblRule8MADown = new JLabel("3 8MA ->Down");
        exitRules.add(lblRule8MADown);
        chkExit8MaDown = new JCheckBox();
        chkExit8MaDown.setSelected(true);
        exitRules.add(chkExit8MaDown);

        JLabel lblRuleRSIPontingDown = new JLabel("11 RSI ->Down");
        exitRules.add(lblRuleRSIPontingDown);
        chkExitRsiDown = new JCheckBox();
        chkExitRsiDown.setSelected(true);
        exitRules.add(chkExitRsiDown);

        JLabel lblRuleStoPontingDown = new JLabel("11 Sto ->Down");
        exitRules.add(lblRuleStoPontingDown);
        chkExitStoDown = new JCheckBox();
        chkExitStoDown.setSelected(true);
        exitRules.add(chkExitStoDown);

        JLabel lblRulePriceDown = new JLabel("21 Price ->Down");
        exitRules.add(lblRulePriceDown);
        chkExitPriceDown = new JCheckBox();
        chkExitPriceDown.setSelected(false);
        exitRules.add(chkExitPriceDown);

        JLabel lblRuleStopLoss = new JLabel("22 Stop Loss");
        exitRules.add(lblRuleStopLoss);
        chkExitStopLoss = new JCheckBox();
        chkExitStopLoss.setSelected(false);
        exitRules.add(chkExitStopLoss);

        JLabel lblRuleTrailingStopLoss = new JLabel("22b Tr. Stop Loss");
        exitRules.add(lblRuleTrailingStopLoss);
        chkExitTrailingStopLoss = new JCheckBox();
        chkExitTrailingStopLoss.setSelected(true);
        exitRules.add(chkExitTrailingStopLoss);

        JLabel lblRuleStopGain = new JLabel("23 Stop Gain");
        exitRules.add(lblRuleStopGain);
        chkExitStopGain = new JCheckBox();
        chkExitStopGain.setSelected(false);
        exitRules.add(chkExitStopGain);

        result.add(exitRules);

        /// quad cci 
        // exit rule chain
        JPanel quadCCI = new JPanel();
        quadCCI.setLayout(new FlowLayout());
        quadCCI.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblCCI14 = new JLabel("CCI 14");
        quadCCI.add(lblCCI14);
        cci14 = new NumericTextField();
        cci14.setText(String.valueOf(14));
        cci14.setColumns(2);
        quadCCI.add(cci14);

        JLabel lblCCI50 = new JLabel("CCI 50");
        quadCCI.add(lblCCI50);
        cci50 = new NumericTextField();
        cci50.setText(String.valueOf(50));
        cci50.setColumns(2);
        quadCCI.add(cci50);

        JLabel lblCCI100 = new JLabel("CCI 100");
        quadCCI.add(lblCCI100);
        cci100 = new NumericTextField();
        cci100.setText(String.valueOf(100));
        cci100.setColumns(3);
        quadCCI.add(cci100);

        JLabel lblCCI200 = new JLabel("CCI 200");
        quadCCI.add(lblCCI200);
        cci200 = new NumericTextField();
        cci200.setText(String.valueOf(200));
        cci200.setColumns(3);
        quadCCI.add(cci200);

        JLabel lblCCI14ThreshBuy = new JLabel("CCI 14 Thres. B");
        quadCCI.add(lblCCI14ThreshBuy);
        cci14ThresholdBuy = new JTextField();
        cci14ThresholdBuy.setText(String.valueOf(-100));
        cci14ThresholdBuy.setColumns(4);
        quadCCI.add(cci14ThresholdBuy);

        JLabel lblCCI14ThreshSell = new JLabel("CCI 14 Thres. S");
        quadCCI.add(lblCCI14ThreshSell);
        cci14ThresholdSell = new JTextField();
        cci14ThresholdSell.setText(String.valueOf(100));
        cci14ThresholdSell.setColumns(4);
        quadCCI.add(cci14ThresholdSell);

        JLabel lblCCI50ThreshBuy = new JLabel("CCI 50 Thres. B");
        quadCCI.add(lblCCI50ThreshBuy);
        cci50ThresholdBuy = new JTextField();
        cci50ThresholdBuy.setText(String.valueOf(-20));
        cci50ThresholdBuy.setColumns(4);
        quadCCI.add(cci50ThresholdBuy);

        JLabel lblCCI50ThreshSell = new JLabel("CCI 50 Thres. S");
        quadCCI.add(lblCCI50ThreshSell);
        cci50ThresholdSell = new JTextField();
        cci50ThresholdSell.setText(String.valueOf(100));
        cci50ThresholdSell.setColumns(4);
        quadCCI.add(cci50ThresholdSell);

        JLabel lblCCI100Thresh = new JLabel("CCI 100 Thres.");
        quadCCI.add(lblCCI100Thresh);
        cci100ThresholdBuy = new JTextField();
        cci100ThresholdBuy.setText(String.valueOf(0));
        cci100ThresholdBuy.setColumns(4);
        quadCCI.add(cci100ThresholdBuy);

        JLabel lblCCI200Thresh = new JLabel("CCI 200 Thres.");
        quadCCI.add(lblCCI200Thresh);
        cci200ThresholdBuy = new JTextField();
        cci200ThresholdBuy.setText(String.valueOf(0));
        cci200ThresholdBuy.setColumns(4);
        quadCCI.add(cci200ThresholdBuy);
        
        JLabel lblcci50FallingTimeframe = new JLabel("TF Falling");
        quadCCI.add(lblcci50FallingTimeframe);
        cci50FallingTimeframe = new JTextField();
        cci50FallingTimeframe.setText(String.valueOf(1));
        cci50FallingTimeframe.setColumns(1);
        quadCCI.add(cci50FallingTimeframe);
        
        JLabel lblfallingStrenght = new JLabel("Falling Str");
        quadCCI.add(lblfallingStrenght);
        cciFallingStrenght = new JTextField();
        cciFallingStrenght.setText(String.valueOf(0.5d));
        cciFallingStrenght.setColumns(4);
        quadCCI.add(cciFallingStrenght);
        

        JLabel lblCCIStopLoss = new JLabel("Stop Loss");
        quadCCI.add(lblCCIStopLoss);
        cciStopLoss = new JTextField();
        cciStopLoss.setText(String.valueOf(5));
        cciStopLoss.setColumns(2);
        quadCCI.add(cciStopLoss);

        JLabel lblCCITrStopLoss = new JLabel("tr Stop Loss");
        quadCCI.add(lblCCITrStopLoss);
        cciTrStopLoss = new JTextField();
        cciTrStopLoss.setText(String.valueOf(5));
        cciTrStopLoss.setColumns(2);
        quadCCI.add(cciTrStopLoss);

        JLabel lblCCIStopLossActive = new JLabel("Stop Loss");
        quadCCI.add(lblCCIStopLossActive);
        cciStopLossActive = new JCheckBox();
        cciStopLossActive.setSelected(false);
        quadCCI.add(cciStopLossActive);

        JLabel lblCCITrStopLossActive = new JLabel("Tr Stop Loss");
        quadCCI.add(lblCCITrStopLossActive);
        cciTrStopLossActive = new JCheckBox();
        cciTrStopLossActive.setSelected(true);
        quadCCI.add(cciTrStopLossActive);

        result.add(quadCCI);

        return result;
    }

    private void updateLog(TradingRecord tradingRecord) {
        String log = LogUtil.createLogOutput(tradingRecord, series);
        textArea.setText(log);
    }

    private void printAddOrderInfo(List<ExtOrder> orders) {
        LogUtil.printAddOrderInfo(orders);
    }

    public TimeSeries getSeries() {
        return series;
    }

    public void setSeries(TimeSeries series) {
        this.series = series;
    }

    public TimeSeries getPreSeries() {
        return preSeries;
    }

    public void setPreSeries(TimeSeries preSeries) {
        this.preSeries = preSeries;
    }

    public List<Tick> getTicks() {
        return ticks;
    }

    public void setTicks(List<Tick> ticks) {
        this.ticks = ticks;
    }

}

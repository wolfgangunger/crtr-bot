/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;

/**
 *
 * @author UNGERW
 */
public class StrategyPanel extends JPanel {

    private final TimeSeries series;
    private final RSI2Strategy rsi2 = new RSI2Strategy();
    private final MovingMomentumStrategy mm = new MovingMomentumStrategy();
    private final MovingMomentumStrategyUnw mmUnger = new MovingMomentumStrategyUnw();
    private final CCICorrectionStrategy cciCorrectionStrategy = new CCICorrectionStrategy();
    private final GlobalExtremaStrategy globalExtremaStrategy = new GlobalExtremaStrategy();
    private final TestStrategy testStrategy = new TestStrategy();
    private TradingRecord tradingRecord;
    private JTextArea textArea;
    private static final String LB = "\n";
    private static final String TAB = "\t";

    public StrategyPanel(TimeSeries series) {
        this.series = series;
        this.setLayout(new BorderLayout());
        initUi();
    }

    private void initToolbar() {
        JPanel toolbar = new JPanel();
        toolbar.setBorder(BorderFactory.createEtchedBorder());

        JButton bttRSI2 = new JButton();
        bttRSI2.setText("RSI2 Strategy");
        bttRSI2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = rsi2.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttRSI2);

        JButton bttMm = new JButton();
        bttMm.setText("Moving Momentumg Strategy");
        bttMm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = mm.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttMm);

        JButton bttCCI = new JButton();
        bttCCI.setText("CCI Strategy");
        bttCCI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = cciCorrectionStrategy.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttCCI);
        
        JButton bttGE = new JButton();
        bttGE.setText("Global Extrema Strategy");
        bttGE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = globalExtremaStrategy.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttGE);
        
        JButton bttMmUnger = new JButton();
        bttMmUnger.setText("Moving Momentumg Strategy Unger");
        bttMmUnger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = mmUnger.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttMmUnger);
        
        JButton bttTestStrategy = new JButton();
        bttTestStrategy.setText("Test Strategie");
        bttTestStrategy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                tradingRecord = testStrategy.execute(series);
                updateLog(tradingRecord);
            }
        });
        toolbar.add(bttTestStrategy);
        
        this.add(toolbar, BorderLayout.NORTH);
    }

    private void initMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEtchedBorder());
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createRaisedBevelBorder());
        //scrollpanel for log
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 700));
        mainPanel.add(scrollPane);
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
        Decimal sum = Decimal.ZERO ;
        Decimal averagePercent = Decimal.ZERO ;
        for (Trade trade : trades) {
            //sb.append( "trade amount " + trade.getEntry().getAmount() );
            sb.append("Entry " + trade.getEntry().getPrice() + TAB +  " : Exit " + trade.getExit().getPrice()+ TAB);
            Decimal diff = trade.getExit().getPrice().minus(trade.getEntry().getPrice());
            Decimal diffPercent = trade.getExit().getPrice().dividedBy(trade.getEntry().getPrice());
            diffPercent = diffPercent.minus(Decimal.ONE);
            diffPercent = diffPercent.multipliedBy(Decimal.HUNDRED);
            //Decimal diffPercent = diff.plus(trade.getEntry().getPrice());
            String percent = NumberFormat.getNumberInstance().format(diffPercent);
            sb.append("  Diff amount : " + diff + TAB + " Diff Percent : " + percent +" %");
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

}

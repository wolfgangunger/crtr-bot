/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.ExtOrder;
import java.text.NumberFormat;
import java.util.List;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

/**
 *
 * @author UNGERW
 */
public final class LogUtil {

    private static final String LB = "\n";
    private static final String TAB = "\t";

    private LogUtil() {

    }

    public static String createLogOutput(TradingRecord tradingRecord, TimeSeries series) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of trades for the strategy: " + tradingRecord.getTradeCount() + LB);
        // Analysis
        sb.append("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord) + LB);
        //
        sb.append("" + LB);
        sb.append("Trades:" + LB);
        List<Trade> trades = tradingRecord.getTrades();
        Num sum = DoubleNum.valueOf(0d);
        Num averagePercent = DoubleNum.valueOf(0d);
        for (Trade trade : trades) {
            sb.append("Entry " + trade.getEntry().getPrice() + TAB + " : Exit " + trade.getExit().getPrice() + TAB);
            Num diff = trade.getExit().getPrice().minus(trade.getEntry().getPrice());
            Num diffPercent = trade.getExit().getPrice().dividedBy(trade.getEntry().getPrice());
            diffPercent = diffPercent.minus(DoubleNum.valueOf(1));
            diffPercent = diffPercent.multipliedBy(DoubleNum.valueOf(100));
            String percent = NumberFormat.getCurrencyInstance().format(diffPercent.doubleValue());
            percent = percent.replace("€", "");
            String strDiff = NumberFormat.getCurrencyInstance().format(diff.floatValue());
            strDiff = strDiff.replace("€", "");
            sb.append("  Diff amount : " + strDiff + TAB + " Diff Percent : " + percent + " %  ");
            sb.append("Index-Diff " + (trade.getExit().getIndex() - trade.getEntry().getIndex()) + " ");
            sb.append(LB);
            sum = sum.plus(diff);
            averagePercent = averagePercent.plus(diffPercent);
        }
        sb.append("" + LB);
        String strSum = NumberFormat.getCurrencyInstance().format(sum.doubleValue());
        strSum = strSum.replace("€", "");
        sb.append("Total " + strSum + LB);
        //String percent = NumberFormat.getCurrencyInstance().format(averagePercent.doubleValue());
        String percent = NumberFormat.getNumberInstance().format(averagePercent.doubleValue());
        //percent = percent.replace("€", "");
        sb.append("Total Percent " + percent + " % " + LB);

        averagePercent = averagePercent.dividedBy(DoubleNum.valueOf(trades.size()));
        percent = NumberFormat.getNumberInstance().format(averagePercent.doubleValue());
        sb.append("Average Percent " + percent + " % " + LB);

        return sb.toString();
    }

    public static void printAddOrderInfo(List<ExtOrder> orders) {
        for (ExtOrder order : orders) {
            System.out.println("###add Order Info");
            System.out.println(order.getType());
            System.out.println(order.getPrice());
            System.out.println(order.getIndex());
            System.out.println("RSI " + order.getAddOrderInfo().getRsi());
            System.out.println("RSI Strenght" + order.getAddOrderInfo().getRsiStrength());
            System.out.println("RSI Strenght 1 " + order.getAddOrderInfo().getRsiStrength1());
            System.out.println("Sto " + order.getAddOrderInfo().getSto());
            System.out.println("Sto Strenght" + order.getAddOrderInfo().getStoStrength());
            System.out.println("Sto Strenght 1 " + order.getAddOrderInfo().getStoStrength1());
            System.out.println("SMA3 Strength " + order.getAddOrderInfo().getSma3());
            System.out.println("SMA8 Strength " + order.getAddOrderInfo().getSma8());
            System.out.println("SMA50 Strength " + order.getAddOrderInfo().getSma50());
            System.out.println("SMA200 Strength " + order.getAddOrderInfo().getSma200());
            System.out.println("SMA314 Strength " + order.getAddOrderInfo().getSma314());
            System.out.println("EMA14 Strength " + order.getAddOrderInfo().getEma14());
            System.out.println("EMA50 Strength " + order.getAddOrderInfo().getEma50());
            System.out.println("Price over SMA 200 " + order.getAddOrderInfo().isPriceAboveSma200());
            System.out.println("Price over SMA 314 " + order.getAddOrderInfo().isPriceAboveSma3141());
            System.out.println("Price is long time Bullish " + order.getAddOrderInfo().isSMALongTimeBullish());
            System.out.println("###end add Order Info");
        }
    }

}

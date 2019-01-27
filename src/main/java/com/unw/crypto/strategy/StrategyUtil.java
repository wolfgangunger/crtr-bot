/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.ExtOrder;
import com.unw.crypto.model.Tick;
import com.unw.crypto.strategy.to.StrategyInputParams;
import java.util.List;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.DoubleNum;

/**
 *
 * @author UNGERW
 */
public final class StrategyUtil {

    private StrategyUtil() {
    }

    public static BaseTimeSeries copySeries(TimeSeries source) {
        BaseTimeSeries result = new BaseTimeSeries("series", DoubleNum::valueOf);
        List<Bar> bars = source.getBarData();
        bars.forEach((b) -> {
            result.addBar(b);
        });
        return result;
    }

    public static int getTimeInHoursAndMinutes(Tick tick) {
        return tick.getTradeTime().getHours() + tick.getTradeTime().getMinutes();
    }

    public static TradingRecord buildTradingRecord(List<ExtOrder> orders) {
        ExtOrder[] orderArray = new ExtOrder[orders.size()];
        orderArray = orders.toArray(orderArray);
        return orders.size() > 0 ? new BaseTradingRecord(orderArray) : new BaseTradingRecord();
    }

    public static void printParams(StrategyInputParams params) {
        System.out.println("### params ###");
        System.out.println(params.getBarDuration());
        System.out.println("#Entry Rules#");
        System.out.println("R1 RSI Low " + params.getEntryRuleChain().isRule1_rsiLow());
        System.out.println("R2 STO Low " + params.getEntryRuleChain().isRule2_stoLow());
        System.out.println("R3 Price above sma200 Low " + params.getEntryRuleChain().isRule3_priceAboveSMA200());
        System.out.println("R4 MA8> " + params.getEntryRuleChain().isRule4_ma8PointingUp());
        System.out.println("R5 Price below 8MA " + params.getEntryRuleChain().isRule5_priceBelow8MA());
        System.out.println("R7 EmaBands -> " + params.getEntryRuleChain().isRule7_emaBandsPointingUp());
        System.out.println("R11 RSI -> " + params.getEntryRuleChain().isRule11_isRsiPointingUp());
        System.out.println("R12 STO -> " + params.getEntryRuleChain().isRule12_isStoPointingUp());
        System.out.println("R13 MovMom " + params.getEntryRuleChain().isRule13_movingMomentum());
        System.out.println("#xit Rules#");
        System.out.println("R1 RSI HIgh " + params.getExitRuleChain().isRule1_rsiHigh());
        System.out.println("R2 STO HIgh " + params.getExitRuleChain().isRule2_stoHigh());
        System.out.println("R3 8MA down " + params.getExitRuleChain().isRule3_8maDown());
        System.out.println("R11 RSI ->down " + params.getExitRuleChain().isRule11_rsiPointingDown());
        System.out.println("R12 STO ->down " + params.getExitRuleChain().isRule12_StoPointingDown());
        System.out.println("R21 Price -> down " + params.getExitRuleChain().isRule21_priceFalling());
        System.out.println("R22 Stop Loss " + params.getExitRuleChain().isRule22_stopLoss());
        System.out.println("R23 Stop Gain " + params.getExitRuleChain().isRule23_stopGain());
        System.out.println("R24 Macd falling " + params.getExitRuleChain().isRule24_macdFalling());
        System.out.println("R25 ShortEma falling " + params.getExitRuleChain().isRule25_shortEmaFalling());
        System.out.println("# params #");
        System.out.println("EmaInd Timeframe " + params.getEmaIndicatorTimeframe());
        System.out.println("Ema Long " + params.getEmaLong());
        System.out.println("Ema Short " + params.getEmaShort());
        System.out.println("Falling Strenth " + params.getFallingStrenght());
        System.out.println("Price Timeframe " + params.getPriceTimeFrameBuy());
        System.out.println("Rising Strenth " + params.getRisingStrenght());
        System.out.println("RSI Threshold High " + params.getRsiThresholdHigh());
        System.out.println("RSI Threshold Low " + params.getRsiThresholdLow());
        System.out.println("RSI Timeframe " + params.getRsiTimeframeBuy());
        System.out.println("SMA Ind Timeframe " + params.getSmaIndicatorTimeframe());
        System.out.println("SMA Long " + params.getSmaLong());
        System.out.println("SMA Short " + params.getSmaShort());
        System.out.println("StoOsk Threshold High " + params.getStoOscKThresholdHigh());
        System.out.println("StoOsk Threshold Low " + params.getStoOscKThresholdLow());
        System.out.println("StoOsk Timeframe " + params.getStoOscKTimeFrame());
        System.out.println("Sto Threshold High " + params.getStoThresholdHigh());
        System.out.println("Sto Threshold Low " + params.getStoThresholdLow());
        System.out.println("StopGain " + params.getStopGain());
        System.out.println("Stop Loss " + params.getStopLoss());
        System.out.println("Wait Bars " + params.getWaitBars());
        System.out.println("### params ###");
    }

}

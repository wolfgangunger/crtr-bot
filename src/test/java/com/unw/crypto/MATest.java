/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto;

import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.service.IndicatorCalculationService;
import com.unw.crypto.service.MarketAnalyzer;
import java.time.LocalDate;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

/**
 *
 * @author UNGERW
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
//@SpringBootTest(classes =StrategyConfigurationTest.class )
//@AutoConfigurationPackage
//@SpringBootConfiguration
public class MATest {

    @Autowired
    private TimeSeriesDBLoader timeSeriesDBLoader;

    @Autowired
    private MarketAnalyzer marketAnalyzer;
    
    @Autowired
    private IndicatorCalculationService indicatorCalculationService;



//    private StrategyConfigurationRepository strategyConfigurationRepository;
//    @Autowired
//    private AbstractStrategyParamsRepository abstractStrategyParamsRepository;
    @Test
    public void test1() {
        System.out.println("run");
        Assert.assertNotNull(timeSeriesDBLoader);
        LocalDate from = LocalDate.of(2017, 8, 1);
        LocalDate until = from.plusMonths(1);
        TimeSeries series = timeSeriesDBLoader.loadSeriesWithParams(from, until, Currency.BTC, Exchange.BITSTAMP, 120);
        int endIndex = series.getEndIndex();
        int beginIndex = series.getBeginIndex();
        System.out.println(beginIndex);
        System.out.println(endIndex);
        System.out.println(series.getFirstBar().getDateName() + " " + series.getFirstBar().getClosePrice());
        System.out.println(series.getLastBar().getDateName() + " " + series.getLastBar().getClosePrice());
        System.out.println("barcount" + series.getMaximumBarCount());
        System.out.println("end inex" + series.getEndIndex());
        System.out.println(series.getBar(series.getEndIndex() - 12).getDateName());
        System.out.println(series.getBar(series.getEndIndex() - 24).getDateName());
        System.out.println(series.getBar(series.getEndIndex() - 36).getDateName());
        System.out.println("close Price");
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        System.out.println(closePrice.getValue(0));
        System.out.println(closePrice.getValue(beginIndex));
        System.out.println(closePrice.getValue(endIndex));

//        for (int i = 0; i <= endIndex; i++) {
//                System.out.println(series.getBar(i).getDateName() + " : " + series.getBar(i).getClosePrice() + " :closePrice " + closePrice.getValue(i));    
//        }
        System.out.println("SMA");
// simple moving average on long time frame
        SMAIndicator sma = new SMAIndicator(closePrice, 14);
        System.out.println(sma.getValue(beginIndex));
        System.out.println(sma.getValue(endIndex));
        for (int i = 0; i <= endIndex; i++) {
            System.out.println(series.getBar(i).getDateName() + " : " + series.getBar(i).getClosePrice() + " :sma " + sma.getValue(i));
        }
        // market analyzer
        double strenght = marketAnalyzer.getMarketSMAStrength(series, 14);
        System.out.println(strenght);
    }

}

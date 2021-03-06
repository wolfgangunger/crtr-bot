
package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 * CCI Correction Strategy
 * <p></p>
 * @see <a href="http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:cci_correction">
 *     http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:cci_correction</a>
 */
@Component
public class CCICorrectionStrategy extends AbstractStrategy{

    
    /**
     * @param series a time series
     * @return a CCI correction strategy
     */
    public  Strategy buildStrategy(TimeSeries series,BarDuration barDuration) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        CCIIndicator longCci = new CCIIndicator(series, 200);
        CCIIndicator shortCci = new CCIIndicator(series, 5);
        Num plus100 = DoubleNum.valueOf(BigDecimal.valueOf(100));
        Num minus100 = DoubleNum.valueOf(BigDecimal.valueOf(- 100));
        
        Rule entryRule = new OverIndicatorRule(longCci, plus100) // Bull trend
                .and(new UnderIndicatorRule(shortCci, minus100)); // Signal
        
        Rule exitRule = new UnderIndicatorRule(longCci, minus100) // Bear trend
                .and(new OverIndicatorRule(shortCci, plus100)); // Signal
        
        Strategy strategy = new BaseStrategy(entryRule, exitRule);
        strategy.setUnstablePeriod(5);
        return strategy;
    }

    public TradingRecord execute(TimeSeries series,BarDuration barDuration) {
        // Building the trading strategy
        Strategy strategy = buildStrategy(series, barDuration);

        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());

        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }
}

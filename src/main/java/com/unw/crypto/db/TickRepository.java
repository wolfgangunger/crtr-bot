package com.unw.crypto.db;

import com.unw.crypto.model.Tick;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TickRepository extends JpaRepository<Tick, Long> {

    @Query("SELECT t FROM Tick t  WHERE t.currency=:currency AND t.exchange= :exchange order by t.tradeTime ASC ")
    List<Tick> findByExchangeAndCurrency(@Param("exchange") String exchange, @Param("currency") String currency);

    @Query("SELECT t FROM Tick t  WHERE t.currency= :currency AND t.exchange= :exchange AND t.tradeTime >= :fromDate AND t.tradeTime <= :untilDate order by t.tradeTime ASC ")
    List<Tick> findByExchangeAndCurrencyAndDates(@Param("exchange") String exchange, @Param("currency") String currency,
            @Param("fromDate") Date fromDate, @Param("untilDate") Date untilDate);

    @Query("SELECT t FROM Tick t  WHERE t.currency=:currency AND t.exchange= :exchange order by t.tradeTime ASC ")
    Tick findByExchangeAndCurrencyFirst(@Param("exchange") String exchange, @Param("currency") String currency);

    @Query("SELECT t FROM Tick t  WHERE t.currency=:currency AND t.exchange= :exchange order by t.tradeTime DESC ")
    Tick findByExchangeAndCurrencyLast(@Param("exchange") String exchange, @Param("currency") String currency);
    
    Tick findTopByCurrencyAndExchangeOrderByTradeTimeDesc(@Param("currency") String currency, @Param("exchange") String exchange);
//    
    Tick findTopByCurrencyAndExchangeOrderByTradeTimeAsc(@Param("currency") String currency, @Param("exchange") String exchange );    
    
    Tick findTopByCurrencyOrderByTradeTimeAsc(@Param("currency") String currency);   
}

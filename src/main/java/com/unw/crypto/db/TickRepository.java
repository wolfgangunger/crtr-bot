package com.unw.crypto.db;

import com.unw.crypto.model.Tick;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TickRepository extends JpaRepository<Tick, Long> {

//     @Query("SELECT t FROM Tick t  WHERE t.currency=(:currency) AND t.exchange= (:exchange) order by t.tradeTime")
    @Query("SELECT t FROM Tick t  WHERE t.currency=:currency AND t.exchange= :exchange ")
    List<Tick> findByExchangeAndCurrency(@Param("exchange") String exchange, @Param("currency") String currency);

    @Query("SELECT t FROM Tick t  WHERE t.currency= :currency AND t.exchange= :exchange AND t.tradeTime >= :fromDate AND t.tradeTime <= :untilDate order by t.tradeTime ASC ")
    List<Tick> findByExchangeAndCurrencyAndDates(@Param("exchange") String exchange, @Param("currency") String currency,
            @Param("fromDate") Date fromDate, @Param("untilDate") Date untilDate);
}

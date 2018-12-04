package com.unw.crypto.db;

import com.unw.crypto.model.Tick;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TickRepository extends JpaRepository<Tick, Long> {
    
     @Query("SELECT t FROM Tick t  WHERE t.currency=(:currency) AND t.exchange= (:exchange) order by t.tradeTime")
     List<Tick> findByExchangeAndCurrency(@Param("exchange") String exchange, @Param("currency") String currency);

}

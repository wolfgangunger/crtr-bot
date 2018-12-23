/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.loader;

import com.unw.crypto.Config;
import com.unw.crypto.loader.CSVAdapter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
@Component
public  class DataLoader {

    public  TimeSeries loadData() {
        LocalDate df = LocalDate.parse(Config.fromDate);
        LocalDate du = LocalDate.parse(Config.untilDate);
        Instant instantFrom = df.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        Instant instantUntil = du.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        TimeSeries series = CSVAdapter.loadBitstampSeries(Config.csv_file,instantFrom, instantUntil);
        return series;
    }

}

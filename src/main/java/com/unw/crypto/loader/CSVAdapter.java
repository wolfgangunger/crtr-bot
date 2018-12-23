/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.loader;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;


    


import com.opencsv.CSVReader;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ta4j.core.num.DoubleNum;


/**
 * see http://api.bitcoincharts.com/v1/csv/
 * @author UNGERW
 */
public class CSVAdapter {
    
    /**
     * @param filename
     * @return a time series from Bitstamp (bitcoin exchange) trades
     */
    public static TimeSeries loadBitstampSeries(String filename, Instant from, Instant until) {

        // Reading all lines of the CSV file
        InputStream stream = CSVAdapter.class.getClassLoader().getResourceAsStream(filename);
        CSVReader csvReader = null;
        List<String[]> lines = null;
        try {
            csvReader = new CSVReader(new InputStreamReader(stream, Charset.forName("UTF-8")), ',');
            lines = csvReader.readAll();
            lines = filterLines(lines,from, until);
            //lines.remove(0); // Removing header line
        } catch (IOException ioe) {
            Logger.getLogger(CSVAdapter.class.getName()).log(Level.SEVERE, "Unable to load trades from CSV", ioe);
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        List<Bar> bars = null;
        if ((lines != null) && !lines.isEmpty()) {

            // Getting the first and last trades timestamps
            ZonedDateTime beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(lines.get(0)[0]) * 1000), ZoneId.systemDefault());
            ZonedDateTime endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(lines.get(lines.size() - 1)[0]) * 1000), ZoneId.systemDefault());
            if (beginTime.isAfter(endTime)) {
                Instant beginInstant = beginTime.toInstant();
                Instant endInstant = endTime.toInstant();
                beginTime = ZonedDateTime.ofInstant(endInstant, ZoneId.systemDefault());
                endTime = ZonedDateTime.ofInstant(beginInstant, ZoneId.systemDefault());
                // Since the CSV file has the most recent trades at the top of the file, we'll reverse the list to feed the List<Bar> correctly.
                Collections.reverse(lines);
            }
            // build the list of populated bars
           	bars = buildBars(beginTime, endTime, 300, lines);
        }

        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    private static List<String[]> filterLines(List<String[]> lines, Instant from, Instant until){
        List<String[]> result = new ArrayList<>();
        for(String[] string : lines){
           String strDate = string[0];
           //ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(strDate) * 1000), ZoneId.systemDefault());
           Instant instant = Instant.ofEpochMilli(Long.parseLong(strDate) * 1000);
           //System.out.println(strDate + " "  + instant.toString());
           if(instant.compareTo(from)>= 0 && instant.compareTo(until)<= 0 ){
               result.add(string);
           }
        }
        return result;
    }
    /**
     * Builds a list of populated bars from csv data.
     * @param beginTime the begin time of the whole period
     * @param endTime the end time of the whole period
     * @param duration the bar duration (in seconds)
     * @param lines the csv data returned by CSVReader.readAll()
     * @return the list of populated bars
     */
    private static List<Bar> buildBars(ZonedDateTime beginTime, ZonedDateTime endTime, int duration, List<String[]> lines) {

    	List<Bar> bars = new ArrayList<>();

    	Duration barDuration = Duration.ofSeconds(duration);
    	ZonedDateTime barEndTime = beginTime;
    	// line number of trade data
    	int i = 0;
    	do {
    		// build a bar
    		barEndTime = barEndTime.plus(barDuration);
    		Bar bar = new BaseBar(barDuration, barEndTime, null);
    		do {
    			// get a trade
    			String[] tradeLine = lines.get(i);
    			ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(tradeLine[0]) * 1000), ZoneId.systemDefault());
    			// if the trade happened during the bar
    			if (bar.inPeriod(tradeTimeStamp)) {
    				// add the trade to the bar
    				double tradePrice = Double.parseDouble(tradeLine[1]);
    				double tradeAmount = Double.parseDouble(tradeLine[2]);
    				bar.addTrade(DoubleNum.valueOf(tradeAmount), DoubleNum.valueOf(tradePrice));
    			} else {
    				// the trade happened after the end of the bar
    				// go to the next bar but stay with the same trade (don't increment i)
    				// this break will drop us after the inner "while", skipping the increment
    				break;
    			}
    			i++;
    		} while (i < lines.size());
    		// if the bar has any trades add it to the bars list
    		// this is where the break drops to
    		if (bar.getTrades() > 0) {
    			bars.add(bar);
    		}
    	} while (barEndTime.isBefore(endTime));
    	return bars;
    }
 
}


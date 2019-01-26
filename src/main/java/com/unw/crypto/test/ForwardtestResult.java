package com.unw.crypto.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_forwardtest_result")
public class ForwardtestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String strategy;
    
    private int testRun;
    
    private LocalDateTime periodStart;

    private LocalDateTime periodEnd;

    private String currency;

    private String exchange;

    private Double profit;

    private Double avgProfitPerTrade;

    private String configuration;
    
    private int configNumber;
    
    private int numberOfTrades;
    
    private double totalProfit;
    
    private double total;
    
    private double totalPercent;
    
    private double averagePercent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forwardtestResult", cascade = CascadeType.ALL)
    private List<Trade> trades = new ArrayList<>();

}

package com.unw.crypto.test;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private LocalDateTime date;

    @Column(columnDefinition = "DECIMAL(10,4)")
    private Double price;

    @Column(columnDefinition = "DECIMAL(10,4)")
    private Double priceEntry;

    @Column(columnDefinition = "DECIMAL(10,4)")
    private Double priceExit;
    
    private double percent;
    
    private double amount;
    
    private int indexDiff;

    @ManyToOne
    private ForwardtestResult forwardtestResult;

}

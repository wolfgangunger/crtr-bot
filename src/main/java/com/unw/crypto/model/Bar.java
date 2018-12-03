package com.unw.crypto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_bar")
public class Bar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;
	
	private Date timeEnd;
	
	private double openPrice;
	
	private double lastPrice;
		
	private double avgPrice;
	
	private double highPrice;
	
	private double lowPrice;
        
        private String currency;
        
        private String exchange;
	
}

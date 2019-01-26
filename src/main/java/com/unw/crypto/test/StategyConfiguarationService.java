package com.unw.crypto.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StategyConfiguarationService {
	
	PermutationProvider permutationProvider;
	
	@Autowired
	public StategyConfiguarationService(PermutationProvider permutationProvider) {
		this.permutationProvider = permutationProvider;
		
	}
	
	public List<String> getStrategyConfiguration(StrategyType strategyType) {
		switch (strategyType) {
		case FINAL_LONG:
			return getRsiStrategyConfiguration();
		case FINAL_SHORT:
			return getMovingMonumentumStrategyConfiguration();
		default:
			throw new IllegalArgumentException("Unknown strategy type: " + strategyType.name());
		}
	}
	
	private List<String> getRsiStrategyConfiguration() {
		List<Double> smaShortTimeframe = permutationProvider.generateSet(new ArrayList<>(), 5.0, 5.0, 1.0);
		List<Double> smaLongTimeframe = permutationProvider.generateSet(new ArrayList<>(), 200.0, 200.0, 1.0);
		List<Double> rsiTimeframe = permutationProvider.generateSet(new ArrayList<>(), 2.0, 2.0, 1.0);
		List<Double> crossedDownThreshold = permutationProvider.generateSet(new ArrayList<>(), 10.0, 10.0, 1.0);
		List<Double> crossedUpThreshold = permutationProvider.generateSet(new ArrayList<>(), 80.0, 80.0, 1.0);
		
		List<List<Double>> lists = Arrays.asList(smaShortTimeframe, smaLongTimeframe, rsiTimeframe, crossedDownThreshold, crossedUpThreshold);
		List<String> permutations = new ArrayList<>();
		permutationProvider.generatePermutations(lists, permutations, 0, "");
		
		return permutations;
	}
	
	private List<String> getMovingMonumentumStrategyConfiguration() {
		List<Double> iMAShortTimeFrame = permutationProvider.generateSet(new ArrayList<>(), 8.0, 10.0, 1.0);
		List<Double> iMALongTimeFrame = permutationProvider.generateSet(new ArrayList<>(), 25.0, 27.0, 1.0);
		List<Double> barCountStochasticOscillatorKIndicator = permutationProvider.generateSet(new ArrayList<>(), 14.0, 14.0, 1.0);
		List<Double> emaTimeFrame = permutationProvider.generateSet(new ArrayList<>(), 17.0, 19.0, 1.0);
		List<Double> crossedDownThreshold = permutationProvider.generateSet(new ArrayList<>(), 19.0, 21.0, 1.0);
		List<Double> crossedUpThreshold = permutationProvider.generateSet(new ArrayList<>(), 79.0, 81.0, 1.0);
		
		List<List<Double>> lists = Arrays.asList(iMAShortTimeFrame, iMALongTimeFrame, barCountStochasticOscillatorKIndicator, emaTimeFrame, crossedDownThreshold, crossedUpThreshold);
		List<String> permutations = new ArrayList<>();
		permutationProvider.generatePermutations(lists, permutations, 0, "");
		
		return permutations;
	}

}

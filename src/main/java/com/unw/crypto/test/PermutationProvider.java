package com.unw.crypto.test;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PermutationProvider {
	
	public void generatePermutations(List<List<Double>> lists, List<String> result, int depth, String current) {
		if (depth == lists.size()) {
			current = current.substring(0, current.length() - 1);
			result.add(current);
			return;
		}

		for (int i = 0; i < lists.get(depth).size(); ++i) {
			generatePermutations(lists, result, depth + 1, current + lists.get(depth).get(i) + ",");
		}
	}
	
	public List<Double> generateSet(List<Double> result, Double current, Double max, Double step) {
		if(current <= max) {
			result.add(current);
			generateSet(result, current + step, max, step);
		}
		return result;
	}
	
}

package topcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * @author Prasad
 * http://community.topcoder.com/stat?c=problem_statement&pm=12608
 * @date Jul 10, 2013
 *
 */
public class TravelOnMars {

	private int[] range;
	private int startCity;
	private int endCity;
	
	public TravelOnMars(int[] range, int startCity, int endCity) {
		this.range = range;
		this.startCity = startCity;
		this.endCity = endCity;
	}
	
	public void compute() {
		int minTimes = minTimes(range, startCity, endCity);
		System.out.println(minTimes);
	}
	
	private int minTimes(int[] range, int startCity, int endCity) {
		int n = range.length;
		int[] minTimesArray = new int[n];
		Arrays.fill(minTimesArray, Integer.MAX_VALUE);
		minTimesArray[startCity] = 0;
		
		Set<Integer> balanced = new HashSet<Integer>();
		balanced.add(startCity);
		Set<Integer> toVisit = new HashSet<Integer>();
		toVisit.add(startCity);
		
		while (!toVisit.isEmpty()) {
			int currentCity = getMinimumDistanceIndex(toVisit, minTimesArray);
			
			for (int k = 1; k <= range[currentCity]; k++) {
				int forward_index = (currentCity + k) % n;
				int backward_index = (n + currentCity - k) % n; // adding n to avoid negatives ex : curIndex = 0; k = 2; n = 4 results in 2;
				int curMinTime = minTimesArray[currentCity];

				if (!balanced.contains(forward_index)) {
					if (curMinTime + 1 < minTimesArray[forward_index]) {// use distance computed so far from startCity to currentCity
						minTimesArray[forward_index] = curMinTime + 1;
						toVisit.add(forward_index);
					}
				}

				if (!balanced.contains(backward_index)) {
					if (curMinTime + 1 < minTimesArray[backward_index]) {
						minTimesArray[backward_index] = curMinTime + 1;
						toVisit.add(backward_index);
					}
				}
			}
			toVisit.remove(currentCity);
			balanced.add(currentCity);
			
			String array = Arrays.toString(minTimesArray);
			System.out.println(array);
		}
		
		System.out.println("------------------------------------------------");
		String array = Arrays.toString(minTimesArray);
		System.out.println(array);
		
		return minTimesArray[endCity];
	}
	
	
	private int getMinimumDistanceIndex(Set<Integer> toVisit, int[] minTimesArray) {
		int minIndex = toVisit.iterator().next();
		int minDistance = minTimesArray[minIndex];
		for (int index : toVisit) {
			if (minDistance > minTimesArray[index]) {
				minDistance = minTimesArray[index];
				minIndex = index;
			}
		}
		return minIndex;
	}

	public static void main(String[] args) {
		int[] range = new int[] { 3, 2, 1, 1, 3, 1, 2, 2, 1, 1, 2, 2, 2, 2, 3 };
		int startCity = 6;
		int endCity = 13;
		new TravelOnMars(range, startCity, endCity).compute();
	}
	
	
}

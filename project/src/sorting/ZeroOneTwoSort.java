package sorting;

import java.util.Arrays;

public class ZeroOneTwoSort {
	
	
	private int[] numbers;
	
	public ZeroOneTwoSort(int[] numbers) {
		this.numbers = numbers;
	}
	
	
	public void sort() {
		int startOfOnes = 0;
		int current = 0;
		int curEndOfOnes = numbers.length - 1;
		while (current < curEndOfOnes) {
			if (numbers[current] == 0) {
				if (numbers[startOfOnes] != 0) {
					numbers[current] = numbers[startOfOnes];
					numbers[startOfOnes] = 0;
				}
				startOfOnes++;
				current++;
			} else if (numbers[current] == 1) {
				current++;
			} else {
				if (numbers[current] != numbers[curEndOfOnes]) {
					int temp = numbers[current];
					numbers[current] = numbers[curEndOfOnes];
					numbers[curEndOfOnes] = temp;
				}
				curEndOfOnes--;
			}
			
			
		}
		
		String sorted = Arrays.toString(numbers);
		System.out.println(sorted);
		
	}
	
	
	
	public static void main(String[] args) {
		int[] a = new int[]{2, 1, 0 , 1, 1, 1, 0, 1, 1, 2};
		new ZeroOneTwoSort(a).sort();
	}
	
}

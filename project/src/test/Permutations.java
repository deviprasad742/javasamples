package test;

import java.util.Arrays;

public class Permutations {

	private String input;
	private int count;
	
	public Permutations(String input) {
		this.input = input;
	}
	
	public void compute() {
//		permute("", input.toCharArray());
		permute(input.toCharArray(), 0);
		System.out.println(count);
	}
	
	
	private void permute (char[] charArray, int position) {
		if (position == charArray.length) {
			System.out.println(new String(charArray));
			count ++ ;
		} else {
			for (int i = position; i < charArray.length; i++) {
				swap(charArray, position, i);
				permute(charArray, position + 1);
				swap(charArray, position, i);// restore to old
			}
		}
	}

	private void swap(char[] charArray, int from, int to) {
		char temp = charArray[from];
		charArray[from] = charArray[to];   
		charArray[to] = temp;
	}
	
	
	private void permute(String prefix, char[] charArray) {
		if (charArray.length == 1) {
			System.out.println(prefix + charArray[0]);
			count ++ ;
		} else if (charArray.length == 0) {
			return;
		}

		for (int i = 0; i < charArray.length; i++) {
			char[] excludedCopy = new char[charArray.length - 1];
			if (i != 0) {
				System.arraycopy(charArray, 0, excludedCopy, 0, i);
			}
			if (i != charArray.length - 1) {
				System.arraycopy(charArray, i + 1, excludedCopy, i, excludedCopy.length - i);
			}
			permute(prefix + charArray[i], excludedCopy);
		}
	}

	public static void main(String[] args) {
		Permutations permutations = new Permutations("12345");
		permutations.compute();
	}
	
}

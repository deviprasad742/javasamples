package dynamicprogramming;

public class Factorial {

	public static void main(String[] args) {
/*		int num = 12;
		int[] result = new int[1];
		result[0] = 1;
		for (int i = 1; i <= num ; i ++) {
			int[] tempResult = new int[result.length];
			for (int j = 0; j < result.length; j++) {
				tempResult[j] = result[j] * i;
				if  (tempResult[j] < result[j]) {
                    tempResult = multiplyWithCarry(i, result, tempResult, j);	
                    result = tempResult;// set the new 
				}
				
			}
			result = tempResult;
		}
		
		for (int i = result.length - 1; i >= 0; i--) {
			if (result[i] != 0) {
				System.out.print(result[i]);
			}
		}
		int[] breakOverFlow = breakOverFlow(479001600, 1321 , 1);
		printArray(breakOverFlow);*/
	}
	
/*	private static void printArray(int[] result) {
		for (int i = result.length - 1; i >= 0; i--) {
			if (result[i] != 0) {
				System.out.println(result[i]);
			}
		}
	}

	private static int[] multiplyWithCarry(int i, int[] result, int[] tempResult, int j) {
		int bucketNo = result[j];
		if (tempResult.length == j + 1) {
			//double the array
			int[] doubleResult = new int[tempResult.length * 2];
			System.arraycopy(tempResult, 0, doubleResult, 0, tempResult.length);
			tempResult = doubleResult;
		}

		int divisor = 1;
		while (bucketNo / 10 != 0) { // extract left most digit
			bucketNo = bucketNo / 10;
			divisor = divisor * 10;
		}
		
		int remainder = result[j]/divisor;
		
		

		return tempResult;
	}
	
	private static int[] breakOverFlow(int number, int multiplier, int overflowDigits) {
		int digits = getNumberofDigits(number);
		int divisor = 1;
		for (int i = 1; i <= digits - overflowDigits; i++) {
			divisor = divisor * 10;
		}
		int leftDigits = number / divisor;
		int remainder = number % divisor;
		if (checkOverFlow(remainder, multiplier)) {// break overflow with incremented digits
			return breakOverFlow(number, multiplier, ++overflowDigits);
		}
		return new int[] { remainder, leftDigits };
	}
	
	private static int getNumberofDigits(int n) {
		int i = 1;
		while (n/10 != 0) {
			n = n/10;
			i++;
		}
		return i;
	}
	
	
	private static boolean checkOverFlow(int number1, int number2) {
		int lastResult = number1 * number2;
		while (number2/10 != 0) {
			number2 = number2/ 10;
			if (lastResult < number1 * number2) {
				return true;
			}
		}
		return false;
	}*/
	
}

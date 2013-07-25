package topcoder;

public class FindMinSwapNumber {

	public static void main(String[] args) {
		String[] numbers = {"4617239", "596", "93218910471211292416", "5491727514", "10234", "2304"};
		for (String number : numbers) {
			String swapDigits = swapDigits(number);
			System.out.println(number + "---->" + swapDigits);
		}
	}

	private static String swapDigits(String number) {
		char[] charArray = number.toCharArray();
		for (int i = 0; i < charArray.length; i ++) {
			int swapIndex = -1;
			int cur = (int)charArray[i];
			for (int j = i + 1; j < charArray.length; j++) {
				int swap = (int) charArray[j];
				if (cur > swap && (charArray[j] != '0' || i > 0)) {
					if (swapIndex == -1 || charArray[swapIndex] >= swap) {
						swapIndex = j;
					}
				}
			}
			if (swapIndex != -1) {
				char temp = charArray[i];
				charArray[i] = charArray[swapIndex];
				charArray[swapIndex] = temp;
				break;
			}
		}
       return new String(charArray);		
	}
	
}

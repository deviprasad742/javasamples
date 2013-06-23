package dynamicprogramming;
import java.util.GregorianCalendar;


public class NthPrime {

	public static void main(String[] args) {
		long start_time = new GregorianCalendar().getTimeInMillis();
		int n = 100000;
		int[] primes = new int[n];
		primes[0] = 2;
		primes[1] = 3;
		int primeIndex = 2;
		int number = 3;
		while (true) {
			number = number + 2;
			boolean isPrime = true;
			
			
			for (int i = 0; i < primeIndex; i++) {
				if (number % primes[i] == 0) { // divisible by existing primes not a prime
					isPrime = false;
					break;
				}
			}
			
			if (isPrime) {
				primes[primeIndex] = number; // update the new prime
				primeIndex++;
				if (primeIndex == n) {
					System.out.println(number);
					break;
				}
			}
			
		}
		long end_time = new GregorianCalendar().getTimeInMillis();
		System.out.println(primes[n-1]);
		System.out.println("Time taken in milliseconds:" + (end_time - start_time));
	}
	
}

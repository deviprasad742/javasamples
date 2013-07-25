package test;

public class SquareRoot {

	private static float squareRoot(float n) {
		/* We are using n itself as initial approximation This can definitely be improved */
		float x = n;
		float y = 1;
		float e = (float) 0.000001; /* e decides the accuracy level */
		while (x - y > e) {
			x = (x + y) / 2;
			y = n / x;
			System.out.println(x + "," + y);
		}
		return x;
	}
	
	public static void main(String[] args) {
		System.out.println(squareRoot(25));
	}
}

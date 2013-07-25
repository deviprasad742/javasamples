package gen;

public class TowersOfHanoi {
	private static int i = 0;
	public static void move(int n, int startPole, int interim, int endPole) {
		if (n == 0) {
			return;
		}
		move(n - 1, startPole, endPole, interim);
		System.out.println(++i + ".Move " + n + " from " + startPole + " to " + endPole);
		move(n - 1, interim, startPole, endPole);
	}

	public static void main(String[] args) {
		move(6, 1, 2, 3);
	}

}
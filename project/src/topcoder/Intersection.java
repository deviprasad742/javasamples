package topcoder;

public class Intersection {

	public static void doIntersect(Point p1, Point q1, Point p2, Point q2) {
		boolean intersect = false;
		if (p1.x == q1.x && p2.x == q2.x) {// represents lines x = c;
			intersect = p1.x == p2.x; // check if both lines represent same
		} else if (p1.y == q1.y && p2.y == q2.y) {// represents lines y = c;
			intersect = p1.y == p2.y; // check if both lines represent same
		} else {
			float m1 = (q1.y - p1.y) / (q1.x - p1.x); // slope (y2-y1)/(x2-x1);
			float m2 = (q2.y - p2.y) / (q2.x - p2.x);
			if (m1 != m2) {
				intersect = true;
			} else {// check if both represent the same line
				float c1 = q1.y - m1 * q1.x; // c = y - mx
				float c2 = q2.y - m2 * q2.x;
				if (c1 != c2) {// parallel lines
					intersect = false;
				} else {
					intersect = true;// both represent the same lines
				}
			}
		}
		System.out.println((intersect ? "Yes" : "No") + " : " + p1 + "------>" + q1 + " & " + p2 + "------>" + q2);

	}

	private static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "[" + x + "," + y + "]";
		}
	}

	// Driver program to test above functions
	public static void main(String[] args) {
		Point p1 = new Point(1, 1), q1 = new Point(10, 1);
		Point p2 = new Point(1, 2), q2 = new Point(10, 2);

		doIntersect(p1, q1, p2, q2);

		p1 = new Point(10, 0);
		q1 = new Point(0, 10);
		p2 = new Point(0, 0);
		q2 = new Point(10, 10);
		doIntersect(p1, q1, p2, q2);

		p1 = new Point(-5, -5);
		q1 = new Point(0, 0);
		p2 = new Point(1, 1);
		q2 = new Point(10, 10);

		doIntersect(p1, q1, p2, q2);

	}
}

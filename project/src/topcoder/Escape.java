package topcoder;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


/**
 * 
 * @author Prasad
 * @date Jul 7, 2013
 * http://community.topcoder.com/stat?c=problem_statement&pm=1170&rd=4371
 *
 */
public class Escape {

	private String[] harmful;
	private String[] deadly;
	private static int M = 501;
	private static int N = 501;
	private static int UNREACHABLE = Integer.MAX_VALUE;
	private int[][] severity = new int[M][N];// 0- normal,  1- harfmul, UNREACHABLE- deady
	private int[][] distance = new int[M][N];
	
	public Escape(String[] harmful, String[] deadly) {
		this.harmful = harmful;
		this.deadly = deadly;
		initialize();
	}
	
	public int execute() {
		computeMinLife();
		int minLife = distance[M-1][N-1];
		if (minLife == 0) {
			return -1;
		}
		return minLife;
	}
	
	private void initialize() {
		initialize(harmful, 1);
		initialize(deadly, UNREACHABLE);
	}

	private void initialize(String[] array, int life) {
		for (String points : array) {
			String[] split = points.split(" ");
			int x1 = Integer.valueOf(split[0]);
			int y1 = Integer.valueOf(split[1]);
			int x2 = Integer.valueOf(split[2]);
			int y2 = Integer.valueOf(split[3]);
			for (int i = 0; i <= Math.abs(x1 - x2); i++) {
				int next_x = x1 < x2 ? x1 + i : x1 - i;
				for (int j = 0; j <= Math.abs(y1 - y2); j++) {
					int next_y = y1 < y2 ? y1 + j : y1 - j;
					severity[next_x][next_y] = life;
				}
			}

		}
	}

	

	private void computeMinLife() {
		Queue<EscapePoint> points = new LinkedList<EscapePoint>();
		points.add(new EscapePoint(0, 0));
		while (!points.isEmpty()) {
			EscapePoint nextPoint = points.poll();
			int x = nextPoint.x;
			int y = nextPoint.y;
			System.out.println(x + "," + y);
			
			int sumSoFar = distance[x][y];
			checkAndAdd(x+1, y, sumSoFar, nextPoint, points);
			checkAndAdd(x, y+1, sumSoFar, nextPoint, points);
			checkAndAdd(x-1, y, sumSoFar, nextPoint, points);
			checkAndAdd(x, y-1, sumSoFar, nextPoint, points);
		}
	}


	private void checkAndAdd(int x, int y, int prevValue, EscapePoint currentPoint, Queue<EscapePoint> points) {
		EscapePoint newPoint = new EscapePoint(x, y);
		if (!(x < 0 || x >= M || y < 0 || y >= N)) {
			int cValue = severity[x][y];
			if (cValue == UNREACHABLE) {
				return;
			}
			int nValue = prevValue + cValue;
			if (distance[x][y] == 0 || distance[x][y] > nValue) {
				// add the new path to the point
				distance[x][y] = nValue;
				points.add(newPoint);
			}
		}
	}

	int findMin(int... values) {
		int min = UNREACHABLE;
		for (int i : values) {
			if (i < min) {
				min = i;
			}
		}

		return min;
	}
	
	
	public static void main(String[] args) {
		String[] harmful = {"0 0 250 250", "250 250 500 500" };
		String[] deadly = { "0 251 249 500", "251 0 500 249" };
//		deadly = new String[]{"0 0 0 0"};
//		harmful = new String[] {"500 0 0 500"};
//		deadly = new String[]{"0 250 250 500","250 0 500 250"};
		Escape escape = new Escape(harmful, deadly);
		System.out.println(escape.execute());
	}
	
	private class EscapePoint {
		int x,y;
		
		public EscapePoint(int x, int y) {
			this.x  = x;
			this.y = y;
		}
		
		
		@Override
		public int hashCode() {
			return 31*x + y;
		}
		
        @Override
        public boolean equals(Object obj) {
        	if (obj == null) {
        		return false;
        	} else if (obj instanceof EscapePoint) {
        		EscapePoint other = (EscapePoint) obj;
        		return other.x == this.x && other.y == this.y;
        	}
        	return false;
        }
		
	}
	
}

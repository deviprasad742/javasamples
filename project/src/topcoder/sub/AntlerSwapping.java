package topcoder.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class AntlerSwapping {
	public void p(Object... args) {
		System.out.println(Arrays.deepToString(args));
	}

	public int getmin(int[] A, int[] B, int C) {
		int N = A.length;
		int[] cost = new int[1 << N];
		Arrays.fill(cost, 100000);
		ArrayList<Integer> l = new ArrayList<Integer>();
		
		for (int i = 0; i < cost.length; i++) {
			l.clear();
			for (int j = 0; j < N; j++) {
				if (((1 << j) & i) != 0) {
					l.add(A[j]);
					l.add(B[j]);
				}
			}
			Collections.sort(l);
			boolean legal = true;
			for (int j = 0; j < l.size() / 2; j++) {
				if (abs(l.get(j * 2) - l.get(j * 2 + 1)) > C) {
					legal = false;
					break;
				}
			}
			if (legal)
				cost[i] = max(0, l.size() / 2 - 1);
		}
		for (int i = 0; i < cost.length; i++) {
			for (int j = i; j > 0; j = (j - 1) & i) {
				cost[i] = min(cost[i], cost[i - j] + cost[j]);
			}
		}
		int ans = cost[cost.length - 1];
		if (ans >= 100000)
			ans = -1;
		return ans;
	}

}
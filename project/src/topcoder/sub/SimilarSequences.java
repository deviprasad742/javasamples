package topcoder.sub;

import java.util.*;
import static java.lang.System.arraycopy;

public class SimilarSequences {

	static class Sequence {
		int[] array;

		public Sequence(int[] array) {
			this.array = array;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(array);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Sequence other = (Sequence) obj;
			if (!Arrays.equals(array, other.array))
				return false;
			return true;
		}
	}

	static final int MOD = 1000000009;

	public int count(int[] seq, int bound) {
		int n = seq.length;
		Set<Integer> interesting = new HashSet<Integer>();
		for (int i : seq) {
			interesting.add(i);
		}
		int wildCard = -1;
		for (int i = 0; i < bound; i++) {
			if (!interesting.contains(i)) {
				wildCard = i;
				break;
			}
		}
		if (wildCard >= 0) {
			interesting.add(wildCard);
		}
		Set<Sequence> set = new HashSet<Sequence>();
		for (int remove = 0; remove < n; remove++) {
			int[] newSeq = new int[n - 1];
			arraycopy(seq, 0, newSeq, 0, remove);
			arraycopy(seq, remove + 1, newSeq, remove, n - remove - 1);
			for (int addTo = 0; addTo < n; addTo++) {
				for (int addWhat : interesting) {
					int[] t = new int[n];
					arraycopy(newSeq, 0, t, 0, addTo);
					t[addTo] = addWhat;
					arraycopy(newSeq, addTo, t, addTo + 1, n - 1 - addTo);
					Sequence get = new Sequence(t);
					set.add(get);
				}
			}
		}
		long res = 0;
		for (Sequence s : set) {
			boolean haveWildcard = false;
			for (int i : s.array) {
				if (i == wildCard) {
					haveWildcard = true;
				}
			}
			if (!haveWildcard) {
				res++;
			} else {
				res += bound - interesting.size() + 1;
			}
		}
		res %= MOD;
		return (int) res;
	}
	
	public static void main(String[] args) {
		int[] seq = {1, 2, 3, 4, 5};
		seq = new int[] {5, 8, 11, 12, 4, 1, 7, 9};
		int bound = 5;
//		bound = 7;
		bound = 1000000000;
		int count = new SimilarSequences().count(seq, bound);
		System.out.println(count);
	}

}
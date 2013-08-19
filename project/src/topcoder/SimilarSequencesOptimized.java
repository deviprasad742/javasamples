package topcoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Prasad
 * 	http://community.topcoder.com/stat?c=problem_statement&pm=12615
 * @date Jul 5, 2013
 *
 */
public class SimilarSequencesOptimized {
	
	private Set<String> found = new HashSet<String>();
	private int[] seq;
	private int bound;

	public SimilarSequencesOptimized(int[] seq, int bound) {
		this.seq = seq;
		this.bound = bound;
	}
	
	public void execute() {
	   permutate(seq, 0, false, false);
	   System.out.println("Total number of matches: " + found.size());
	}
	
	private void permutate(int[] permSeq, int permIndex, boolean nestedForward, boolean nestedBackWard) {
		if (permIndex == permSeq.length || permIndex == -1) {
			return;
		}

		int[] oneDisSeq = Arrays.copyOf(permSeq, permSeq.length);
		for (int i = 1; i <= bound; i++) {
			oneDisSeq[permIndex] = i;
			String nextPrefix = toString(oneDisSeq);
			found.add(nextPrefix);
			/*if (found.add(nextPrefix)) {
				System.out.println((found.size()) + "." + nextPrefix);
			}*/

			if (!nestedBackWard) {
				if (permIndex < permSeq.length - 1) {
					if (i == permSeq[permIndex + 1]) {
						permutate(oneDisSeq, permIndex + 1, true, false);
					}
				}
			}
			
			if (!nestedForward) {
				if (permIndex > 0) {
					if (i == permSeq[permIndex - 1]) {
						permutate(oneDisSeq, permIndex - 1, false, true);
					}
				}
			}
		}

		// continue permutating for other indexes
		if (!nestedForward && !nestedBackWard) {
			permutate(permSeq, permIndex + 1, false, false);
		}
	}
	
	private String toString(int[] array) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i : array) {
			builder.append(i);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		int[] seq = {1, 2, 3, 4, 5};
		seq = new int[] {5, 8, 11, 12, 4, 1, 7, 9};
		int bound = 5;
		bound = 7;
//		bound = 100000000;
		new SimilarSequencesOptimized(seq, bound).execute(); 
	}
	
	
}

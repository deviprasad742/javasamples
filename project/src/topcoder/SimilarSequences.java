package topcoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.ibm.disthub2.impl.gd.ARangeList;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * 
 * @author Prasad
 * 	http://community.topcoder.com/stat?c=problem_statement&pm=12615
 * @date Jul 5, 2013
 *
 */
public class SimilarSequences {
	
	private Set<String> found = new HashSet<String>();
	private int[] seq;
	private int bound;

	public SimilarSequences(int[] seq, int bound) {
		this.seq = seq;
		this.bound = bound;
	}
	
	public void execute() {
	   permutate(seq, 0);
	   System.out.println("Total number of matches: " + found.size());
	}
	
	private void permutate(int[] prefix, int permIndex) {
		int[] origSubSeq = Arrays.copyOf(seq, permIndex + 1);
		int[] newSubSeq = Arrays.copyOf(prefix, permIndex + 1);
		for (int i = 1; i <= bound; i++) {
			newSubSeq[permIndex] = i;
			if (matches(origSubSeq, newSubSeq)) {
				if (permIndex == seq.length - 1) {// end of the permutation
					String nextPrefix = toString(newSubSeq);
					if (found.add(nextPrefix)) {
						System.out.println((found.size()) + "." + nextPrefix);
					}
				} else {
					permutate(newSubSeq, permIndex + 1);
				}
			} else if (permIndex < seq.length - 1) {
				//ignore
			}
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
		int[] seq = {1, 2, 3, 4};
//		seq = new int[] {5, 8, 11, 12, 4, 1, 7, 9};
//		boolean matches = new SimilarSequences(seq, 5).matches(seq, "41345".toCharArray());
//		System.out.println(matches);
		new SimilarSequences(seq, 7).execute(); 
	}
	
	
	private boolean matches(int[] array1, int[] array2) {
		int maxSubSequence = maxSubSequence(array1, array2, 0, 0);
//		System.out.println(maxSubSequence);
		return maxSubSequence >= (array1.length -1);
	}
	
	
	
	private int maxSubSequence(int[] array1, int[] array2, int i, int j) {
		if (i > array1.length - 1 || j > array2.length-1) {
			return 0;
		}
		if(array1[i] == array2[j]) {
			return 1 +  maxSubSequence(array1, array2, i+1, j+1);
		}
		return Math.max(maxSubSequence(array1, array2, i + 1, j), maxSubSequence(array1, array2, i, j + 1));
	}
	
	
}

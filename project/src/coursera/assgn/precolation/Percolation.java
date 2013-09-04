package coursera.assgn.precolation;

import coursera.algos.WeightedQuickUnionUF;

public class Percolation {

	WeightedQuickUnionUF weightedQUF;
	private boolean[][] openSites;
	private int n;
	private int qufSize;
	private int virtualTopIndex = 0;
	private int virtualBottomIndex;

	public Percolation(int N) {
		this.n = N;
		qufSize = N * N + 2;
		// one each extra for virtual top and bottom cells
		weightedQUF = new WeightedQuickUnionUF(qufSize);
		openSites = new boolean[N][N];
		virtualBottomIndex = qufSize -1;
	}

	public void open(int i, int j) {
		checkIndices(i, j);
		openSites[i-1][j-1] = true;
		
		if (i == 1) {
			// connect to virtual top for first row elements
			weightedQUF.union(virtualTopIndex, toQufIndex(i, j));  
		} else if (i == n) {
			// connect to virtual bottom for last row elements
			weightedQUF.union(virtualBottomIndex, toQufIndex(i, j));
		}
		
		// connected all the opened neighbors
		
		if (i + 1 <= n) {
			if (isOpen(i + 1, j)) {
				weightedQUF.union(toQufIndex(i, j), toQufIndex(i + 1, j));
			}
		}

		if (j + 1 <= n) {
			if (isOpen(i, j + 1)) {
				weightedQUF.union(toQufIndex(i, j), toQufIndex(i, j + 1));
			}
		}

		if (i - 1 > 0) {
			if (isOpen(i - 1, j)) {
				weightedQUF.union(toQufIndex(i, j), toQufIndex(i - 1, j));
			}
		}

		if (j - 1 > 0) {
			if (isOpen(i, j - 1)) {
				weightedQUF.union(toQufIndex(i, j), toQufIndex(i, j - 1));
			}
		}

	}

	public boolean isOpen(int i, int j) {
		checkIndices(i, j);
		return openSites[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		checkIndices(i, j);
		// checke if the indices are connected to virtual top
		return weightedQUF.connected(virtualTopIndex, toQufIndex(i, j));
	}

	public boolean percolates() {
		// if the virtual top and virtual bottom are connected
		return weightedQUF.connected(virtualTopIndex, virtualBottomIndex);
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return mapped index in the weightedQUF for the i,j element
	 */
	private int toQufIndex(int i, int j) {
		// 0'th index is virtual top so every index should be added with 1 while mapping 
		return (i - 1) * n + (j - 1) + 1;
	}

	private void checkIndices(int i, int j) {
		if (i <= 0 || i > n)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j <= 0 || j > n)
			throw new IndexOutOfBoundsException("column index j out of bounds");

	}

}
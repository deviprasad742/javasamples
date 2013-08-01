package amazon;

public class DeleteKeyInTwoDimArrayNoDups {

	
	public void deleteKey (int k , int[][] array, int m, int n) {
		Point keyToDelete = findKey(k, array, m, n);
		if (keyToDelete.x < 0 || keyToDelete.x >= m || keyToDelete.y < 0 || keyToDelete.y >= n) {
			// key not found
			return;
		}
		
		while (true) {
			if (keyToDelete.x == 0 && keyToDelete.y == 0) { // boundaries
				array[keyToDelete.x][keyToDelete.y] = Integer.MIN_VALUE;
				break;
			} else if (keyToDelete.x == m - 1 && keyToDelete.y == n - 1) {
				array[keyToDelete.x][keyToDelete.y] = Integer.MAX_VALUE;
				break;
			} else {
				// try swapping with one of the neighbor elements preserving the sorting properties and move towards
				// one of the corners  top-left or bottom-right whichever is near
				
				int leftValue = keyToDelete.y - 1 > 0 ? array[keyToDelete.x][keyToDelete.y - 1] : Integer.MIN_VALUE;
				int topValue = keyToDelete.x - 1 > 0 ? array[keyToDelete.x - 1][keyToDelete.y] : Integer.MIN_VALUE;
				int rightValue = keyToDelete.y + 1 < n ? array[keyToDelete.x][keyToDelete.y + 1] : Integer.MAX_VALUE;
				int downValue = keyToDelete.x + 1 < m ? array[keyToDelete.x + 1][keyToDelete.y] : Integer.MAX_VALUE;

				int minBoundary = Math.max(leftValue, topValue); // element should be greater than left and top
				int maxBoundary = Math.max(rightValue, downValue); // element should be less than bottom and right

				// example   2  3  4  transforms to 2  3  4
				//           7 '9' 11               7 '8' 11
				//           10 12 13               10 12 13
				// key to delete is 9
				// minBoundary = 7; max boundary = 11  one of the 8 or 10 could be chosen
				// delete key 9 can be replaced with element between 7 and 11 while preserving properties			
				// newNumber could be 8;
				
				
				// new element should be between min and max boundaries to preserve the sorting properties 
				int i = minBoundary;
				while (i <= maxBoundary) {
					if (i == k) {
						i++;
						continue;// don't choose current key value
					}
					if (i != leftValue && i != rightValue && i != topValue && i != downValue) {
						break; // found an element different than surrounding elements
					}
					i++;
				}

				if (i == maxBoundary + 1) { // no element found with in the boundaries
				 // move towards left top or bottom right which is nearer to current index and swap the index
					
					Point newKey = null;
					if ((keyToDelete.x + keyToDelete.y) < (((m - 1) - keyToDelete.x) + ((n - 1) - keyToDelete.y))) {
						// top-left is near to current index
						if (minBoundary == leftValue) {// move left
							newKey = new Point(keyToDelete.x, keyToDelete.y - 1);
						} else {// move top
							newKey = new Point(keyToDelete.x - 1, keyToDelete.y);
						}
					} else {
						// bottom-right corner is nearer
						if (maxBoundary == rightValue) {// move right
							newKey = new Point(keyToDelete.x, keyToDelete.y + 1);
						} else {// move down
							newKey = new Point(keyToDelete.x + 1, keyToDelete.y - 1);
						}
					}
					// current position can be swapped with new position without violating properties
					array[keyToDelete.x][keyToDelete.y] = array[newKey.x][newKey.y];
					array[newKey.x][newKey.y] = k; // move key to new location
					keyToDelete = newKey; // now key to delete is the swapped key
				} else {
					// found a suitable element which is between min and max boundary
					
					
					array[keyToDelete.x][keyToDelete.y] = i;
					break;
				}
				
				
				// -------------------- worst Time Complexity is O(m/2 + n/2) for center element ------- //
				
			}
		}
		
		
		
		
		
		
		
	}
	
	public Point findKey(int k, int[][] array, int m, int n) {
		int i = 0;
		int j = n - 1;
		while (i >= 0 && i < m && j >= 0 && j < n) {
           if (array[i][j] < k) {
        	   i++;
           } else if (array[i][j] > k) {
        	   j--;
           } else {
        	   break;
           }
		}

		return new Point(i, j);
	}
	
	private class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}

package gen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PrintSkyLine {

	public static void main(String[] args) {
		List<Rectangle> buildings = new ArrayList<Rectangle>();
		buildings.add(new Rectangle(0, 2, 6));
		buildings.add(new Rectangle(2, 1, 3));
		buildings.add(new Rectangle(4, 3, 9));
		buildings.add(new Rectangle(8, 4, 6));
		buildings.add(new Rectangle(10, 4, 2));


		
		int cur_x = 0;
		int cur_y = 0;
		int drawable_width = 0;
		int next_x = 0;
		for (Rectangle next : buildings) {
			next_x = next.getX();
			int next_y = next.getHeight();
			if (cur_x + drawable_width >= next_x) { // overlapping  buildings
				if (cur_y < next_y) { // next building is taller than current one so draw partially for current
					drawLine(cur_x, cur_y, next_x, cur_y);
					cur_x = next_x;
				} else {// current building is taller than next overlapping so draw ceiling of last  building completely
					drawLine(cur_x, cur_y, cur_x + drawable_width, cur_y);
					cur_x = cur_x + drawable_width;
				}

			}  else { // spaced buildings
				if (drawable_width != 0) {
					drawLine(cur_x, cur_y, cur_x + drawable_width, cur_y);// draw ceiling of last building
					cur_x = cur_x + drawable_width;
				}
				if (cur_y > 0) {
					drawLine(cur_x, cur_y, cur_x, 0);
					cur_y = 0;
				}
				drawLine(cur_x, cur_y, next_x, cur_y);// draw floor to new building
				cur_x = next_x;
			} 

			// move to y position
			if (next_y != cur_y) {
				drawLine(cur_x, cur_y, cur_x, next_y);
				cur_y = next_y;
			}

			
			drawable_width = next.getWidth();
		}
		
		drawLine(cur_x, cur_y, next_x + drawable_width, cur_y);// draw ceiling of last building
		cur_x = next_x + drawable_width;

		drawLine(cur_x, cur_y, cur_x, 0);
		
		/*drawLine(0, 4, 2, 4);
		drawLine(4, 4, 4, 6);
		drawLine(6, 6, 6, 2);
*/
	}
	
	private Point getNextPosition(int curX, int curY, List<Rectangle> availableList) {
		int nextX = -1;
		int nextY = -1;
		List<String> processed = new ArrayList<String>();
		int max_height = curY;
		for (Rectangle rect : availableList) {
			if (rect.getX() <= curX) {
				if (rect.getX() + rect.getWidth() >= curX) { // over lapping 
					if (max_height < rect.getHeight()) { // compare all heights
						max_height = rect.getHeight();
					}
				} else {
					//out of scope
				}
			} else {
				// non overlapping
				if (rect.getX() > curX  && (nextX == -1 || rect.getX() < nextX)) {
					nextX = rect.getX();
				}
			}
		}
		
		return new Point(nextX, nextY);
	}

	private static void drawLine(int x1, int y1, int x2, int y2) {
		if (x1== x2 && y1 == y2) {
			return;
		}
		
		
		if (y1 == y2) {
			System.out.println("["+ x1 + "," + y1 + "]" + "------->["+ x2 + "," + y2 + "]");
		} else if (x1 == x2) {
			if (y1 < y2) {
				System.out.println("["+ x1 + "," + y1 + "]" + "/|\\ ["+ x2 + "," + y2 + "]");
			} else if (y1 > y2) {
				System.out.println("["+ x1 + "," + y1 + "]" + "\\|/ ["+ x2 + "," + y2 + "]");
				System.out.println("--------------");
			}

		}
	}
	
	
	private static class Rectangle {
		int x;
		int width;
		int height;
		
		public Rectangle(int x, int width, int height) {
			this.x = x;
			this.width = width;
			this.height = height;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getX() {
			return x;
		}
		
	}
	
}

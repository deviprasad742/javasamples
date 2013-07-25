package codechef;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	private int N;
	private int M;
	private int K;
	private Node[][] nodes;
	boolean printNodes = true;

	private Main(Input input) {
		this.N = input.n;
		this.M = input.m;
		this.K = input.k;
		nodes = new Node[N][M];
	}

	public int compute() {
		if (N == 0 && M == 0) {
			return 0;
		} else if (N == 1 && M <= 2 || M == 1 && N <= 2) {
			return 0;
		} else if ( M == 1 || N == 1) {
			return K;
		} else if (K >= 1) {
			int maxStones = K / 2;
			return maxStones + K % 2;
		}

		for (int i = 0; i < K; i++) {
			Queue<Node> toSearch = new LinkedList<Node>();
			Node origin = getNode(0, 0);
			origin.cost = 0;
			toSearch.add(origin);
			while (!toSearch.isEmpty()) {
				Node current = toSearch.poll();
				int x = current.x;
				int y = current.y;
				if (x == N - 1 && y == M - 1) {
					continue;
				}
				List<Node> adjacentNodes = getAdjacentNodes(x, y);
				for (Node adjNode : adjacentNodes) {
					int newCost = current.cost + adjNode.stones;
					if (newCost < adjNode.cost) {
						adjNode.cost = newCost;
						toSearch.add(adjNode);
						adjNode.prev = current;
					}
				}
			}
			Node target = nodes[N - 1][M - 1];
			while (target != null) {
				target.stones++;
				if (printNodes) {
					System.out.print("[" + target.x + "," + target.y + "]");
				}
				target = target.prev;
				if (printNodes) {
					if (target == null) {
						System.out.println();
					} else {
						System.out.print("->");
					}
				}
			}

			resetNodes();
		}

		int maxStones = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				Node node = nodes[i][j];
				if (node.stones > maxStones) {
					maxStones = node.stones;
				}
			}
		}

		return maxStones;
	}

	private List<Node> getAdjacentNodes(int x, int y) {
		List<Node> adjacent = new ArrayList<Node>();
		checkAndAdd(x + 1, y, adjacent);
		checkAndAdd(x, y + 1, adjacent);
		return adjacent;
	}

	private void checkAndAdd(int x, int y, List<Node> adjacent) {
		Node node = getNode(x, y);
		if (node != null) {
			adjacent.add(node);
		}
	}

	private void resetNodes() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				Node node = nodes[i][j];
				node.cost = Integer.MAX_VALUE;
				node.prev = null;
			}
		}
		Node origin = nodes[0][0];
		origin.cost = 0;
		origin.stones = 0;
		Node target = nodes[N - 1][M - 1];
		target.stones = 0;

	}

	private Node getNode(int x, int y) {
		if (x > N - 1 || y > M - 1 || x < 0 || y < 0) {
			return null;
		}
		Node node = nodes[x][y];
		if (node == null) {
			node = new Node();
			node.x = x;
			node.y = y;
			node.stones = 0;
			node.cost = Integer.MAX_VALUE;
			nodes[x][y] = node;
		}
		return node;
	}

	public void setPrintNodes(boolean printNodes) {
		this.printNodes = printNodes;
	}

	private class Node {
		int x;
		int y;
		int stones;
		int cost;
		Node prev;
	}

	public static void main(String[] args) {
		boolean sampleInput = false;
		List<Input> inputList = new ArrayList<Input>();
		if (!sampleInput) {
			readInput(inputList);
		} else {
			inputList.add(new Input(3, 3, 4));
			inputList.add(new Input(10, 10, 26));
			inputList.add(new Input(1, 1, 70));
			inputList.add(new Input(1, 2, 70));
			inputList.add(new Input(2, 5, 12));

		}

		for (Input input : inputList) {
			Main stones = new Main(input);
			stones.setPrintNodes(sampleInput);
			System.out.println(stones.compute());
		}
	}

	private static void readInput(List<Input> inputList) {
		BufferedReader reader = null;
		try {
			reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			int testCases = Integer.parseInt(reader.readLine());
			for (int i = 0; i < testCases; i++) {
				String line = reader.readLine();
				String[] split = line.split(" ");
				inputList.add(new Input(getInt(split, 0), getInt(split, 1), getInt(split, 2)));
			}
		} catch (Exception e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					
				}
			}

		}
	}

	private static int getInt(String[] split, int index) {
		return Integer.valueOf(split[index]);
	}

	private static class Input {
		int n;
		int m;
		int k;

		private Input(int n, int m, int k) {
			this.n = n;
			this.m = m;
			this.k = k;
		}

	}

}

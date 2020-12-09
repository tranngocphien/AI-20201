package puzzle_test;


import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AStarSolver1 {
	private Stack<Board> solution = null;
	private SearchNode searchNode;
	public static int countOfNodesUsed = 0;
	public static Set<Integer> hashes;

	private static class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private int moves;
		private SearchNode previous;

		public SearchNode(Board board, SearchNode previous) {
			this.board = board;
			countOfNodesUsed++;
			if (previous != null) {
				this.moves = previous.moves + 1;
				this.previous = previous;
			}
		}

		// Check if the current Search node is better than the other node.
		// If negative, the current node is better.
		// If positive, then the other node is better
		// Change hurisitic here to try different ones
		@Override
		public int compareTo(SearchNode o) {
			return (this.board.getManhattanDistance() + this.moves) - (o.board.getManhattanDistance() + o.moves);
		}
	}

	public AStarSolver1(Board initial) {
		try {

			// Priority Queue to order the nodes according to the least sum of
			// moves and heuristic
			PriorityQueue<SearchNode> nodes = new PriorityQueue<>();

			// This stores the hash values of the nodes visited.This is to make
			// sure that we dont add already visited states
			hashes = new HashSet<>();

			// Insert the root node into the priority queue
			searchNode = new SearchNode(initial, null);
			nodes.add(searchNode);
			hashes.add(searchNode.board.toString().hashCode());

			while (true) {
				// Get the least node in the priority queue
				if (!nodes.isEmpty())
					searchNode = nodes.poll();
				else
					break;

				// Check if we have reached the goal state
				if (searchNode.board.isGoal())
					break;

				// Add all the neighbors into the priority queue
				for (Board neighbor : searchNode.board.getNeighbors()) {
					int hashCode = neighbor.toString().hashCode();
					if (!hashes.contains(hashCode)) {
						nodes.add(new SearchNode(neighbor, searchNode));
						hashes.add(hashCode);
					}
				}

			}
			// Updating the Solution Stack
			solution = new Stack<>();
			while (searchNode.previous != null) {
				solution.add(searchNode.board);
				searchNode = searchNode.previous;
			}
			System.out.println("Solved using A Star!");

		} catch (Exception ex) {
			throw ex;
		}
	}

	public Stack<Board> getSolution() {
		return solution;
	}
	
	public static void main(String[] args) {
        int[][] tiles = {{6,8,11,4},{9,15,14,3},{1,13,12,10},{0,5,7,2}}; //hard 2: 49 moves
        Board board = new Board(tiles);
        AStarSolver1 astarSolver = new AStarSolver1(board);
        System.out.println(astarSolver.solution.size());
        
	}
}

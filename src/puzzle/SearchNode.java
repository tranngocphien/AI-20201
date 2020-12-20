package puzzle;

import java.security.cert.CertPathChecker;

import puzzle_test.Board2;
import puzzle_test.SearchNode2;

public class SearchNode implements Comparable<SearchNode> {
	private Board board;
	private int g;
	private SearchNode parent;
	public int f;
	public int heuristic;
	
	public SearchNode(Board board, SearchNode parent) {
		this.board = board;
		this.parent = parent;
		if(parent != null) {
			this.g = parent.g + 1;
			this.f = parent.g + 1 + board.getManhattanDistance();
		}
		else {
			this.g = 0;
			this.f = board.getManhattanDistance();
		}
		this.heuristic = this.board.estimate(2);
		
	}
	
	public SearchNode(Board board, SearchNode parent, int x) {
		this.board = board;
		this.parent = parent;
		this.heuristic = this.board.estimate(x);
		if(parent != null) {
			this.g = parent.g + 1;
			this.f = parent.g + 1 + heuristic;
		}
		else {
			this.g = 0;
			this.f = heuristic + 1;
		}
	}
	
	

	@Override
	public int compareTo(SearchNode o) {
		// TODO Auto-generated method stub
		return (this.heuristic + this.g) - (o.heuristic + o.g);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public SearchNode getParent() {
		return this.parent;
	}
	
	public boolean check(SearchNode searchNode2) {
		return this.board.equals(searchNode2.getBoard());
	}
	
	public static void main(String[] args) {
		int tiles[] = {3,8,1,4,0,5,7,2,6};
        Board board2 = new Board(tiles, 3);
		SearchNode searchNode = new SearchNode(board2, null);
		System.out.println(searchNode.f);
		for(Board board : board2.getNeighbor()) {
			SearchNode searchNode2 = new SearchNode(board, searchNode);
			System.out.println(searchNode2.f);
		}
		
	}
	



}

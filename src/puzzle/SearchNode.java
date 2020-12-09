package puzzle;

import puzzle_test.Board2;
import puzzle_test.SearchNode2;

public class SearchNode implements Comparable<SearchNode> {
	private Board board;
	private int g;
	private SearchNode parent;
	
	public SearchNode(Board board, SearchNode parent) {
		this.board = board;
		this.parent = parent;
		if(parent != null) {
			this.g = parent.g + 1;
		}
		else {
			this.g = 0;
		}
	}
	

	@Override
	public int compareTo(SearchNode o) {
		// TODO Auto-generated method stub
		return (this.board.getManhattanDistance() + this.g) - (o.getBoard().getManhattanDistance() + o.g);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public SearchNode getParent() {
		return this.parent;
	}


}

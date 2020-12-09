package puzzle_test;

public class SearchNode2 implements Comparable<SearchNode2> {
	private Board2 board2;
	private int g;
	private SearchNode2 parent;
	
	public SearchNode2(Board2 board2, SearchNode2 parent) {
		this.board2 = board2;
		this.parent = parent;
		if( parent!= null) {
			this.g = parent.g + 1;
		}
		else {
			this.g = 0;
		}
	}

	@Override
	public int compareTo(SearchNode2 o) {
		return (this.board2.getHammingDistance() + this.g) - (o.board2.getHammingDistance() + o.g);
	}
	
	public Board2 getBoard2() {
		return this.board2;
	}
	
	public SearchNode2 getParent() {
		return this.parent;
	}
	
	

}

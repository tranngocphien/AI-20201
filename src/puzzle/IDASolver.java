package puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class IDASolver {
	public Stack<Board> solution = null;
	private SearchNode searchNode;
	private int cutoff;
	
	public int countNode = 0;
	public PriorityQueue<SearchNode> FRINGE = new PriorityQueue<SearchNode>();
	public PriorityQueue<SearchNode> VISIT = new PriorityQueue<SearchNode>();
	public Set<Integer> CLOSE ;
	
	public IDASolver(Board initBoard) {
		
		CLOSE = new HashSet<Integer>();
		searchNode = new SearchNode(initBoard, null);
		FRINGE.add(searchNode);
		CLOSE.add(searchNode.getBoard().toString().hashCode());
		cutoff = 0;
		boolean found = false;
		while(true) {
			while(!FRINGE.isEmpty()) {
				searchNode = FRINGE.poll();
				
				if(searchNode.getBoard().isGoal()) {
					found = true;
					break;
				}
				int hashCodeParent;
				if(searchNode.getParent() != null) {
					hashCodeParent = searchNode.getParent().toString().hashCode();
				}
				else {
					hashCodeParent = 0;
				}
					
				if(searchNode.f <= cutoff) {
					for( Board neighbor : searchNode.getBoard().getNeighbor()) {
						int hashCode = neighbor.toString().hashCode();
						if(hashCode != hashCodeParent) {
							FRINGE.add(new SearchNode(neighbor, searchNode));
						}

					}
				}
				else {
					VISIT.add(searchNode);
				}
				
			}
			if(found) {
				solution = new Stack<Board>();
				while(searchNode.getParent() != null) {
					solution.push(searchNode.getBoard());
					searchNode = searchNode.getParent();
				}
				System.out.println("Solved using A Star");
				break;
			}
			if(VISIT.size() == 0) {
				System.out.println("Can't solve");
				break;
			}
			
			cutoff = VISIT.element().f;
			while(!VISIT.isEmpty()) {
				FRINGE.add(VISIT.poll());
			}
			System.out.println(VISIT.size());
			
		}
		
	}
	
	public static void main(String[] args) {
//	        int[] tiles = {7,2,4,5,0,6,8,3,1};
	
      int[] tiles = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15}; 
		Board board = new Board(tiles, 4);
		IDASolver idaSolver = new IDASolver(board);
		for(Board board22 : idaSolver.solution) {
        	System.out.println(board22.toString());
        }	
		System.out.println(idaSolver.solution.size());
	}

}

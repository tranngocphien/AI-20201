package puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class AstarSolver {
	public Stack<Board> solution = null;
	private SearchNode searchNode;
	
	public static int countNode = 0;
	public static Set<Integer> CLOSE;
	
	public AstarSolver(Board initialBoard2) {
		try {
			PriorityQueue<SearchNode> FRINGE = new PriorityQueue<SearchNode>();
			
			CLOSE = new HashSet<>();
			
			searchNode = new SearchNode(initialBoard2, null);
			FRINGE.add(searchNode);
			CLOSE.add(searchNode.getBoard().toString().hashCode());
			while(true) {
				if(!FRINGE.isEmpty()) {
					searchNode = FRINGE.poll();
			//		break;
				}
				else {
					break;
				}
				
				if(searchNode.getBoard().isGoal()) {
					break;
				}
				
				for( Board neighbor : searchNode.getBoard().getNeighbor()) {
					int hashCode = neighbor.toString().hashCode();
					if(!CLOSE.contains(hashCode)) {
						FRINGE.add(new SearchNode(neighbor, searchNode));
						CLOSE.add(hashCode);
					}
				}
			}
			
			solution = new Stack<Board>();
			while(searchNode.getParent() != null) {
				solution.push(searchNode.getBoard());
				searchNode = searchNode.getParent();
			}
			System.out.println("Solved using A Star");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
    //    int[] tiles = {7,2,4,5,0,6,8,3,1};
       //int[] tiles = {1,2,0,3,4,5,6,7,8,9,10,11,12,13,14,15};
//       int tiles[] = {5,1,3,2,0,7,4,8,6};
//        int[] tiles = {4,1,2,3,8,5,6,7,12,9,10,11,0,13,14,15};
//       int[] tiles = {2,3,4,8,1,6,0,12,5,10,7,11,9,13,14,15}; //Easy: 13 moves
//        int[] tiles = {6,8,11,4,9,15,14,3,1,13,12,10,0,5,7,2}; //hard 2: 49 moves
//        int[] tiles = {5,4,3,8,9,2,6,1,0,13,14,7,15,11,10,12}; //hard 1: 38 moves
		int[] tiles = {0,7,5,8,3,4,2,1,6};



        Board board2 = new Board(tiles, 3);
        AstarSolver astarSolver = new AstarSolver(board2);
        System.out.println(astarSolver.solution.size());
        for(Board board22 : astarSolver.solution) {
        	System.out.println(board22.toString());
        }

	}

}

package puzzle_test;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AstarSolver {
	public Stack<Board2> solution = null;
	private SearchNode2 searchNode;
	
	public static int countNode = 0;
	public static Set<Integer> CLOSE;
	
	public AstarSolver(Board2 initialBoard2) {
		try {
			PriorityQueue<SearchNode2> FRINGE = new PriorityQueue<SearchNode2>();
			
			CLOSE = new HashSet<>();
			
			searchNode = new SearchNode2(initialBoard2, null);
			FRINGE.add(searchNode);
			CLOSE.add(searchNode.getBoard2().toString().hashCode());
			while(true) {
				if(!FRINGE.isEmpty()) {
					searchNode = FRINGE.poll();
			//		break;
				}
				else {
					break;
				}
				
				if(searchNode.getBoard2().isGoal()) {
					break;
				}
				
				for( Board2 neighbor : searchNode.getBoard2().getNeighbor()) {
					int hashCode = neighbor.toString().hashCode();
					if(!CLOSE.contains(hashCode)) {
						FRINGE.add(new SearchNode2(neighbor, searchNode));
						CLOSE.add(hashCode);
					}
				}
			}
			
			solution = new Stack<Board2>();
			while(searchNode.getParent() != null) {
				solution.add(searchNode.getBoard2());
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
       int tiles[] = {5,1,3,2,0,7,4,8,6};
//        int[] tiles = {4,1,2,3,8,5,6,7,12,9,10,11,0,13,14,15};
        Board2 board2 = new Board2(tiles, 3);
        AstarSolver astarSolver = new AstarSolver(board2);
        System.out.println(astarSolver.solution.size());
        for(Board2 board22 : astarSolver.solution) {
        	System.out.println(board22.toString());
        }

	}

}

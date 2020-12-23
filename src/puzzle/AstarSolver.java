package puzzle;

import java.net.Socket;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class AstarSolver {
	public Stack<Board> solution = null;
	private SearchNode searchNode;
	
	public int countNode = 0;
	public Set<Integer> CLOSE;
	public int x ;// hàm heuristics
	public String result;
	public long timeSolve;
	
	public AstarSolver(Board initialBoard2, int x) {
		this.x = x;
		try {
			long timeStart = System.currentTimeMillis();
			PriorityQueue<SearchNode> FRINGE = new PriorityQueue<SearchNode>();
			
			CLOSE = new HashSet<>();
			
			searchNode = new SearchNode(initialBoard2, null,x);
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
					long timeEnd = System.currentTimeMillis();
					this.timeSolve = timeEnd - timeStart;
					break;
				}

				for( Board neighbor : searchNode.getBoard().getNeighbor()) {
					int hashCode = neighbor.toString().hashCode();
					if(!CLOSE.contains(hashCode)) {
						FRINGE.add(new SearchNode(neighbor, searchNode,x));
						CLOSE.add(hashCode);
						countNode++;
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
//        int[] tiles = {7,2,4,5,0,6,8,3,1};
       //int[] tiles = {1,2,0,3,4,5,6,7,8,9,10,11,12,13,14,15};
//       int tiles[] = {5,1,3,2,0,7,4,8,6};
//        int[] tiles = {4,1,2,3,8,5,6,7,12,9,10,11,0,13,14,15};
//       int[] tiles = {2,3,4,8,1,6,0,12,5,10,7,11,9,13,14,15}; 
        int[] tiles = {6,8,11,4,9,15,14,3,1,13,12,10,0,5,7,2}; 
//        int[] tiles = {5,4,3,8,9,2,6,1,0,13,14,7,15,11,10,12}; 
//		int tiles[] = {3,8,1,4,0,5,7,2,6};

        Board board2 = new Board(tiles, 4);
        AstarSolver astarSolver = new AstarSolver(board2,3);
        System.out.println(astarSolver.solution.size());
        System.out.println(astarSolver.countNode);
        System.out.println(astarSolver.timeSolve);
        System.out.println(astarSolver.solution.size());

	}

}

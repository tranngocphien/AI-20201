package puzzle_test;

import java.util.Vector;

public class Board2 {
	private int[] board;
	private int size;

	public Board2(int[] b, int size) {
		board = new int[b.length];
		for (int i = 0; i < b.length; i++) {
			this.board[i] = b[i];
		}
		this.size = size;
		

		
	}

	public int getManhattanDistance() {	//khoảng cách ngắn nhất để đi đúng vị trí
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			int value = board[i];
			if (value != i) {
				int x = Math.abs(value / size - i / size);
				int y = Math.abs(value % size - i % size);
				distance = distance + x + y;
			}
		}
		return distance;
	}

	public int getHammingDistance() {	//số vị trí bị sai
		int distance = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] != i) {
				distance++;
			}
		}
		return distance;
	}

	public int estimate(int x) {
		if (x == 1) {
			return getHammingDistance();	// số điểm sai vị trí
		} else
			return getManhattanDistance();	//khoảng cách ngắn nhất để đi đúng vị trí
	}
	
	public boolean isGoal() {	//trả về game over hay continue
		if(this.getHammingDistance() == 0) {
			return true;
		}
		else return false;
	}
	
	public Vector<Board2> getNeighbor(){	//hàng xóm
		Vector<Board2> listNeighbor = new Vector<Board2>();
		// Tìm vị trí phần tử có giá trị bằng 0
		int index_0 = 0;
		for(int i = 0; i < this.board.length; i++) {
			if(board[i] == 0) {
				index_0 = i;
				break;
			}
		}
		
		// Sinh ra các vị trí kế tiếp có thể đi
		// UP: cho rỗng lên trên
		if (index_0 / size > 0) {	
			Board2 board2 = new Board2(this.board, this.size);
			int temp = this.board[index_0 - size];
			board2.board[index_0] = temp;
			board2.board[index_0 - size] = 0;
			listNeighbor.add(board2);
		}
		// RIGHT: cho rỗng sang phải
		if (index_0 % size < size - 1) {
			Board2 board2 = new Board2(this.board, this.size);
			int temp = this.board[index_0 + 1];
			board2.board[index_0] = temp;
			board2.board[index_0 + 1] = 0;
			listNeighbor.add(board2);

		}
		// DOWN: cho rỗng xuống dưới
		if (index_0 / size < size - 1) {
			Board2 board2 = new Board2(this.board, this.size);
			int temp = this.board[index_0 + size];
			board2.board[index_0] = temp;
			board2.board[index_0 + size] = 0;
			listNeighbor.add(board2);
		}
		
		// LEFT: cho rỗng sang trái
		if (index_0 % size > 0) {
			Board2 board2 = new Board2(this.board, this.size);
			int temp = this.board[index_0 - 1];
			board2.board[index_0] = temp;
			board2.board[index_0 - 1] = 0;
			listNeighbor.add(board2);
		}
		return listNeighbor;
		
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.board.length; i++) {
			sb.append(this.board[i]);
		}
		return sb.toString();
	}
	public int[] getArray() {
		return this.board;
	}
	
	public static void main(String[] args) {
        int[] tiles = {7,3,4,5,0,6,8,2,1};
        Board2 board2 = new Board2(tiles, 3);
        int a = board2.getManhattanDistance();
        for(Board2 board22 : board2.getNeighbor()) {
        	System.out.println(board22.getManhattanDistance());
        }
        System.out.println(a);

	}

}

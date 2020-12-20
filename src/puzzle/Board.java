package puzzle;

import java.util.Vector;

public class Board {
	private int[] board;
	private int size;
	public final int[] goal;

	public Board(int[] b, int size) {
		board = new int[b.length];
		for (int i = 0; i < b.length; i++) {
			this.board[i] = b[i];
		}
		this.size = size;

		goal = new int[b.length];
		for (int i = 0; i < b.length - 1; i++) {
			goal[i] = i + 1;
		}
		goal[b.length - 1] = 0;
	}

	public boolean equals(Board b2) {
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] != b2.board[i]) {
				return false;
			}
		}
		return true;
	}

	// Kiem tra co the giai duoc khong
	public boolean isSolve() {
		int index_blank = getBlank();
		int row = index_blank / size + 1;
		int count = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = i + 1; j < board.length; j++) {
				if (board[j] < board[i] && board[j] != 0) {
					count++;
				}
			}
		}
		System.out.println(count);

		if (size % 2 == 0) {
			if (row % 2 == 0) {
				return count % 2 == 0;
			} else {
				return count % 2 == 1;
			}
		} else {
			return count % 2 == 0;
		}
//		return true;

	}

	public int getHammingDistance() {
		int distance = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] != goal[i]) {
				distance++;
			}
		}
		return distance;

	}

	public int getManhattanDistance() { // khoảng cách ngắn nhất để đi đúng vị trí
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			int value = board[i] - 1;
			if (value != i) {
				if (value == -1) {
					value = -1 + size * size;
				}
				int x = Math.abs(value / size - i / size);
				int y = Math.abs(value % size - i % size);
				distance = distance + x + y;
			}
		}
		return distance;
	}

	/*
	 * public int getHammingDistance() { //số vị trí bị sai int distance = 0; for
	 * (int i = 0; i < this.board.length; i++) { if (this.board[i] != i) {
	 * distance++; } } return distance; }
	 */
	public int getDistance() {
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			int value = board[i] - 1;
			if (value != i) {
				if (value == -1) {
					value = -1 + size * size;
				}
				int x = Math.abs((value / size) * (value / size) - (i / size) * (i / size));
				int y = Math.abs((value % size) * (value % size) - (i % size) * (i % size));
				distance = distance + (int) Math.sqrt(x + 1) + (int) Math.sqrt(y + 1);
			}
		}
		return distance;

	}

	public int getDistance2() {
		int a1 = this.getHammingDistance();
		int a2 = this.getManhattanDistance();
		if (a2 > a1) {
			return a2;
		} else {
			return a1;
		}

	}
	
	public int getDistance3() {
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			int value = board[i] - 1;
			if (value != i) {
				if (value == -1) {
					value = -1 + size * size;
				}
				int x = ((value / size)  - (i / size))*((value / size)  - (i / size));
				int y = ((value % size)  - (i % size))*((value % size)  - (i % size));
				distance = distance + (int) Math.sqrt(x + y);
			}
		}
		return distance;
		
	}

	public int getManhattanLinearConfict() {
		int distance = 0;
		for (int i = 0; i < board.length; i++) {
			int value = board[i] - 1;
			if (value != i) {
				if (value == -1) {
					value = -1 + size * size;
				}
				int x = Math.abs(value / size - i / size);
				int y = Math.abs(value % size - i % size);
				distance = distance + x + y;
			}
			if( i % size != size - 1)
			if(board[i + 1] != 0 && board[i +1] == board[i] - 1) {
				distance += 2;
			}
			if(i < board.length - size)
			if(board[i + size]!= 0 && board[i + size] == board[i] - size ) {
				distance += 2;
			}

		}
		
		return distance;
	}

	public int estimate(int x) {
		if (x == 1) {
			return getHammingDistance();
		} else if (x == 2) {
			return getManhattanDistance();
		} else if (x == 3) {
			return getManhattanLinearConfict();
		} else if (x == 4) {
			return getDistance();
		} else if(x == 5){
			return getDistance2();
		}
		else {
			return getDistance3();
		}
	}

	public boolean isGoal() {
		if (this.getHammingDistance() == 0) {
			return true;
		} else
			return false;
	}

	public int getBlank() {
		int index_0 = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0) {
				index_0 = i;
				break;
			}
		}
		return index_0;
	}

	public Vector<Board> getNeighbor() { // hàng xóm
		Vector<Board> listNeighbor = new Vector<Board>();
		// Tìm vị trí phần tử có giá trị bằng 0
		int index_0 = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (board[i] == 0) {
				index_0 = i;
				break;
			}
		}

		// Sinh ra các vị trí kế tiếp có thể đi
		// UP: cho rỗng lên trên
		if (index_0 / size > 0) {
			Board board2 = new Board(this.board, this.size);
			int temp = this.board[index_0 - size];
			board2.board[index_0] = temp;
			board2.board[index_0 - size] = 0;
			listNeighbor.add(board2);
		}
		// RIGHT: cho rỗng sang phải
		if (index_0 % size < size - 1) {
			Board board2 = new Board(this.board, this.size);
			int temp = this.board[index_0 + 1];
			board2.board[index_0] = temp;
			board2.board[index_0 + 1] = 0;
			listNeighbor.add(board2);

		}
		// DOWN: cho rỗng xuống dưới
		if (index_0 / size < size - 1) {
			Board board2 = new Board(this.board, this.size);
			int temp = this.board[index_0 + size];
			board2.board[index_0] = temp;
			board2.board[index_0 + size] = 0;
			listNeighbor.add(board2);
		}

		// LEFT: cho rỗng sang trái
		if (index_0 % size > 0) {
			Board board2 = new Board(this.board, this.size);
			int temp = this.board[index_0 - 1];
			board2.board[index_0] = temp;
			board2.board[index_0 - 1] = 0;
			listNeighbor.add(board2);
		}
		return listNeighbor;

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.board.length; i++) {
			sb.append(this.board[i]);
		}
		return sb.toString();
	}

	public int[] getArray() {
		return this.board;
	}

	public static void main(String[] args) {
		// int[] tiles = {6,8,11,4,9,15,14,3,1,13,12,10,0,5,7,2}; //hard 2: 49 moves
//		int tiles[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,15,14};
		//int tiles[] = {3,9,1,15,14,11,4,6,13,0,10,12,2,7,8,5};
		int tiles[] = {6,13,7,10,8,9,11,0,15,2,12,5,14,3,1,4};
		int tiles2[] = { 3, 8, 1, 4, 0, 7, 5, 2, 6 };

		Board board = new Board(tiles, 4);

		for (int i = 0; i < tiles.length; i++) {
			System.out.print(board.goal[i] + " ");
		}
		if(board.isSolve()) {
			System.out.println("CAN SOLVE");
		}
		System.out.println();
		System.out.println(board.getManhattanDistance());
		System.out.println("aa");
		System.out.println(board.getManhattanLinearConfict());
	}

}

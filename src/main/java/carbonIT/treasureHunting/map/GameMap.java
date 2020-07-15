package carbonIT.treasureHunting.map;

import java.util.Arrays;

import carbonIT.treasureHunting.cells.Cell;

public class GameMap {

    private int length;
    private int height;
    private Cell[][] board;

    public GameMap() {
    }

    public GameMap(int length, int height) {
        this.length = length;
        this.height = height;
        this.board = new Cell[length][height];
    }

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Cell[][] getBoard() {
		return board;
	}

	public void setBoard(Cell[][] board) {
		this.board = board;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + height;
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameMap other = (GameMap) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameMap [length=" + length + ", height=" + height + ", board=" + Arrays.toString(board) + "]";
	}
	
	
	

    
}

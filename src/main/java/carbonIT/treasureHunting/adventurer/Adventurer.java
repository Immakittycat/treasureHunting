package carbonIT.treasureHunting.adventurer;

import java.util.ArrayList;
import java.util.List;

import carbonIT.treasureHunting.cells.Cell;
import carbonIT.treasureHunting.movement.TypeMovement;
import carbonIT.treasureHunting.orientation.Orientation;

public class Adventurer {
	
	private Cell positionOnBoard;

	private Orientation orientation;

	private String name;

	private String movementsAsString;

	private List<TypeMovement> movements;

	private int priorityInFile;

	private int treasuresCollected;

	private boolean changedCellDuringTurn;

	public Adventurer() {
		this.treasuresCollected = 0;
		this.movements = new ArrayList<TypeMovement>();
	}

	public Adventurer(Orientation orientation, String name, String movementsAsString, int priorityInFile) {
		this.treasuresCollected = 0;
		this.orientation = orientation;
		this.name = name;
		this.priorityInFile = priorityInFile;
		this.movements = new ArrayList<TypeMovement>();
		this.setMovements(movementsAsString);
	}
	

	public Cell getPositionOnBoard() {
		return positionOnBoard;
	}

	public void setPositionOnBoard(Cell positionOnBoard) {
		this.positionOnBoard = positionOnBoard;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMovementsAsString() {
		return movementsAsString;
	}

	public void setMovementsAsString(String movementsAsString) {
		this.movementsAsString = movementsAsString;
	}

	public List<TypeMovement> getMovements() {
		return movements;
	}

	public void setMovements(List<TypeMovement> movements) {
		this.movements = movements;
	}

	public void setMovements(String movementsAsString) {
		this.movementsAsString = movementsAsString;
		for (int i = 0; i < this.movementsAsString.length(); i++) {
			this.movements.add(TypeMovement.valueOf("" + this.movementsAsString.charAt(i)));
		}
	}

	public int getPriorityInFile() {
		return priorityInFile;
	}

	public void setPriorityInFile(int priorityInFile) {
		this.priorityInFile = priorityInFile;
	}

	public int getTreasuresCollected() {
		return treasuresCollected;
	}

	public void setTreasuresCollected(int treasuresCollected) {
		this.treasuresCollected = treasuresCollected;
	}

	public boolean isChangedCellDuringTurn() {
		return changedCellDuringTurn;
	}

	public void setChangedCellDuringTurn(boolean changedCellDuringTurn) {
		this.changedCellDuringTurn = changedCellDuringTurn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changedCellDuringTurn ? 1231 : 1237);
		result = prime * result + ((movements == null) ? 0 : movements.hashCode());
		result = prime * result + ((movementsAsString == null) ? 0 : movementsAsString.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + ((positionOnBoard == null) ? 0 : positionOnBoard.hashCode());
		result = prime * result + priorityInFile;
		result = prime * result + treasuresCollected;
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
		Adventurer other = (Adventurer) obj;
		if (changedCellDuringTurn != other.changedCellDuringTurn)
			return false;
		if (movements == null) {
			if (other.movements != null)
				return false;
		} else if (!movements.equals(other.movements))
			return false;
		if (movementsAsString == null) {
			if (other.movementsAsString != null)
				return false;
		} else if (!movementsAsString.equals(other.movementsAsString))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orientation != other.orientation)
			return false;
		if (positionOnBoard == null) {
			if (other.positionOnBoard != null)
				return false;
		} else if (!positionOnBoard.equals(other.positionOnBoard))
			return false;
		if (priorityInFile != other.priorityInFile)
			return false;
		if (treasuresCollected != other.treasuresCollected)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adventurer [positionOnBoard=" + positionOnBoard + ", orientation=" + orientation + ", name=" + name
				+ ", movementsAsString=" + movementsAsString + ", movements=" + movements + ", priorityInFile="
				+ priorityInFile + ", treasuresCollected=" + treasuresCollected + ", changedCellDuringTurn="
				+ changedCellDuringTurn + "]";
	}


}

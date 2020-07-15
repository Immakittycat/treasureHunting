package carbonIT.treasureHunting.cells;

import carbonIT.treasureHunting.coordinates.Coordinates;

public abstract class Cell {
	
	protected Coordinates coords;
	
	protected boolean walkable;
	
	protected boolean hasAdventurer;

	public Coordinates getCoords() {
		return coords;
	}

	public void setCoords(Coordinates coords) {
		this.coords = coords;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public boolean isHasAdventurer() {
		return hasAdventurer;
	}

	public void setHasAdventurer(boolean hasAdventurer) {
		this.hasAdventurer = hasAdventurer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
		result = prime * result + (hasAdventurer ? 1231 : 1237);
		result = prime * result + (walkable ? 1231 : 1237);
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
		Cell other = (Cell) obj;
		if (coords == null) {
			if (other.coords != null)
				return false;
		} else if (!coords.equals(other.coords))
			return false;
		if (hasAdventurer != other.hasAdventurer)
			return false;
		if (walkable != other.walkable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [coords=" + coords + ", walkable=" + walkable + ", hasAdventurer=" + hasAdventurer + "]";
	}
	
	

}

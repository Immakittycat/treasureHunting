package carbonIT.treasureHunting.cells;

public class Treasure extends Cell {
	
	private int remainingLoots;
	
	public Treasure() {
		super();
		this.setWalkable(true);
	}
	
	public Treasure(int remainingLoots) {
		super();
		this.setWalkable(true);
		this.remainingLoots = remainingLoots;
	}

	public int getRemainingLoots() {
		return remainingLoots;
	}

	public void setRemainingLoots(int remainingLoots) {
		this.remainingLoots = remainingLoots;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + remainingLoots;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Treasure other = (Treasure) obj;
		if (remainingLoots != other.remainingLoots)
			return false;
		return true;
	}
	
	

}

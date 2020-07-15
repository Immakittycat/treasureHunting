package carbonIT.treasureHunting.movement;

import carbonIT.treasureHunting.adventurer.Adventurer;

public class Movement {
	
	private Adventurer adventurer;
	
	private TypeMovement typeMovement;
	
	public Movement() {
	}
	public Movement(Adventurer adventurer, TypeMovement typeMovement) {
		this.adventurer = adventurer;
		this.typeMovement = typeMovement;
	}

	public Adventurer getAdventurer() {
		return adventurer;
	}

	public void setAdventurer(Adventurer adventurer) {
		this.adventurer = adventurer;
	}

	public TypeMovement getTypeMovement() {
		return typeMovement;
	}

	public void setTypeMovement(TypeMovement typeMovement) {
		this.typeMovement = typeMovement;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adventurer == null) ? 0 : adventurer.hashCode());
		result = prime * result + ((typeMovement == null) ? 0 : typeMovement.hashCode());
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
		Movement other = (Movement) obj;
		if (adventurer == null) {
			if (other.adventurer != null)
				return false;
		} else if (!adventurer.equals(other.adventurer))
			return false;
		if (typeMovement != other.typeMovement)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Movement [adventurer=" + adventurer.getName() + ", typeMovement=" + typeMovement + "]";
	}
	

}

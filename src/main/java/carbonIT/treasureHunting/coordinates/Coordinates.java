package carbonIT.treasureHunting.coordinates;

public class Coordinates {

    private int positionXAxis;
    private int positionYAxis;

    public Coordinates() {
    }

    public Coordinates(int positionXAxis, int positionYAxis) {
        this.positionXAxis = positionXAxis;
        this.positionYAxis = positionYAxis;
    }

	public int getpositionXAxis() {
		return positionXAxis;
	}

	public void setpositionXAxis(int positionXAxis) {
		this.positionXAxis = positionXAxis;
	}

	public int getpositionYAxis() {
		return positionYAxis;
	}

	public void setpositionYAxis(int positionYAxis) {
		this.positionYAxis = positionYAxis;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + positionXAxis;
		result = prime * result + positionYAxis;
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
		Coordinates other = (Coordinates) obj;
		if (positionXAxis != other.positionXAxis)
			return false;
		if (positionYAxis != other.positionYAxis)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinates [positionXAxis=" + positionXAxis + ", positionYAxis=" + positionYAxis + "]";
	}

}

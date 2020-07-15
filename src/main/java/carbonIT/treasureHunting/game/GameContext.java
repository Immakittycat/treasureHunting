package carbonIT.treasureHunting.game;

import java.util.ArrayList;
import java.util.List;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.cells.Mountain;
import carbonIT.treasureHunting.cells.Treasure;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.movement.Movement;

public class GameContext {
	
	private GameMap map;
	
	private List<Mountain> mountains;
	private List<Treasure> treasures;
	private List<Adventurer> adventurers;
	
	private List<Movement> movements;
	
	public GameContext() {
		this.mountains = new ArrayList<Mountain>();
		this.treasures = new ArrayList<Treasure>();
		this.adventurers = new ArrayList<Adventurer>();
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}
	

	public List<Mountain> getMountains() {
		return mountains;
	}

	public void setMountains(List<Mountain> moutains) {
		this.mountains = moutains;
	}

	public List<Treasure> getTreasures() {
		return treasures;
	}

	public void setTreasures(List<Treasure> treasures) {
		this.treasures = treasures;
	}

	public List<Adventurer> getAdventurers() {
		return adventurers;
	}

	public void setAdventurers(List<Adventurer> adventurers) {
		this.adventurers = adventurers;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}
	
	
}

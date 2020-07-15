package carbonIT.treasureHunting.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.Mountain;
import carbonIT.treasureHunting.cells.Treasure;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.file.service.FileParser;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.orientation.Orientation;

public class FileParserTest {
	
	private final String pathToResources = "src/test/resources/";
	
	@Test
    public void should_process_line_map() {
		
		FileParser fp = new FileParser();
		GameContext gameContext = fp.readFile(pathToResources + "testIn.txt");
		
		List<Mountain> mountainsList = new ArrayList<Mountain>();
		Mountain mountain1 = new Mountain();
		mountain1.setCoords(new Coordinates(1, 0));
		mountainsList.add(mountain1);
		Mountain mountain2 = new Mountain();
		mountain2.setCoords(new Coordinates(2, 1));
		mountainsList.add(mountain2);
		
		List<Treasure> treasuresList = new ArrayList<Treasure>();
		Treasure treasure1 = new Treasure();
		treasure1.setCoords(new Coordinates(0, 3));
		treasure1.setRemainingLoots(2);
		treasuresList.add(treasure1);
		Treasure treasure2 = new Treasure();
		treasure2.setCoords(new Coordinates(1, 3));
		treasure2.setRemainingLoots(3);
		treasuresList.add(treasure2);
		
		List<Adventurer> adventurersList = new ArrayList<Adventurer>();
		Adventurer adventurer1 = new Adventurer(Orientation.valueOf("S"), "Lara", "AADADAGGA", 5);
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(1, 1));
		adventurer1.setPositionOnBoard(positionAdventurer);
		adventurersList.add(adventurer1);
		
		assertEquals(gameContext.getMap().getLength(), 3);
		assertEquals(gameContext.getMap().getHeight(), 4);
		assertEquals(gameContext.getMountains(), mountainsList);
		assertEquals(gameContext.getTreasures(), treasuresList);
		assertEquals(gameContext.getAdventurers(), adventurersList);
	}

}

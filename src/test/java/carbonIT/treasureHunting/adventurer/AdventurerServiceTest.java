package carbonIT.treasureHunting.adventurer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.adventurer.service.AdventurerService;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.Treasure;
import carbonIT.treasureHunting.cells.service.TreasureService;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.AdventurerException;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;
import carbonIT.treasureHunting.orientation.Orientation;

public class AdventurerServiceTest {
	
	@Test
    public void should_create_adventurer() throws LineException, AdventurerException {
		String line = "A - Toto - 1 - 2 - S - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		GameContext gc = new GameContext();
		gc.setMap(map);
		
		Adventurer expectedAdventurer = new Adventurer(Orientation.valueOf("S"), "Toto", "AAGADA", 0);
		expectedAdventurer.setPositionOnBoard(map.getBoard()[1][2]);
		Adventurer outPutAdventurer = AdventurerService.createAdventurer(lineParts, 0, gc);
		
        assertEquals(expectedAdventurer, outPutAdventurer);
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_nb_args() {
		String line = "A - Toto - 1 - 2 - S - AAGADA - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> AdventurerService.createAdventurer(lineParts, 0, new GameContext()));
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_value_1() {
		String line = "W - Toto - 1 - 2 - S - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> AdventurerService.createAdventurer(lineParts, 0, new GameContext()));
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_value_3() {
		String line = "A - Toto - RR - 2 - S - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> AdventurerService.createAdventurer(lineParts, 0, new GameContext()));
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_value_4() {
		String line = "A - Toto - 1 - LP - S - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> AdventurerService.createAdventurer(lineParts, 0, new GameContext()));
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_value_5() {
		String line = "A - Toto - 1 - 2 - U - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		GameMap map = new GameMap(4, 4);
		MapService.fillMapWithEmptyCells(map);
		GameContext gc = new GameContext();
		gc.setMap(map);
		
		assertThrows(IllegalArgumentException.class, () -> AdventurerService.createAdventurer(lineParts, 0, gc));
    }
	
	@Test
    public void should_not_create_adventurer_incorrect_line_value_6() {
		String line = "A - Toto - 1 - 2 - S - 333";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		GameMap map = new GameMap(4, 4);
		MapService.fillMapWithEmptyCells(map);
		GameContext gc = new GameContext();
		gc.setMap(map);
		
		assertThrows(IllegalArgumentException.class, () -> AdventurerService.createAdventurer(lineParts, 0, gc));
    }
	
	@Test
    public void should_not_create_adventurer_twice() throws LineException, AdventurerException {
		String line = "A - Toto - 1 - 2 - S - AAGADA";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		Adventurer previousAdventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurers.add(previousAdventurer);
		
		GameContext gc = new GameContext();
		gc.setAdventurers(adventurers);
		
        assertThrows(AdventurerException.class, () -> AdventurerService.createAdventurer(lineParts, 0, gc));
    }
	
	@Test
    public void should_return_name_available() {
		String adventurerName = "Tutu";
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		Adventurer previousAdventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurers.add(previousAdventurer);
		
        assertTrue(AdventurerService.checkAdventurerNameAvailability(adventurerName, adventurers));
    }
	
	@Test
    public void should_return_name_unavailable() {
		String adventurerName = "Toto";
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		Adventurer previousAdventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurers.add(previousAdventurer);
		
        assertFalse(AdventurerService.checkAdventurerNameAvailability(adventurerName, adventurers));
    }
	
	@Test
    public void should_add_adventurer() throws OutOfMapException, MapException {
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurer.setPositionOnBoard(map.getBoard()[2][2]);

		AdventurerService.addAdventurer(map, adventurer);
		
        assertTrue(map.getBoard()[2][2].isHasAdventurer());
    }
	
	@Test
    public void should_not_add_adventurer_out_of_bound() {		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		EmptyCell outOfBoundCell = new EmptyCell();
		outOfBoundCell.setCoords(new Coordinates(4, 4));
		adventurer.setPositionOnBoard(outOfBoundCell);
		
		assertThrows(OutOfMapException.class, () -> AdventurerService.addAdventurer(map, adventurer));
    }
	
	@Test
    public void should_loot_treasure() throws OutOfMapException, MapException {
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2,2));
		treasure.setRemainingLoots(3);
		TreasureService.addTreasure(map, treasure);
		
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurer.setPositionOnBoard(map.getBoard()[2][2]);
		adventurer.setTreasuresCollected(0);
			
		AdventurerService.lootCell(map, adventurer);
		
        assertEquals(adventurer.getTreasuresCollected(), 1);
    }
	
	@Test
    public void should_loot_empty_cell() throws OutOfMapException, MapException {
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);

		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2,2));
		treasure.setRemainingLoots(0);
		TreasureService.addTreasure(map, treasure);
		
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		adventurer.setPositionOnBoard(map.getBoard()[2][2]);
		adventurer.setTreasuresCollected(1);
		
		AdventurerService.lootCell(map, adventurer);
		
        assertEquals(adventurer.getTreasuresCollected(), 1);
    }
	
	@Test
    public void should_get_adventurer_line() {
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(2, 3));
		adventurer.setPositionOnBoard(positionAdventurer);
		adventurer.setTreasuresCollected(1);
		adventurers.add(adventurer);
		
		GameContext gc = new GameContext();
		gc.setAdventurers(adventurers);
		
		String expectedLine = "A - Toto - 2 - 3 - N - 1" + System.lineSeparator();
		
        assertEquals(expectedLine, AdventurerService.getAdventurerLines(gc));
    }

}

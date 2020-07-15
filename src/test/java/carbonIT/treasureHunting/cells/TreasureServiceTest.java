package carbonIT.treasureHunting.cells;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.cells.service.TreasureService;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;

public class TreasureServiceTest {
	
	@Test
    public void should_create_treasure() throws LineException {
		String line = "T - 1 - 3 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		Treasure expectedTreasure = new Treasure(1);
		expectedTreasure.setCoords(new Coordinates(1, 3));
		Treasure outPutTreasure = TreasureService.createTreasure(lineParts);
		
        assertEquals(expectedTreasure, outPutTreasure);
    }
	
	@Test
    public void should_not_create_treasure_incorrect_line_nb_args() {
		String line = "T - 1 - 1 - 1 - S";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> TreasureService.createTreasure(lineParts));
    }
	
	@Test
    public void should_not_create_treasure_incorrect_line_value_1() {
		String line = "J - 1 - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> TreasureService.createTreasure(lineParts));
    }
	
	@Test
    public void should_not_create_treasure_incorrect_line_value_2() {
		String line = "T - Lala - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> TreasureService.createTreasure(lineParts));
    }
	
	@Test
    public void should_not_create_treasure_incorrect_line_value_3() {
		String line = "T - 2 - Z - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> TreasureService.createTreasure(lineParts));
    }
	
	@Test
    public void should_not_create_treasure_incorrect_line_value_4() {
		String line = "T - 2 - 1 - S";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> TreasureService.createTreasure(lineParts));
    }
	
	@Test
    public void should_add_treasure() throws OutOfMapException, MapException {
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2, 2));
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		TreasureService.addTreasure(map, treasure);
		
        assertTrue(map.getBoard()[2][2] instanceof Treasure);
    }
	
	@Test
    public void should_not_add_treasure_out_of_bound() {
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(4, 4));
		
		GameMap map = new GameMap(3, 3);
		
		assertThrows(OutOfMapException.class, () -> TreasureService.addTreasure(map, treasure));
    }
	
	@Test
    public void should_not_add_treasure_cell_used() {
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2, 2));
		
		GameMap map = new GameMap(3, 3);
		map.getBoard()[2][2] = new Treasure();
		
		assertThrows(MapException.class, () -> TreasureService.addTreasure(map, treasure));
    }
	
	@Test
    public void should_get_treasure_line() {
		List<Treasure> treasures = new ArrayList<Treasure>();
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2, 3));
		treasure.setRemainingLoots(1);
		treasures.add(treasure);
		
		GameContext gc = new GameContext();
		gc.setTreasures(treasures);
		
		String expectedLine = "T - 2 - 3 - 1" + System.lineSeparator();
		
        assertEquals(expectedLine, TreasureService.getTreasureLines(gc));
    }
	
	@Test
    public void should_not_get_treasure_line_no_more_loots() {
		List<Treasure> treasures = new ArrayList<Treasure>();
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2, 3));
		treasure.setRemainingLoots(0);
		treasures.add(treasure);
		
		GameContext gc = new GameContext();
		gc.setTreasures(treasures);
		
		String expectedLine = "";
		
        assertEquals(expectedLine, TreasureService.getTreasureLines(gc));
    }

}

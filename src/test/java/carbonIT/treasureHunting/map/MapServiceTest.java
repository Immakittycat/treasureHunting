package carbonIT.treasureHunting.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.Mountain;
import carbonIT.treasureHunting.cells.Treasure;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.service.MapService;

public class MapServiceTest {
	
	@Test
    public void should_fill_map_with_empty_cells() {
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		
		for (int i = 0; i < map.getLength(); i++) {
			for (int j = 0; j < map.getHeight(); j++) {
				assertTrue(map.getBoard()[i][j] instanceof EmptyCell);
			}
		}
    }
	
	@Test
    public void should_create_map() throws LineException {
		String line = "C - 2 - 2";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		GameMap expectedMap = new GameMap(2, 2);
		MapService.fillMapWithEmptyCells(expectedMap);
		GameMap outPutMap = MapService.createMap(lineParts);
		
        assertEquals(expectedMap, outPutMap);
    }
	
	@Test
    public void should_not_create_map_incorrect_line_nb_args() {
		String line = "C - 1 - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> MapService.createMap(lineParts));
    }
	
	@Test
    public void should_not_create_map_incorrect_line_value_1() {
		String line = "J - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> MapService.createMap(lineParts));
    }
	
	@Test
    public void should_not_create_map_incorrect_line_value_2() {
		String line = "C - Lala - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> MapService.createMap(lineParts));
    }
	
	@Test
    public void should_not_create_map_incorrect_line_value_3() {
		String line = "C - 2 - Z";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> MapService.createMap(lineParts));
    }
	
	@Test
    public void should_fill_map_with_each_type() {
		GameMap map = new GameMap(3, 3);
		GameContext gc = new GameContext();
		
		Adventurer adventurer = new Adventurer();
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(0, 1));
		adventurer.setPositionOnBoard(positionAdventurer);
		gc.getAdventurers().add(adventurer);
		
		Mountain mountain = new Mountain();
		mountain.setCoords(new Coordinates(1, 2));
		gc.getMountains().add(mountain);
		
		Treasure treasure = new Treasure();
		treasure.setCoords(new Coordinates(2, 2));
		gc.getTreasures().add(treasure);
		
		MapService.fillMapWithEmptyCells(map);
		gc.setMap(map);
		MapService.fillMap(gc);
		
		assertTrue(map.getBoard()[0][1].isHasAdventurer());
		assertTrue(map.getBoard()[1][2] instanceof Mountain);
		assertTrue(map.getBoard()[2][2] instanceof Treasure);

    }
	
	@Test
    public void should_return_cell_available() {
		Coordinates coords = new Coordinates(2, 2);
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		
		assertTrue(MapService.checkCellAvailable(map.getBoard()[coords.getpositionXAxis()][coords.getpositionYAxis()]));
	}
	
	@Test
    public void should_return_cell_unavailable() {
		Coordinates coords = new Coordinates(2, 2);
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		map.getBoard()[2][2] = new Mountain();
		
		assertFalse(MapService.checkCellAvailable(map.getBoard()[coords.getpositionXAxis()][coords.getpositionYAxis()]));
	}
	
	@Test
    public void should_return_coords_inbound() {
		Coordinates coords = new Coordinates(2, 2);
		GameMap map = new GameMap(3, 3);
		
		assertTrue(MapService.checkCoordinates(coords, map));
	}
	
	@Test
    public void should_return_coords_out_of_bound() {
		Coordinates coords = new Coordinates(3, 2);
		GameMap map = new GameMap(3, 3);
		
		assertFalse(MapService.checkCoordinates(coords, map));
	}

	@Test
    public void should_return_coords_walkable() {
		Coordinates coords = new Coordinates(2, 2);
		GameMap map = new GameMap(3, 3);
		map.getBoard()[2][2] = new EmptyCell();
		
		assertTrue(MapService.checkCoordinatesAccessible(map, coords));
	}
	
	@Test
    public void should_return_coords_not_walkable() {
		Coordinates coords = new Coordinates(2, 2);
		GameMap map = new GameMap(3, 3);
		map.getBoard()[2][2] = new Mountain();
		
		assertFalse(MapService.checkCoordinatesAccessible(map, coords));
	}
}

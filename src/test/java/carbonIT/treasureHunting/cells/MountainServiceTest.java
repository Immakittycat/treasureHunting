package carbonIT.treasureHunting.cells;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.cells.service.MountainService;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;

public class MountainServiceTest {
	
	@Test
    public void should_create_mountain() throws LineException {
		String line = "M - 2 - 2";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		Mountain expectedMountain = new Mountain();
		expectedMountain.setCoords(new Coordinates(2, 2));
		Mountain outPutMountain = MountainService.createMountain(lineParts);
		
        assertEquals(expectedMountain, outPutMountain);
    }
	
	@Test
    public void should_not_create_mountain_incorrect_line_nb_args() {
		String line = "M - 1 - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> MountainService.createMountain(lineParts));
    }
	
	@Test
    public void should_not_create_mountain_incorrect_line_value_1() {
		String line = "J - 1 - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(LineException.class, () -> MountainService.createMountain(lineParts));
    }
	
	@Test
    public void should_not_create_mountain_incorrect_line_value_2() {
		String line = "M - Lala - 1";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> MountainService.createMountain(lineParts));
    }
	
	@Test
    public void should_not_create_mountain_incorrect_line_value_3() {
		String line = "M - 2 - Z";
		String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
		
		assertThrows(NumberFormatException.class, () -> MountainService.createMountain(lineParts));
    }
	
	@Test
    public void should_add_mountain() throws OutOfMapException, MapException {
		Mountain mountain = new Mountain();
		mountain.setCoords(new Coordinates(2, 2));
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		MountainService.addMountain(map, mountain);
		
        assertTrue(map.getBoard()[2][2] instanceof Mountain);
    }
	
	@Test
    public void should_not_add_mountain_out_of_bound() {
		Mountain mountain = new Mountain();
		mountain.setCoords(new Coordinates(4, 4));
		
		GameMap map = new GameMap(3, 3);
		
		assertThrows(OutOfMapException.class, () -> MountainService.addMountain(map, mountain));
    }
	
	@Test
    public void should_not_add_mountain_cell_used() {
		Mountain mountain = new Mountain();
		mountain.setCoords(new Coordinates(2, 2));
		
		GameMap map = new GameMap(3, 3);
		map.getBoard()[2][2] = new Treasure();
		
		assertThrows(MapException.class, () -> MountainService.addMountain(map, mountain));
    }

}

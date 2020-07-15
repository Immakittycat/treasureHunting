package carbonIT.treasureHunting.cells.service;

import java.util.List;

import carbonIT.treasureHunting.cells.Cell;
import carbonIT.treasureHunting.cells.Mountain;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;

public class MountainService {

	final private static int MOUTAIN_LINE_LENGTH = 3;

	public static Mountain createMountain(String[] lineParts) throws LineException, NumberFormatException {
		Mountain mountain = null;
		if (lineParts.length != MOUTAIN_LINE_LENGTH || !(lineParts[0].trim()
				.equals(PropertiesHandler.getInstance().getValue("starterMountainCharacter")))) {
			throw new LineException("This line isn't correctly written, no mountain can be created from it.");
		} else {
			int positionX = Integer.parseInt(lineParts[1].trim());
			int positionY = Integer.parseInt(lineParts[2].trim());
			mountain = new Mountain();
			mountain.setCoords(new Coordinates(positionX, positionY));
		}
		return mountain;
	}

	public static void addMountains(GameMap map, List<Mountain> mountains) {
		mountains.forEach(mountain -> {
			try {
				addMountain(map, mountain);
			} catch (OutOfMapException e) {
				e.printStackTrace();
			} catch (MapException e) {
				e.printStackTrace();
				System.exit(0);
			}
		});
	}

	public static void addMountain(GameMap map, Mountain mountain) throws OutOfMapException, MapException {
		Coordinates mountainCoordinates = mountain.getCoords();
		if (MapService.checkCoordinates(mountainCoordinates, map)) {
			Cell boardCell = map.getBoard()[mountainCoordinates.getpositionXAxis()][mountainCoordinates
					.getpositionYAxis()];
			if (MapService.checkCellAvailable(boardCell)) {
				map.getBoard()[mountainCoordinates.getpositionXAxis()][mountainCoordinates
						.getpositionYAxis()] = mountain;
			} else {
				throw new MapException("This cell is already used.");
			}
		} else {
			throw new OutOfMapException();
		}
	}

}

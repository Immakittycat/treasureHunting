package carbonIT.treasureHunting.cells.service;

import java.util.List;

import carbonIT.treasureHunting.cells.Cell;
import carbonIT.treasureHunting.cells.Treasure;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;

public class TreasureService {

	final private static int TREASURE_LINE_LENGTH = 4;

	public static Treasure createTreasure(String[] lineParts) throws LineException, NumberFormatException {
		Treasure treasure = null;
		if (lineParts.length != TREASURE_LINE_LENGTH || !(lineParts[0].trim()
				.equals(PropertiesHandler.getInstance().getValue("starterTreasureCharacter")))) {
			throw new LineException("This line isn't correctly written, no treasure can be created from it.");
		} else {
			int positionX = Integer.parseInt(lineParts[1].trim());
			int positionY = Integer.parseInt(lineParts[2].trim());
			int remainingLoots = Integer.parseInt(lineParts[3].trim());
			treasure = new Treasure();
			treasure.setCoords(new Coordinates(positionX, positionY));
			treasure.setRemainingLoots(remainingLoots);
		}
		return treasure;
	}

	public static void addTreasures(GameMap map, List<Treasure> treasures) {
		treasures.forEach(treasure -> {
			try {
				addTreasure(map, treasure);
			} catch (OutOfMapException e) {
				e.printStackTrace();
			} catch (MapException e) {
				e.printStackTrace();
				System.exit(0);
			}
		});
	}

	public static void addTreasure(GameMap map, Treasure treasure) throws OutOfMapException, MapException {
		Coordinates treasureCoordinates = treasure.getCoords();
		if (MapService.checkCoordinates(treasureCoordinates, map)) {
			Cell boardCell = map.getBoard()[treasureCoordinates.getpositionXAxis()][treasureCoordinates
					.getpositionYAxis()];
			if (MapService.checkCellAvailable(boardCell)) {
				map.getBoard()[treasureCoordinates.getpositionXAxis()][treasureCoordinates
						.getpositionYAxis()] = treasure;
			} else {
				throw new MapException("This cell is already used.");
			}
		} else {
			throw new OutOfMapException();
		}
	}

	public static String getTreasureLines(GameContext gameContext) {
		StringBuilder treasureLines = new StringBuilder();
		for (Treasure treasure : gameContext.getTreasures()) {
			if (treasure.getRemainingLoots() > 0) {
				treasureLines.append(PropertiesHandler.getInstance().getValue("starterTreasureCharacter"));
				treasureLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
				treasureLines.append(treasure.getCoords().getpositionXAxis());
				treasureLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
				treasureLines.append(treasure.getCoords().getpositionYAxis());
				treasureLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
				treasureLines.append(treasure.getRemainingLoots());
				treasureLines.append(System.lineSeparator());
			}
		}

		return treasureLines.toString();
	}

}

package carbonIT.treasureHunting.map.service;

import carbonIT.treasureHunting.adventurer.service.AdventurerService;
import carbonIT.treasureHunting.cells.Cell;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.service.MountainService;
import carbonIT.treasureHunting.cells.service.TreasureService;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.GameMap;

public class MapService {

	final private static int MAP_LINE_LENGTH = 3;

	public static GameMap createMap(String[] lineParts) throws LineException, NumberFormatException {
		GameMap map = null;
		if (lineParts.length != MAP_LINE_LENGTH
				|| !(lineParts[0].trim().equals(PropertiesHandler.getInstance().getValue("starterMapCharacter")))) {
			throw new LineException("This line isn't correctly written, no map can be created from it.");
		} else {
			int length = Integer.parseInt(lineParts[1].trim());
			int height = Integer.parseInt(lineParts[2].trim());
			map = new GameMap(length, height);
			fillMapWithEmptyCells(map);
		}
		return map;
	}
	

	public static GameMap fillMap(GameContext gc) {
		GameMap currentMap = gc.getMap();
		MountainService.addMountains(currentMap, gc.getMountains());
		TreasureService.addTreasures(currentMap, gc.getTreasures());
		AdventurerService.addAdventurers(currentMap, gc.getAdventurers());

		return currentMap;
	}

	public static void fillMapWithEmptyCells(GameMap currentMap) {
		int lengthMap = currentMap.getLength();
		int heightMap = currentMap.getHeight();
		for (int i = 0; i < lengthMap; i++) {
			for (int j = 0; j < heightMap; j++) {
				Cell emptyCell = new EmptyCell();
				emptyCell.setCoords(new Coordinates(i, j));
				currentMap.getBoard()[i][j] = emptyCell;
			}
		}

	}
	
	public static boolean checkCellAvailable(Cell boardCell) {
		return (boardCell instanceof EmptyCell && !boardCell.isHasAdventurer());
	}
	
	public static boolean checkCoordinatesAccessible(GameMap map, Coordinates coordinates) {
		boolean isCellAccessible = true;
		if (!MapService.checkCoordinates(coordinates, map)) {
			isCellAccessible = false;
		} else {
			isCellAccessible = map.getBoard()[coordinates.getpositionXAxis()][coordinates.getpositionYAxis()].isWalkable();
		}
		return isCellAccessible;
	}

	public static boolean checkCoordinates(Coordinates coords, GameMap map) {
		return (coords.getpositionXAxis() >= 0 && coords.getpositionXAxis() < map.getLength() && 
				coords.getpositionYAxis() >= 0 && coords.getpositionYAxis() < map.getHeight());
	}
	
	public static void setCellWalkable(Coordinates coords, GameMap map) {
		map.getBoard()[coords.getpositionXAxis()][coords.getpositionYAxis()].setWalkable(true);
	}
	
	public static void setCellNotWalkable(Coordinates coords, GameMap map) {
		map.getBoard()[coords.getpositionXAxis()][coords.getpositionYAxis()].setWalkable(false);
	}

}

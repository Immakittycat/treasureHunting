package carbonIT.treasureHunting.adventurer.service;

import java.util.List;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.cells.Cell;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.Treasure;
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

public class AdventurerService {

	final private static int ADVENTURER_LINE_LENGTH = 6;

	public static Adventurer createAdventurer(String[] lineParts, int priorityInFile, GameContext gameContext)
			throws LineException, NumberFormatException, AdventurerException {
		Adventurer adventurer = null;
		List<Adventurer> registeredAdventurers = gameContext.getAdventurers();
		if (lineParts.length != ADVENTURER_LINE_LENGTH || !(lineParts[0].trim()
				.equals(PropertiesHandler.getInstance().getValue("starterAdventurerCharacter")))) {
			throw new LineException("This line isn't correctly written, no adventurer can be created from it.");
		} else {
			String adventurerName = lineParts[1].trim();
			if (checkAdventurerNameAvailability(adventurerName, registeredAdventurers)) {
				int positionX = Integer.parseInt(lineParts[2].trim());
				int positionY = Integer.parseInt(lineParts[3].trim());

				adventurer = new Adventurer();
				adventurer.setPriorityInFile(priorityInFile);
				adventurer.setName(adventurerName);
				adventurer.setPositionOnBoard(gameContext.getMap().getBoard()[positionX][positionY]);
				adventurer.setOrientation(Orientation.valueOf(lineParts[4].trim()));
				adventurer.setMovements(lineParts[5]);
			} else {
				throw new AdventurerException("This adventurer name is already taken.");
			}
		}
		return adventurer;

	}

	public static boolean checkAdventurerNameAvailability(String adventurerName,
			List<Adventurer> registeredAdventurers) {
		boolean isNameAvailable = true;
		for (Adventurer registeredAdventurer : registeredAdventurers) {
			if (adventurerName.equals(registeredAdventurer.getName())) {
				isNameAvailable = false;
			}
		}
		return isNameAvailable;
	}

	public static void addAdventurers(GameMap map, List<Adventurer> adventurers) {
		adventurers.forEach(adventurer -> {
			try {
				addAdventurer(map, adventurer);
			} catch (OutOfMapException e) {
				e.printStackTrace();
			} catch (MapException e) {
				e.printStackTrace();
				System.exit(0);
			}
		});
	}

	public static void addAdventurer(GameMap map, Adventurer adventurer) throws OutOfMapException, MapException {
		Coordinates adventurerCoordinates = adventurer.getPositionOnBoard().getCoords();
		if (MapService.checkCoordinates(adventurerCoordinates, map)) {
			Cell boardCell = map.getBoard()[adventurerCoordinates.getpositionXAxis()][adventurerCoordinates
					.getpositionYAxis()];
			if (MapService.checkCellAvailable(boardCell)) {
				map.getBoard()[adventurerCoordinates.getpositionXAxis()][adventurerCoordinates.getpositionYAxis()]
						.setWalkable(false);
				map.getBoard()[adventurerCoordinates.getpositionXAxis()][adventurerCoordinates.getpositionYAxis()]
						.setHasAdventurer(true);
			} else {
				throw new MapException("This cell is already used.");
			}
		} else {
			throw new OutOfMapException();
		}
	}

	public static void lootCell(GameMap gameMap, Adventurer adventurer) {
		Coordinates adventurerPosition = adventurer.getPositionOnBoard().getCoords();
		Cell boardCell = gameMap.getBoard()[adventurerPosition.getpositionXAxis()][adventurerPosition
				.getpositionYAxis()];
		if (boardCell instanceof Treasure) {
			Treasure treasureCell = (Treasure) boardCell;
			if (treasureCell.getRemainingLoots() > 0) {
				adventurer.setTreasuresCollected(adventurer.getTreasuresCollected() + 1);
				treasureCell.setRemainingLoots(treasureCell.getRemainingLoots() - 1);
				
				if (treasureCell.getRemainingLoots() == 0) {
					gameMap.getBoard()[adventurerPosition.getpositionXAxis()][adventurerPosition
							.getpositionYAxis()] = new EmptyCell();
				}
			}
		}
	}

	public static String getAdventurerLines(GameContext gameContext) {
		StringBuilder adventurerLines = new StringBuilder();
		for (Adventurer adventurer : gameContext.getAdventurers()) {
			adventurerLines.append(PropertiesHandler.getInstance().getValue("starterAdventurerCharacter"));
			adventurerLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			adventurerLines.append(adventurer.getName());
			adventurerLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			adventurerLines.append(adventurer.getPositionOnBoard().getCoords().getpositionXAxis());
			adventurerLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			adventurerLines.append(adventurer.getPositionOnBoard().getCoords().getpositionYAxis());
			adventurerLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			adventurerLines.append(adventurer.getOrientation());
			adventurerLines.append(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			adventurerLines.append(adventurer.getTreasuresCollected());
			adventurerLines.append(System.lineSeparator());
		}

		return adventurerLines.toString();
	}

}

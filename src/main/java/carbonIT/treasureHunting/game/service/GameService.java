package carbonIT.treasureHunting.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.adventurer.service.AdventurerService;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;
import carbonIT.treasureHunting.movement.Movement;
import carbonIT.treasureHunting.movement.TypeMovement;
import carbonIT.treasureHunting.orientation.Orientation;

public class GameService {

	public static void createSequenceMovements(GameContext gameContext) {
		int nbTurns = getNbTurns(gameContext);
		List<Movement> orderedMovements = new ArrayList<Movement>();
		for (int i = 0; i < nbTurns; i++) {
			for (Adventurer adventurer : gameContext.getAdventurers()) {
				if (i <= adventurer.getMovements().size()) {
					Movement move = new Movement();
					move.setAdventurer(adventurer);
					move.setTypeMovement(adventurer.getMovements().get(i));
					orderedMovements.add(move);
				}
			}
		}
		gameContext.setMovements(orderedMovements);
	}

	public static int getNbTurns(GameContext gameContext) {
		int nbTurns = 0;
		for (Adventurer adventurer : gameContext.getAdventurers()) {
			nbTurns = Math.max(nbTurns, adventurer.getMovements().size());
		}
		return nbTurns;
	}

	public static void playGame(GameContext gameContext) {
		gameContext.getMovements().forEach(movement -> playMove(gameContext, movement));

	}

	public static void playMove(GameContext gameContext, Movement move) {
		switch (move.getTypeMovement()) {
		case A:
			moveForward(gameContext, move.getAdventurer());
			break;
		case D:
			changeDirection(move.getAdventurer(), move.getTypeMovement());
			break;
		case G:
			changeDirection(move.getAdventurer(), move.getTypeMovement());
			break;
		default:
			break;
		}
	}
	
	public static void changeDirection(Adventurer adventurer, TypeMovement typeMovement) {
		List<Orientation> orientations = new ArrayList<Orientation>(Arrays.asList(Orientation.values()));
		int indexCurrentOrientation = orientations.indexOf(adventurer.getOrientation());
		if (typeMovement == TypeMovement.D) {
			adventurer.setOrientation(orientations.get( (indexCurrentOrientation + 1 + 4) % 4 ));
		} else if (typeMovement == TypeMovement.G) {
			adventurer.setOrientation(orientations.get( (indexCurrentOrientation - 1 + 4) % 4 ));
		}
		
		adventurer.setChangedCellDuringTurn(false);
	}

	public static void moveForward(GameContext gameContext, Adventurer adventurer) {
		Coordinates adventurerPosition = adventurer.getPositionOnBoard().getCoords();
		Coordinates newCoordinates = null;
		switch (adventurer.getOrientation()) {
		case N:
			newCoordinates = new Coordinates(adventurerPosition.getpositionXAxis(), adventurerPosition.getpositionYAxis() - 1);
			break;
		case E:
			newCoordinates = new Coordinates(adventurerPosition.getpositionXAxis() + 1, adventurerPosition.getpositionYAxis());
			break;
		case S:
			newCoordinates = new Coordinates(adventurerPosition.getpositionXAxis(), adventurerPosition.getpositionYAxis() + 1);
			break;
		case O:
			newCoordinates = new Coordinates(adventurerPosition.getpositionXAxis() - 1, adventurerPosition.getpositionYAxis());
			break;
		}
		
		if (MapService.checkCoordinatesAccessible(gameContext.getMap(), newCoordinates)) {
			adventurer.getPositionOnBoard().setCoords(newCoordinates);
			adventurer.setChangedCellDuringTurn(true);
			AdventurerService.lootCell(gameContext.getMap(), adventurer);
			
			updateBoardState(gameContext.getMap(), adventurerPosition, newCoordinates);
		} else {
			adventurer.setChangedCellDuringTurn(false);
		}
	}
	
	private static void updateBoardState(GameMap map, Coordinates previousPosition, Coordinates newPosition) {
		MapService.setCellWalkable(previousPosition, map);
		map.getBoard()[previousPosition.getpositionXAxis()][previousPosition.getpositionYAxis()].setHasAdventurer(false);
		MapService.setCellNotWalkable(newPosition, map);
		map.getBoard()[newPosition.getpositionXAxis()][newPosition.getpositionYAxis()].setHasAdventurer(true);
	}

}

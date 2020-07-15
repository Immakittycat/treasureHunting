package carbonIT.treasureHunting.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.adventurer.Adventurer;
import carbonIT.treasureHunting.cells.EmptyCell;
import carbonIT.treasureHunting.cells.Mountain;
import carbonIT.treasureHunting.cells.service.MountainService;
import carbonIT.treasureHunting.coordinates.Coordinates;
import carbonIT.treasureHunting.exceptions.MapException;
import carbonIT.treasureHunting.exceptions.OutOfMapException;
import carbonIT.treasureHunting.game.service.GameService;
import carbonIT.treasureHunting.map.GameMap;
import carbonIT.treasureHunting.map.service.MapService;
import carbonIT.treasureHunting.movement.Movement;
import carbonIT.treasureHunting.movement.TypeMovement;
import carbonIT.treasureHunting.orientation.Orientation;

public class GameServiceTest {
	
	@Test
    public void should_get_nb_turns() {
		GameContext gameContext = new GameContext();
		Adventurer adventurer1 = new Adventurer(Orientation.valueOf("N"), "Toto", "AADADA", 1);
		Adventurer adventurer2 = new Adventurer(Orientation.valueOf("S"), "Tutu", "AGGAADA", 1);
		
		gameContext.getAdventurers().add(adventurer1);
		gameContext.getAdventurers().add(adventurer2);
		
		assertEquals(GameService.getNbTurns(gameContext), 7);
	}
	
	@Test
    public void should_create_sequence_movements() {
		GameContext gameContext = new GameContext();
		Adventurer adventurer1 = new Adventurer(Orientation.valueOf("N"), "Toto", "AD", 1);
		Adventurer adventurer2 = new Adventurer(Orientation.valueOf("S"), "Tutu", "GA", 1);
		
		gameContext.getAdventurers().add(adventurer1);
		gameContext.getAdventurers().add(adventurer2);
		
		Movement move1 = new Movement(adventurer1, TypeMovement.A);
		Movement move2 = new Movement(adventurer2, TypeMovement.G);
		Movement move3 = new Movement(adventurer1, TypeMovement.D);
		Movement move4 = new Movement(adventurer2, TypeMovement.A);
		
		List<Movement> movements = new ArrayList<Movement>();
		movements.add(move1);
		movements.add(move2);
		movements.add(move3);
		movements.add(move4);
		
		GameService.createSequenceMovements(gameContext);
		
		assertEquals(gameContext.getMovements(), movements);
	}
	
	@Test
    public void should_change_direction_right() {
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AD", 1);
		
		GameService.changeDirection(adventurer, TypeMovement.D);
		
		assertEquals(adventurer.getOrientation(), Orientation.valueOf("E"));
	}
	
	@Test
    public void should_change_direction_left() {
		Adventurer adventurer = new Adventurer(Orientation.valueOf("N"), "Toto", "AD", 1);
		
		GameService.changeDirection(adventurer, TypeMovement.G);
		
		assertEquals(adventurer.getOrientation(), Orientation.valueOf("O"));
	}
	
	@Test
    public void should_move_forward_accessible_cell() {
		GameContext gameContext = new GameContext();
		Adventurer adventurer = new Adventurer(Orientation.valueOf("S"), "Toto", "AADADA", 1);
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(1, 1));
		adventurer.setPositionOnBoard(positionAdventurer);
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		gameContext.setMap(map);
		
		GameService.moveForward(gameContext, adventurer);
		Coordinates expectedCoords = new Coordinates(1, 2);
		
		assertEquals(adventurer.getPositionOnBoard().getCoords(), expectedCoords);
	}
	
	@Test
    public void should_move_forward_map_border() {
		GameContext gameContext = new GameContext();
		Adventurer adventurer = new Adventurer(Orientation.valueOf("S"), "Toto", "AADADA", 1);
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(2, 2));
		adventurer.setPositionOnBoard(positionAdventurer);
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		gameContext.setMap(map);
		
		GameService.moveForward(gameContext, adventurer);
		Coordinates expectedCoords = new Coordinates(2, 2);
		
		assertEquals(adventurer.getPositionOnBoard().getCoords(), expectedCoords);
	}
	
	@Test
    public void should_move_forward_not_walkable_cell() throws OutOfMapException, MapException {
		GameContext gameContext = new GameContext();
		Adventurer adventurer = new Adventurer(Orientation.valueOf("E"), "Toto", "AADADA", 1);
		EmptyCell positionAdventurer = new EmptyCell();
		positionAdventurer.setCoords(new Coordinates(1, 1));
		adventurer.setPositionOnBoard(positionAdventurer);
		
		GameMap map = new GameMap(3, 3);
		MapService.fillMapWithEmptyCells(map);
		Mountain mountain = new Mountain();
		mountain.setCoords(new Coordinates(2, 1));
		MountainService.addMountain(map, mountain);
		gameContext.setMap(map);
		
		GameService.moveForward(gameContext, adventurer);
		Coordinates expectedCoords = new Coordinates(1, 1);
		
		assertEquals(adventurer.getPositionOnBoard().getCoords(), expectedCoords);
	}

}

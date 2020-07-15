package carbonIT.treasureHunting.line;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.line.service.LineService;

public class LineServiceTest {
	
	@Test
    public void should_get_line_map() throws LineException {
		String firstPartOfLine = PropertiesHandler.getInstance().getValue("starterMapCharacter");
		
        assertEquals(LineType.MAP_DESCRIPTOR, LineService.getLineType(firstPartOfLine));
    }
	
	@Test
    public void should_get_line_mountain() throws LineException {
		String firstPartOfLine = PropertiesHandler.getInstance().getValue("starterMountainCharacter");
		
        assertEquals(LineType.MOUNTAIN, LineService.getLineType(firstPartOfLine));
    }
	
	@Test
    public void should_get_line_treasure() throws LineException {
		String firstPartOfLine = PropertiesHandler.getInstance().getValue("starterTreasureCharacter");
		
        assertEquals(LineType.TREASURE, LineService.getLineType(firstPartOfLine));
    }
	
	@Test
    public void should_get_line_adventurer() throws LineException {
		String firstPartOfLine = PropertiesHandler.getInstance().getValue("starterAdventurerCharacter");
		
        assertEquals(LineType.ADVENTURER, LineService.getLineType(firstPartOfLine));
    }
	
	@Test
    public void should_get_line_comment() throws LineException {
		String firstPartOfLine = PropertiesHandler.getInstance().getValue("starterCommentCharacter");
		
        assertEquals(LineType.COMMENT, LineService.getLineType(firstPartOfLine));
    }
	
	@Test
    public void should_not_get_line_type() throws LineException {
		String firstPartOfLine = "Q";
		
		assertThrows(LineException.class, () -> LineService.getLineType(firstPartOfLine));
    }

}

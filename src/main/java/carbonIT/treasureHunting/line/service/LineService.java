package carbonIT.treasureHunting.line.service;

import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.line.LineType;

public class LineService {


    public static LineType getLineType(String firstPartOfLine) throws LineException {
    	
    	LineType lineType = null;

        if (firstPartOfLine.equals(PropertiesHandler.getInstance().getValue("starterMapCharacter"))) {
            lineType = LineType.MAP_DESCRIPTOR;
        } else if (firstPartOfLine.equals(PropertiesHandler.getInstance().getValue("starterMountainCharacter"))) {
        	lineType = LineType.MOUNTAIN;
        } else if (firstPartOfLine.equals(PropertiesHandler.getInstance().getValue("starterTreasureCharacter"))) {
        	lineType = LineType.TREASURE;
        } else if (firstPartOfLine.equals(PropertiesHandler.getInstance().getValue("starterAdventurerCharacter"))) {
        	lineType = LineType.ADVENTURER;
        } else if (firstPartOfLine.startsWith(PropertiesHandler.getInstance().getValue("starterCommentCharacter"))) {
        	lineType = LineType.COMMENT;
        } else {
            throw new LineException("Type de ligne inconnu : " + firstPartOfLine);
        }
        return lineType;
    }
}

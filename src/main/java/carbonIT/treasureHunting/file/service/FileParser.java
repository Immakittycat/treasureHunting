package carbonIT.treasureHunting.file.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import carbonIT.treasureHunting.adventurer.service.AdventurerService;
import carbonIT.treasureHunting.cells.service.MountainService;
import carbonIT.treasureHunting.cells.service.TreasureService;
import carbonIT.treasureHunting.configuration.service.PropertiesHandler;
import carbonIT.treasureHunting.exceptions.AdventurerException;
import carbonIT.treasureHunting.exceptions.LineException;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.line.LineType;
import carbonIT.treasureHunting.line.service.LineService;
import carbonIT.treasureHunting.map.service.MapService;

public class FileParser {
	
	private int indexLine;
	
	private StringBuilder unchangedPartOfFile;
	
	public FileParser() {
		indexLine = 0;
		unchangedPartOfFile = new StringBuilder();
	}

	public GameContext readFile(String filePath) {
		
		GameContext gameContext = new GameContext();

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

			stream.forEach((line -> {
				try {
					this.processLine(line, gameContext);
				} catch (AdventurerException e) {
					e.printStackTrace();
				}
			}));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return gameContext;
	}

	private void processLine(String line, GameContext gameContext) throws AdventurerException {
		if (line.length() != 0) {
			String[] lineParts = line.split(PropertiesHandler.getInstance().getValue("lineElementSeparator"));
			try {
				LineType lineType = LineService.getLineType(lineParts[0].trim());
				switch (lineType) {
				case MAP_DESCRIPTOR:
					gameContext.setMap(MapService.createMap(lineParts));
					unchangedPartOfFile.append(line).append(System.lineSeparator());
					break;
				case MOUNTAIN:
					gameContext.getMountains().add(MountainService.createMountain(lineParts));
					unchangedPartOfFile.append(line).append(System.lineSeparator());
					break;
				case TREASURE:
					gameContext.getTreasures().add(TreasureService.createTreasure(lineParts));
					break;
				case ADVENTURER:
					gameContext.getAdventurers().add(AdventurerService.createAdventurer(lineParts, indexLine, gameContext));
				    break;
				default:
					break;
				}
			} catch (LineException e) {
				e.printStackTrace();
			}
		}
		indexLine++;
	}
	
	public void writeFile(String filePath, GameContext gameContext) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
	    writer.append(unchangedPartOfFile);
	    writer.append(TreasureService.getTreasureLines(gameContext));
	    writer.append(AdventurerService.getAdventurerLines(gameContext));
	    writer.close();
	}
}

package carbonIT.treasureHunting;

import org.apache.commons.cli.CommandLine;

import carbonIT.treasureHunting.cli.service.CLIHandler;
import carbonIT.treasureHunting.file.service.FileParser;
import carbonIT.treasureHunting.game.GameContext;
import carbonIT.treasureHunting.game.service.GameService;
import carbonIT.treasureHunting.map.service.MapService;

public class Main {

    public static void main(String[] args) throws Exception {
    	CommandLine commandLine = CLIHandler.getCommandLine(args);
    	String fileInputPath = commandLine.getOptionValue(CLIHandler.INPUT_COMMAND_LONG);  	
    	FileParser fp = new FileParser();
    	GameContext gameContext = fp.readFile(fileInputPath);
    	
    	MapService.fillMap(gameContext);
    	GameService.createSequenceMovements(gameContext);
    	GameService.playGame(gameContext);
    	
    	String fileOutputPath = commandLine.getOptionValue(CLIHandler.OUTPUT_COMMAND_LONG);
    	fp.writeFile(fileOutputPath, gameContext);

    }
}
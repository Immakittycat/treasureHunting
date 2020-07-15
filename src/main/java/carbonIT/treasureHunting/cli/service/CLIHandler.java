package carbonIT.treasureHunting.cli.service;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public abstract class CLIHandler {
	
	final public static String INPUT_COMMAND_LONG = "fileInput";
	final public static String INPUT_COMMAND_SHORT = "i";
	final public static String OUTPUT_COMMAND_LONG = "fileOutput";
	final public static String OUTPUT_COMMAND_SHORT = "o";
	
	public static CommandLine getCommandLine(String[] args) throws ParseException {

        Options options = new Options();
        options.addRequiredOption(INPUT_COMMAND_SHORT, INPUT_COMMAND_LONG, true, "The path of the input file.");
        options.addRequiredOption(OUTPUT_COMMAND_SHORT, OUTPUT_COMMAND_LONG, true, "The path of the output file."); 

        return parseEntries(options, args);
    }


    public static CommandLine parseEntries(Options options, String[] args) {

        CommandLineParser commandLineParser = new DefaultParser();

        try {
            return commandLineParser.parse(options, args);
        } catch (ParseException e) {
        	HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("treasureMap", options);
            e.printStackTrace();
            System.exit(0);
        }
		return null;
    }

}

package carbonIT.treasureHunting.configuration.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

	private static PropertiesHandler instance = null;

	private Properties properties;

	final private static String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";

	private PropertiesHandler() {
		this.properties = new Properties();
		this.readPropertiesFile();
	}
	
	private void readPropertiesFile() {
		try (InputStream inputStream = new FileInputStream(PROPERTIES_FILE_PATH)) {
			this.properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static synchronized PropertiesHandler getInstance() {
		if (instance == null)
			instance = new PropertiesHandler();
		return instance;
	}

	public String getValue(String propKey) {
		return this.properties.getProperty(propKey);
	}

}

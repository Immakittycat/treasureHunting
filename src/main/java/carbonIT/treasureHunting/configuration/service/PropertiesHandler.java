package carbonIT.treasureHunting.configuration.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

	private static PropertiesHandler instance = null;

	private Properties properties;

	private PropertiesHandler() {
		this.properties = new Properties();
		this.readPropertiesFile();
	}
	
	private void readPropertiesFile() {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
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

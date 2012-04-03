package ch.boxi.ants.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigLoader {
	private static Logger		logger					= Logger.getLogger(ConfigLoader.class);
	private static final String	defultConfigFileName	= "config/config.properties";
	
	public Properties loadConfig(String configFile) {
		Properties prop = getDefaultProperties();
		
		Collection<String> fileNames = new LinkedList<String>();
		fileNames.add(configFile);
		fileNames.add(defultConfigFileName);
		
		for (String fileName : fileNames) {
			Properties propFromFile = loadFromFile(fileName);
			if (propFromFile != null) {
				for (String key : propFromFile.stringPropertyNames()) {
					String value = propFromFile.getProperty(key);
					prop.setProperty(key, value);
				}
				break;
			}
		}
		
		logProperties(prop);
		
		return prop;
	}
	
	private void logProperties(Properties prop) {
		logger.trace("***** Show configuration *****");
		for (String key : prop.stringPropertyNames()) {
			logger.trace("* " + key + ": " + prop.getProperty(key));
		}
	}
	
	/**
	 * @param fileName
	 * @return properties from given file or null if there was an Exception
	 */
	public Properties loadFromFile(String fileName) {
		File f = new File(fileName);
		Properties prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(f);
			prop.load(fis);
			logger.info("load config from " + fileName);
		} catch (IOException e) {
			logger.warn("Failed to load config from given file: " + fileName);
			prop = null;
		}
		
		return prop;
	}
	
	public Properties getDefaultProperties() {
		Properties prop = new Properties();
		
		prop.setProperty(ConfigKeys.WORLD_WIDTH, "10");
		prop.setProperty(ConfigKeys.WORLD_HEIGHT, "10");
		prop.setProperty(ConfigKeys.COUNT_OF_RACES, "1");
		prop.setProperty(ConfigKeys.INIT_ANTS_PER_RACE, "1");
		prop.setProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD, "2");
		prop.setProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_WALKING, "5");
		prop.setProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRING, "5");
		prop.setProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRIED_WALKING, "7");
		prop.setProperty(ConfigKeys.INIT_AMOUNT_OF_LIFEPOINTS, "100");
		prop.setProperty(ConfigKeys.PROBABILITY_OF_CARRION_ON_FIELD, "0.10");
		return prop;
	}
}

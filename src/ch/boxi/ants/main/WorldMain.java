package ch.boxi.ants.main;

import java.util.Properties;

import ch.boxi.ants.World;

public class WorldMain {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigLoader configLoader = new ConfigLoader();
		String fileFromArgs = "n/a";
		if (args.length > 0) {
			fileFromArgs = args[0];
		}
		Properties prop = configLoader.loadConfig(fileFromArgs);
		World world = new World(prop);
		world.runGame();
	}
	
}

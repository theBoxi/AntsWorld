package ch.boxi.ants.helper;

import java.util.Random;

import ch.boxi.ants.map.Coordinate;
import ch.boxi.ants.map.Vector;

public class RandomHelper {
	private static final Random	rand	= new Random(System.currentTimeMillis());
	
	public static Coordinate getCoordiante(int width, int height) {
		int rndX = rand.nextInt(width);
		int rndY = rand.nextInt(height);
		return new Coordinate(rndX, rndY);
	}
	
	public static Vector getMoveDirection() {
		int direction = rand.nextInt(4);
		Vector retDirection = Vector.EAST;
		switch (direction) {
			case 0:
				retDirection = Vector.NORTH;
				break;
			case 1:
				retDirection = Vector.EAST;
				break;
			case 2:
				retDirection = Vector.SOUTH;
				break;
			case 3:
				retDirection = Vector.WEST;
				break;
		}
		return retDirection;
	}
	
	public static float getProbability() {
		return rand.nextFloat();
	}
}

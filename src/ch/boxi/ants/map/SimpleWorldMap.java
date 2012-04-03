package ch.boxi.ants.map;

import java.util.HashMap;
import java.util.Map;

import ch.boxi.ants.WorldObject;
import ch.boxi.ants.move.View;
import ch.boxi.javaUtil.map.MultiMap;

public class SimpleWorldMap implements WorldMap {
	private MultiMap<Coordinate, WorldObject>	map;
	private Map<WorldObject, Coordinate>		objToCoordinateMap;
	private int									width;
	private int									height;
	private boolean								endLess;
	
	private SimpleWorldMap() {
		map = new MultiMap<Coordinate, WorldObject>();
		objToCoordinateMap = new HashMap<WorldObject, Coordinate>();
	}
	
	public SimpleWorldMap(int width, int height, boolean endLess) {
		this();
		this.width = width;
		this.height = height;
		this.endLess = endLess;
	}
	
	@Override
	public void add(WorldObject o, Coordinate c) {
		testCoordinateInRange(c);
		map.put(c, o);
		objToCoordinateMap.put(o, c);
	}
	
	@Override
	public View get(Coordinate c, int steps) {
		if (steps > 0) {
			throw new IllegalArgumentException("This is Just Simple can only handle steps = 0");
		}
		View v = new View();
		v.put(c, map.get(c));
		return v;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public Coordinate move(WorldObject o, Vector v) {
		Coordinate c = objToCoordinateMap.remove(o);
		if (c == null) {
			throw new OutOfRangeException("WorldObject not known");
		}
		map.remove(c, o);
		
		Coordinate newC = Coordinate.plus(c, v);
		if (endLess) {
			newC = bringCoordinateInEndlessRange(newC);
		}
		testCoordinateInRange(newC);
		map.put(newC, o);
		objToCoordinateMap.put(o, newC);
		
		return newC;
	}
	
	@Override
	public void remove(WorldObject o) {
		Coordinate c = objToCoordinateMap.remove(o);
		if (c == null) {
			throw new IllegalArgumentException("WorldObject not known");
		}
		map.remove(c, o);
	}
	
	private void testCoordinateInRange(Coordinate c) throws OutOfRangeException {
		if (c.getX() > width || c.getY() > height) {
			throw new OutOfRangeException("Coordinate " + c.toString() + " is out of worldRange");
		}
	}
	
	private Coordinate bringCoordinateInEndlessRange(Coordinate c) {
		int x = c.getX();
		int y = c.getY();
		while (x < 0) {
			x = width + x; // +- = - ;-)
		}
		while (y < 0) {
			y = height + y; // +- = - ;-)
		}
		
		int newX = x % width;
		int newY = y % height;
		Coordinate newC = new Coordinate(newX, newY);
		return newC;
	}
	
	public boolean isEndLess() {
		return endLess;
	}
	
}

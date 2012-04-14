package ch.boxi.ants.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ch.boxi.ants.WorldObject;
import ch.boxi.ants.move.View;
import ch.boxi.javaUtil.map.MultiMap;

public class SimpleWorldMap implements WorldMap {
	private MultiMap<Coordinate, WorldObject>	map;
	private Map<WorldObject, Coordinate>		objToCoordinateMap;
	private MapDimension						dimension;
	
	private SimpleWorldMap() {
		map = new MultiMap<Coordinate, WorldObject>();
		objToCoordinateMap = new HashMap<WorldObject, Coordinate>();
	}
	
	public SimpleWorldMap(int width, int height, boolean endLess) {
		this();
		this.dimension = new MapDimension(width, height, endLess);
	}
	
	@Override
	public void add(WorldObject o, Coordinate c) {
		testCoordinateInRange(c);
		map.put(c, o);
		objToCoordinateMap.put(o, c);
	}
	
	@Override
	public View get(Coordinate c, int steps) {
		View v = new View();
		for (Coordinate coordinate : getCoordinatesFor(c, steps)) {
			List<WorldObject> list = map.get(coordinate);
			if (list != null && !list.isEmpty()) {
				v.put(coordinate, list);
			}
		}
		return v;
	}
	
	Set<Coordinate> getCoordinatesFor(Coordinate center, int steps) {
		Set<Coordinate> coordinates = new TreeSet<Coordinate>();
		for (int x = center.getX() - steps; x <= center.getX() + steps; x++) {
			for (int y = center.getY() - steps; y <= center.getY() + steps; y++) {
				Coordinate c = new Coordinate(x, y);
				if (CoordinateHelper.smallestVector(center, c, dimension).getWalkingDistance() <= steps) {
					c = makeCoordinateLegal(c, false);
					coordinates.add(c);
				}
			}
		}
		return coordinates;
	}
	
	@Override
	public MapDimension getDimension() {
		return dimension;
	}
	
	@Override
	public Coordinate move(WorldObject o, Vector v) {
		Coordinate c = objToCoordinateMap.remove(o);
		if (c == null) {
			throw new OutOfRangeException("WorldObject not known");
		}
		map.remove(c, o);
		
		Coordinate newC = Coordinate.plus(c, v);
		newC = makeCoordinateLegal(newC, true);
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
		if (c.getX() >= dimension.getWidth() || c.getY() >= dimension.getHeight()) {
			throw new OutOfRangeException("Coordinate " + c.toString() + " is out of worldRange");
		}
	}
	
	private Coordinate makeCoordinateLegal(Coordinate c, boolean throwOutOfRangeException) throws OutOfRangeException {
		if (dimension.isEndless()) {
			return bringCoordinateInEndlessRange(c);
		} else {
			return cutCoordinateInRange(c, throwOutOfRangeException);
		}
	}
	
	private Coordinate cutCoordinateInRange(Coordinate c, boolean throwOutOfRangeException) {
		Coordinate newCoordinate = new Coordinate();
		
		if (c.getX() < 0) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setX(0);
		} else if (c.getX() >= dimension.getWidth()) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setX(dimension.getWidth());
		} else {
			newCoordinate.setX(c.getX());
		}
		
		if (c.getY() < 0) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setY(0);
		} else if (c.getY() >= dimension.getHeight()) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setY(dimension.getHeight());
		} else {
			newCoordinate.setY(c.getY());
		}
		
		return newCoordinate;
	}
	
	private Coordinate bringCoordinateInEndlessRange(Coordinate c) {
		int x = c.getX();
		int y = c.getY();
		while (x < 0) {
			x = dimension.getWidth() + x; // +- = - ;-)
		}
		while (y < 0) {
			y = dimension.getHeight() + y; // +- = - ;-)
		}
		
		int newX = x % dimension.getWidth();
		int newY = y % dimension.getHeight();
		Coordinate newC = new Coordinate(newX, newY);
		return newC;
	}
	
	@Override
	public Map<WorldObject, Coordinate> getAllWorldObjects() {
		return Collections.unmodifiableMap(objToCoordinateMap);
	}
	
	@Override
	public void clear() {
		objToCoordinateMap.clear();
		map.clear();
	}
	
}

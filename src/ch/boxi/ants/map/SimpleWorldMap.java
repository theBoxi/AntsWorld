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
		CoordinateHelper.testCoordinateInRange(c, dimension);
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
					c = CoordinateHelper.makeCoordinateLegal(c, false, dimension);
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
		newC = CoordinateHelper.makeCoordinateLegal(newC, true, dimension);
		CoordinateHelper.testCoordinateInRange(newC, dimension);
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

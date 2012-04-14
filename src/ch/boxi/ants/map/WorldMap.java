package ch.boxi.ants.map;

import java.util.Map;

import ch.boxi.ants.WorldObject;
import ch.boxi.ants.move.View;

public interface WorldMap {
	public MapDimension getDimension();
	
	public View get(Coordinate c, int steps);
	
	public Coordinate move(WorldObject o, Vector v);
	
	public void add(WorldObject o, Coordinate c);
	
	public void remove(WorldObject o);
	
	public Map<WorldObject, Coordinate> getAllWorldObjects();
	
	public void clear();
}

package ch.boxi.ants.map;

import ch.boxi.ants.WorldObject;
import ch.boxi.ants.move.View;

public interface WorldMap {
	public int getWidth();
	
	public int getHeight();
	
	public View get(Coordinate c, int steps);
	
	public Coordinate move(WorldObject o, Vector v);
	
	public void add(WorldObject o, Coordinate c);
	
	public void remove(WorldObject o);
	
	public boolean isEndLess();
}

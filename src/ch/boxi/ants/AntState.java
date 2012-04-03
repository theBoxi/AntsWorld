package ch.boxi.ants;

import ch.boxi.ants.map.Coordinate;
import ch.boxi.ants.move.Move;

public class AntState {
	private int			lifePoints;
	private Move		lastMove;
	private Coordinate	Position;
	private WorldObject	carringObject;
	
	public WorldObject getCarringObject() {
		return carringObject;
	}
	
	public void setCarringObject(WorldObject carringObject) {
		this.carringObject = carringObject;
	}
	
	public Coordinate getPosition() {
		return Position;
	}
	
	public void setPosition(Coordinate position) {
		Position = position;
	}
	
	public Move getLastMove() {
		return lastMove;
	}
	
	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
	}
	
	public int getLifePoints() {
		return lifePoints;
	}
	
	public void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}
	
	public boolean isCarring() {
		return carringObject != null;
	}
}

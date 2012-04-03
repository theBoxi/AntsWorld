package ch.boxi.ants.move;

import ch.boxi.ants.WorldObject;
import ch.boxi.ants.map.Vector;

public class Move {
	private Action		action;
	private WorldObject	interactWith;
	private Vector		walkdirection;
	
	public Move() {
		
	}
	
	public Move(Action action, WorldObject interactWith, Vector walkdirection) {
		this();
		this.action = action;
		this.interactWith = interactWith;
		this.walkdirection = walkdirection;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public WorldObject getInteractWith() {
		return interactWith;
	}
	
	public void setInteractWith(WorldObject interactWith) {
		this.interactWith = interactWith;
	}
	
	public Vector getWalkdirection() {
		return walkdirection;
	}
	
	public void setWalkdirection(Vector walkdirection) {
		this.walkdirection = walkdirection;
	}
	
	public String toString() {
		return "Move: " + action;
	}
}

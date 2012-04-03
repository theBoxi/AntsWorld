package ch.boxi.ants;

public class AntHill extends WorldObject {
	
	public AntHill(String id) {
		super(id);
	}
	
	public String toString() {
		return "Hill: " + getId();
	}
}

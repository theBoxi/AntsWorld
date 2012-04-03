package ch.boxi.ants;

public class Carrion extends WorldObject {
	
	private int	lifePoints;
	
	public Carrion(String id, int lifePoints) {
		super(id);
		this.lifePoints = lifePoints;
	}
	
	public int getLifePoints() {
		return lifePoints;
	}
	
	public String toString() {
		return "Carrion: " + getId();
	}
}

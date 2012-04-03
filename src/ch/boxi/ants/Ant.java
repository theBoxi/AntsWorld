package ch.boxi.ants;

import ch.boxi.ants.helper.RandomHelper;
import ch.boxi.ants.move.Action;
import ch.boxi.ants.move.Move;
import ch.boxi.ants.move.View;

public class Ant extends WorldObject {
	private AntRace	race;
	private int		lifePoints;
	private int		maxLifePoints;
	
	public Ant(String id, AntRace race, int lifePoints) {
		super(id);
		this.lifePoints = lifePoints;
		this.maxLifePoints = lifePoints;
		this.race = race;
	}
	
	public Move getNextMove(View v, int lifePoints) {
		this.lifePoints = lifePoints;
		
		Move m = null;
		
		if (lifePoints < maxLifePoints) {
			for (WorldObject obj : v.allValues()) {
				if (obj instanceof Carrion) {
					m = new Move(Action.EAT, obj, null);
				}
			}
		}
		
		if (m == null) {
			m = new Move(Action.WALK, null, RandomHelper.getMoveDirection());
		}
		
		return m;
	}
	
	public int getLifePoints() {
		return lifePoints;
	}
	
	public String toString() {
		return "Ant: " + getId();
	}
}

package ch.boxi.ants;

import ch.boxi.ants.helper.IdGenerator;

public class AntRace {
	private String	name;
	private AntHill	anthill;
	
	public AntRace(String name, AntHill anthill) {
		super();
		if (name == null || name.equals("")) {
			name = IdGenerator.getNextUniqueID();
		}
		this.name = name;
		this.anthill = anthill;
	}
	
	public String getName() {
		return name;
	}
	
	public AntHill getAnthill() {
		return anthill;
	}
}

package ch.boxi.ants;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ch.boxi.ants.helper.IdGenerator;
import ch.boxi.ants.helper.RandomHelper;
import ch.boxi.ants.main.ConfigKeys;
import ch.boxi.ants.map.Coordinate;
import ch.boxi.ants.map.SimpleWorldMap;
import ch.boxi.ants.map.WorldMap;
import ch.boxi.ants.move.Action;
import ch.boxi.ants.move.Move;
import ch.boxi.ants.move.View;

public class World {
	private WorldMap			map;
	private Map<Ant, AntState>	antsState;
	private Properties			config;
	
	private World() {
		antsState = new HashMap<Ant, AntState>();
	}
	
	public World(Properties config) {
		this();
		this.config = config;
		String widthStr = config.getProperty(ConfigKeys.WORLD_WIDTH);
		String heightStr = config.getProperty(ConfigKeys.WORLD_HEIGHT);
		map = new SimpleWorldMap(Integer.parseInt(widthStr), Integer.parseInt(heightStr), true);
		
		makeBigBang();
		System.out.println(this);
	}
	
	private void makeBigBang() {
		addInitialCarrion();
		addInitialCorn();
		addInitionalants();
	}
	
	private void addInitionalants() {
		int raceCount = Integer.parseInt(config.getProperty(ConfigKeys.COUNT_OF_RACES));
		int antPerRace = Integer.parseInt(config.getProperty(ConfigKeys.INIT_ANTS_PER_RACE));
		int initLifePoints = Integer.parseInt(config.getProperty(ConfigKeys.INIT_AMOUNT_OF_LIFEPOINTS));
		for (int i = 0; i < raceCount; i++) {
			Coordinate initCoordinate = RandomHelper.getCoordiante(map.getDimension().getWidth(), map.getDimension()
					.getHeight());
			AntHill hill = new AntHill(IdGenerator.getNextUniqueID());
			AntRace race = new AntRace(null, hill);
			map.add(hill, initCoordinate);
			for (int n = 0; n < antPerRace; n++) {
				AntState state = new AntState();
				state.setLastMove(null);
				state.setLifePoints(100);
				state.setPosition(initCoordinate);
				Ant ant = new Ant(IdGenerator.getNextUniqueID(), race, initLifePoints);
				antsState.put(ant, state);
				map.add(ant, initCoordinate);
			}
		}
	}
	
	private void addInitialCarrion() {
		
		float probability = Float.parseFloat(config.getProperty(ConfigKeys.PROBABILITY_OF_CARRION_ON_FIELD));
		int moveCost = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_WALKING));
		int stdCost = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD));
		int numOfFields = map.getDimension().getWidth() * map.getDimension().getHeight();
		int raceCount = Integer.parseInt(config.getProperty(ConfigKeys.COUNT_OF_RACES));
		int antPerRace = Integer.parseInt(config.getProperty(ConfigKeys.INIT_ANTS_PER_RACE));
		int numOfAnts = raceCount * antPerRace;
		int numOfLPs;
		
		for (int i = 0; i < probability * numOfFields; i++) {
			numOfLPs = Math.round(2 * (1 / probability) * (moveCost + stdCost) * numOfAnts
					* RandomHelper.getProbability());
			Carrion carr = new Carrion(IdGenerator.getNextUniqueID(), numOfLPs);
			map.add(carr, RandomHelper.getCoordiante(map.getDimension().getWidth(), map.getDimension().getHeight()));
		}
	}
	
	private void addInitialCorn() {
		// TODO: Pipo: add some Magic here ;-)
	}
	
	private boolean isAllowedMove(AntState state, Move move) {
		if (move.getInteractWith() != null) {
			if (move.getAction() != Action.DROP) {
				if (!map.get(state.getPosition(), 0).allValues().contains(move.getInteractWith())) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void runGame() {
		for (int i = 0; i < 12; i++) {
			for (Ant ant : antsState.keySet()) {
				AntState state = antsState.get(ant);
				View v = map.get(state.getPosition(), 0);
				Move move = ant.getNextMove(v, state.getLifePoints());
				int lifeReduce = 0;
				Coordinate newPosition = state.getPosition();
				if (isAllowedMove(state, move)) {
					switch (move.getAction()) {
						case DROP:
							lifeReduce = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD));
							map.add(move.getInteractWith(), newPosition);
							state.setCarringObject(null);
							break;
						case EAT:
							lifeReduce = -1 * Integer.parseInt(config.getProperty(ConfigKeys.EATING_PINTS_PER_ROUND));
							if (state.isCarring()) {
								lifeReduce += Integer.parseInt(config
										.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRING));
							}
							break;
						case TAKE:
							lifeReduce = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD));
							map.remove(move.getInteractWith());
							state.setCarringObject(move.getInteractWith());
							break;
						case WALK:
							newPosition = map.move(ant, move.getWalkdirection());
							lifeReduce = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_WALKING));
							if (state.isCarring()) {
								lifeReduce += Integer.parseInt(config
										.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRING));
							}
							break;
						default:
							lifeReduce = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD));
							if (state.isCarring()) {
								lifeReduce += Integer.parseInt(config
										.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRING));
							}
							break;
					}
				} else {
					lifeReduce = Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_STD));
					if (state.isCarring()) {
						lifeReduce += Integer.parseInt(config.getProperty(ConfigKeys.REDUCE_LIFE_PER_ROUND_CARRING));
					}
				}
				
				state.setLastMove(move);
				state.setLifePoints(state.getLifePoints() - lifeReduce);
				state.setPosition(newPosition);
				antsState.put(ant, state);
			}
			System.out.println(this);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < map.getDimension().getHeight(); y++) {
			for (int x = 0; x < map.getDimension().getWidth(); x++) {
				View view = map.get(new Coordinate(x, y), 0);
				boolean foundanAnt = false;
				boolean foundaCarrion = false;
				boolean foundHill = false;
				for (WorldObject obj : view.allValues()) {
					if (AntHill.class.isInstance(obj)) {
						foundHill = true;
					} else if (Ant.class.isInstance(obj)) {
						foundanAnt = true;
					} else if (Carrion.class.isInstance(obj)) {
						foundaCarrion = true;
					}
				}
				if (foundHill) {
					sb.append("H");
				} else if (foundanAnt && foundaCarrion) {
					sb.append("B");
				} else if (foundanAnt) {
					sb.append("X");
				} else if (foundaCarrion) {
					sb.append("O");
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		sb.append("=====================================\n");
		for (Ant ant : antsState.keySet()) {
			AntState state = antsState.get(ant);
			sb.append(" ant." + ant.getId() + ": " + state.getLifePoints() + "\n");
		}
		sb.append("=====================================\n");
		return sb.toString();
	}
}

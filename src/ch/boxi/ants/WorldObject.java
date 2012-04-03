package ch.boxi.ants;

public abstract class WorldObject {
	private String	id;
	
	public String getId() {
		return id;
	}
	
	public WorldObject(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof WorldObject)) {
			return false;
		}
		WorldObject obj = (WorldObject) o;
		return id.equals(obj.getId());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

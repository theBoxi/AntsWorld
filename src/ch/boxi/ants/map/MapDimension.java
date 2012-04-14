package ch.boxi.ants.map;

public class MapDimension extends TwoInts {
	private boolean	endless;
	
	public MapDimension(int width, int hight, boolean endless) {
		super(width, hight);
		this.endless = endless;
	}
	
	public int getWidth() {
		return getX();
	}
	
	public int getHeight() {
		return getY();
	}
	
	public boolean isEndless() {
		return endless;
	}
}

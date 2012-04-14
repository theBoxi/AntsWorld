package ch.boxi.ants.map;

public class TwoInts implements Comparable<TwoInts> {
	private int	x;
	private int	y;
	
	public TwoInts() {
		x = 0;
		y = 0;
	}
	
	public TwoInts(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	void plus(TwoInts ti) {
		setX(getX() + ti.getX());
		setY(getY() + ti.getY());
	}
	
	@Override
	public int compareTo(TwoInts o) {
		if (x == o.x && y == o.y) {
			return 0;
		} else if (x == o.x && y < o.y) {
			return -2;
		} else if (x < o.x && y == o.y) {
			return -1;
		} else if (x < o.x && y < o.y) {
			return 3;
		} else if (x == o.x && y > o.y) {
			return 1;
		} else if (x > o.x && y == o.y) {
			return 2;
		} else { // x > o.x && y > o.y
			return 3;
		}
	}
}

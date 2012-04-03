package ch.boxi.ants.map;

public class Coordinate extends TwoInts {
	
	public Coordinate() {
		super();
	}
	
	public Coordinate(int x, int y) {
		super(x, y);
	}
	
	public void plus(Vector v) {
		super.plus(v);
	}
	
	public static Coordinate plus(Coordinate c, Vector v) {
		Coordinate ret = new Coordinate();
		ret.setX(c.getX() + v.getX());
		ret.setY(c.getY() + v.getY());
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate)) {
			return false;
		}
		Coordinate c = (Coordinate) o;
		return c.getX() == getX() && c.getY() == getY();
	}
	
	@Override
	public int hashCode() {
		return (new String("Coordinate " + toString())).hashCode();
	}
	
	@Override
	public String toString() {
		return getX() + "," + getY();
	}
}

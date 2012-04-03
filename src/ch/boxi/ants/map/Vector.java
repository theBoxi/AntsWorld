package ch.boxi.ants.map;

public class Vector extends TwoInts {
	
	public static final Vector	NORTH	= new Vector(0, 1);
	public static final Vector	EAST	= new Vector(1, 0);
	public static final Vector	SOUTH	= new Vector(0, -1);
	public static final Vector	WEST	= new Vector(-1, 0);
	
	public Vector() {
		super();
	}
	
	public Vector(int x, int y) {
		super(x, y);
	}
	
	public void plus(Vector v) {
		super.plus(v);
	}
	
	public static Vector plus(Vector v1, Vector v2) {
		Vector ret = new Vector();
		ret.setX(v1.getX() + v2.getX());
		ret.setY(v1.getY() + v2.getY());
		return ret;
	}
	
}

package ch.boxi.ants.map;

public class CoordinateHelper {
	public static Vector smallestVector(Coordinate from, Coordinate to, MapDimension dimension) {
		Vector v = Coordinate.minus(to, from);
		if (dimension.isEndless()) {
			v.setX(shortenVector(dimension.getWidth(), v.getX()));
			v.setY(shortenVector(dimension.getHeight(), v.getY()));
		}
		return v;
	}
	
	private static int shortenVector(int worldWith, int vector) {
		int value = getPositivValue(vector);
		int directionMultiplicator = vector >= 0 ? -1 : 1;
		if (value > worldWith / 2) {
			return (worldWith - value) * directionMultiplicator;
		}
		return vector;
	}
	
	public static int getPositivValue(int x) {
		return x > 0 ? x : -1 * x;
	}
	
	public static void testCoordinateInRange(Coordinate c, MapDimension dimension) throws OutOfRangeException {
		if (c.getX() >= dimension.getWidth() || c.getY() >= dimension.getHeight()) {
			throw new OutOfRangeException("Coordinate " + c.toString() + " is out of worldRange");
		}
	}
	
	public static Coordinate makeCoordinateLegal(Coordinate c, boolean throwOutOfRangeException, MapDimension dimension)
			throws OutOfRangeException {
		if (dimension.isEndless()) {
			return bringCoordinateInEndlessRange(c, dimension);
		} else {
			return cutCoordinateInRange(c, throwOutOfRangeException, dimension);
		}
	}
	
	public static Coordinate cutCoordinateInRange(Coordinate c, boolean throwOutOfRangeException, MapDimension dimension) {
		Coordinate newCoordinate = new Coordinate();
		
		if (c.getX() < 0) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setX(0);
		} else if (c.getX() >= dimension.getWidth()) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setX(dimension.getWidth());
		} else {
			newCoordinate.setX(c.getX());
		}
		
		if (c.getY() < 0) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setY(0);
		} else if (c.getY() >= dimension.getHeight()) {
			if (throwOutOfRangeException)
				throw new OutOfRangeException();
			newCoordinate.setY(dimension.getHeight());
		} else {
			newCoordinate.setY(c.getY());
		}
		
		return newCoordinate;
	}
	
	public static Coordinate bringCoordinateInEndlessRange(Coordinate c, MapDimension dimension) {
		int x = c.getX();
		int y = c.getY();
		while (x < 0) {
			x = dimension.getWidth() + x; // +- = - ;-)
		}
		while (y < 0) {
			y = dimension.getHeight() + y; // +- = - ;-)
		}
		
		int newX = x % dimension.getWidth();
		int newY = y % dimension.getHeight();
		Coordinate newC = new Coordinate(newX, newY);
		return newC;
	}
}

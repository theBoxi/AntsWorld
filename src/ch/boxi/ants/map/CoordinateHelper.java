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
}

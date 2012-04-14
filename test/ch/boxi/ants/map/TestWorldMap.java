package ch.boxi.ants.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.boxi.ants.Ant;
import ch.boxi.ants.AntHill;
import ch.boxi.ants.AntRace;
import ch.boxi.ants.helper.IdGenerator;
import ch.boxi.ants.move.View;

public class TestWorldMap {
	private static final int		width			= 12;
	private static final int		height			= 10;
	private static final Coordinate	in1				= new Coordinate(1, 1);
	private static final Coordinate	in2				= new Coordinate(2, 2);
	private static final Coordinate	in3				= new Coordinate(4, 4);
	private static final Coordinate	in4				= new Coordinate(11, 9);
	private static final Coordinate	out1			= new Coordinate(15, 4);
	private static final Coordinate	out2			= new Coordinate(4, 15);
	private static final Coordinate	out3			= new Coordinate(15, 15);
	private static final Vector		smalV			= new Vector(1, 1);
	private static final Vector		bigV			= new Vector(20, 20);
	private static final Vector		xNegativV		= new Vector(-3, 0);
	private static final Vector		yNegativV		= new Vector(0, -3);
	private static final Coordinate	in1BigvEndLess	= new Coordinate(9, 1);
	private static final Coordinate	in2xNegEndLess	= new Coordinate(11, 2);
	private static final Coordinate	in2yNegEndLess	= new Coordinate(2, 9);
	private static final AntHill	hill			= new AntHill(IdGenerator.getNextUniqueID());
	private static final AntRace	race			= new AntRace(null, hill);
	private static final Ant		ant1			= new Ant("ant1", race, 100);
	private static final Ant		ant2			= new Ant("ant2", race, 100);
	private static final Ant		ant3			= new Ant("ant3", race, 100);
	private static final Ant		ant4			= new Ant("ant4", race, 100);
	private static final int		steps			= 0;
	
	private WorldMap				map;
	private WorldMap				endLessMap;
	
	@Before
	public void setUp() {
		map = new SimpleWorldMap(width, height, false);
		endLessMap = new SimpleWorldMap(width, height, true);
	}
	
	@Test
	public void testWidth() {
		assertEquals(width, map.getDimension().getWidth());
		assertEquals(height, map.getDimension().getHeight());
	}
	
	@Test
	public void testAdd() throws OutOfRangeException {
		map.add(ant1, in1);
		map.add(ant2, in2);
		map.add(ant3, in2);
		
		assertTrue(map.get(in1, steps).allValues().contains(ant1));
		assertTrue(map.get(in2, steps).allValues().contains(ant2));
		assertTrue(map.get(in2, steps).allValues().contains(ant3));
		assertEquals(1, map.get(in1, steps).size());
		assertEquals(2, map.get(in2, steps).size());
	}
	
	@Test
	public void testAddNegativ() {
		try {
			map.add(ant1, out1);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
		try {
			map.add(ant1, out2);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
		try {
			map.add(ant1, out3);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetEmpty() {
		assertEquals(0, map.get(in1, steps).size());
		assertEquals(0, map.get(out1, steps).size());
	}
	
	@Test
	public void testMove() throws OutOfRangeException {
		map.add(ant1, in1);
		map.add(ant2, in2);
		Coordinate newPoint = map.move(ant2, smalV);
		assertTrue(!map.get(in2, steps).allValues().contains(ant2));
		assertTrue(map.get(newPoint, steps).allValues().contains(ant2));
		assertEquals(newPoint.getX(), in2.getX() + smalV.getX());
		assertEquals(newPoint.getY(), in2.getY() + smalV.getY());
		
		assertTrue(map.get(in1, steps).allValues().contains(ant1));
	}
	
	@Test
	public void testMoveNegativ() throws OutOfRangeException {
		map.add(ant1, in1);
		try {
			map.move(ant1, bigV);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testEndlessMapMove() {
		endLessMap.add(ant1, in1);
		endLessMap.add(ant2, in2);
		endLessMap.add(ant3, in2);
		Coordinate c1 = endLessMap.move(ant1, bigV);
		Coordinate c2 = endLessMap.move(ant2, xNegativV);
		Coordinate c3 = endLessMap.move(ant3, yNegativV);
		
		assertEquals(in1BigvEndLess.getX(), c1.getX());
		assertEquals(in1BigvEndLess.getY(), c1.getY());
		
		assertEquals(in2xNegEndLess.getX(), c2.getX());
		assertEquals(in2xNegEndLess.getY(), c2.getY());
		
		assertEquals(in2yNegEndLess.getX(), c3.getX());
		assertEquals(in2yNegEndLess.getY(), c3.getY());
	}
	
	@Test
	public void testEndlessMapAdd() {
		endLessMap.add(ant1, in1);
		endLessMap.add(ant2, in2);
		endLessMap.add(ant3, in2);
		
		assertTrue(endLessMap.get(in1, steps).allValues().contains(ant1));
		assertTrue(endLessMap.get(in2, steps).allValues().contains(ant2));
		assertTrue(endLessMap.get(in2, steps).allValues().contains(ant3));
		assertEquals(1, endLessMap.get(in1, steps).size());
		assertEquals(2, endLessMap.get(in2, steps).size());
	}
	
	@Test
	public void testEndlessMapAddNegativ() {
		try {
			endLessMap.add(ant1, out1);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
		try {
			endLessMap.add(ant1, out2);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
		try {
			endLessMap.add(ant1, out3);
			assertTrue(false);
		} catch (OutOfRangeException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testGetCoordinatesFor() {
		SimpleWorldMap map = new SimpleWorldMap(width, height, false);
		assertEquals(1, map.getCoordinatesFor(new Coordinate(0, 0), 0).size());
		assertEquals(3, map.getCoordinatesFor(new Coordinate(0, 0), 1).size());
		assertEquals(5, map.getCoordinatesFor(new Coordinate(1, 1), 1).size());
		assertEquals(13, map.getCoordinatesFor(new Coordinate(2, 2), 2).size());
		assertEquals(12, map.getCoordinatesFor(new Coordinate(1, 2), 2).size());
		
		map = new SimpleWorldMap(width, height, true);
		assertEquals(1, map.getCoordinatesFor(new Coordinate(0, 0), 0).size());
		assertEquals(5, map.getCoordinatesFor(new Coordinate(0, 0), 1).size());
		assertEquals(5, map.getCoordinatesFor(new Coordinate(1, 1), 1).size());
		assertEquals(13, map.getCoordinatesFor(new Coordinate(2, 2), 2).size());
		assertEquals(13, map.getCoordinatesFor(new Coordinate(1, 2), 2).size());
	}
	
	@Test
	public void testGetViewEndingMap() {
		map.add(ant1, in1);
		map.add(ant2, in2);
		map.add(ant3, in3);
		map.add(ant4, in4);
		
		View view = map.get(in2, 2);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertFalse(view.allValues().contains(ant3));
		assertFalse(view.allValues().contains(ant4));
		
		view = map.get(in2, 4);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertTrue(view.allValues().contains(ant3));
		assertFalse(view.allValues().contains(ant4));
		
		view = map.get(in2, 6);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertTrue(view.allValues().contains(ant3));
		assertFalse(view.allValues().contains(ant4));
	}
	
	@Test
	public void testGetViewEndlessMap() {
		endLessMap.add(ant1, in1);
		endLessMap.add(ant2, in2);
		endLessMap.add(ant3, in3);
		endLessMap.add(ant4, in4);
		
		View view = endLessMap.get(in2, 2);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertFalse(view.allValues().contains(ant3));
		assertFalse(view.allValues().contains(ant4));
		
		view = endLessMap.get(in2, 4);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertTrue(view.allValues().contains(ant3));
		assertFalse(view.allValues().contains(ant4));
		
		view = endLessMap.get(in2, 6);
		assertTrue(view.allValues().contains(ant1));
		assertTrue(view.allValues().contains(ant2));
		assertTrue(view.allValues().contains(ant3));
		assertTrue(view.allValues().contains(ant4));
	}
}

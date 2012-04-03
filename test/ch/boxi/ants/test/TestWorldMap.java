package ch.boxi.ants.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.boxi.ants.Ant;
import ch.boxi.ants.AntHill;
import ch.boxi.ants.AntRace;
import ch.boxi.ants.helper.IdGenerator;
import ch.boxi.ants.map.Coordinate;
import ch.boxi.ants.map.OutOfRangeException;
import ch.boxi.ants.map.SimpleWorldMap;
import ch.boxi.ants.map.Vector;
import ch.boxi.ants.map.WorldMap;

public class TestWorldMap {
	private static final int		width			= 12;
	private static final int		height			= 10;
	private static final Coordinate	in1				= new Coordinate(1, 1);
	private static final Coordinate	in2				= new Coordinate(2, 2);
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
		assertEquals(width, map.getWidth());
		assertEquals(height, map.getHeight());
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
}

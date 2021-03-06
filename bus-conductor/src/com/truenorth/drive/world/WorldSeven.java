package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

/**
 * World Seven, subclass of world with specified values
 * 
 * Hours Spent: 0.25 hours
 * 
 * June 12th: Created file, with boundaries, Min
 * June 14th: Final Comments, Ishan
 * 
 * @author Min and Ishan
 * @since June 12th
 */
public class WorldSeven extends World {
	
	/**
	 * Creates the World, with the specified boundaries and other values
	 * @author Min
	 * @since June 12th
	 */
	public WorldSeven() {
		super(-1452, -7797, 6, -489, -8020, 941);
		// Boundaries
		boundary.add(new Integer[] {-2000, -6800, 1700, 1});
		boundary.add(new Integer[] {180, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-680, -9000, 2000, 4});
		// Bus end point
		busStop = new Point(0,-6400);
	}
	
}

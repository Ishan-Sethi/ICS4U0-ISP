package com.truenorth.drive.world;

import java.awt.Point;
import com.truenorth.drive.World;

public class WorldThirteen extends World {

	public WorldThirteen() {
		super(-1450, -5800, 12, -489, -8020, 941);
		boundary.add(new Integer[] {-2000, -5382, 1700, 1});
		boundary.add(new Integer[] {180, -9000, 2000, 2});
		boundary.add(new Integer[] {-2000, 940, 1700, 3});
		boundary.add(new Integer[] {-680, -9000, 2000, 4});
		busStop = new Point(0, -4955);
	}
}

package com.truenorth.puzzles.level;

import java.awt.Color;

import com.truenorth.puzzles.Level;
import com.truenorth.riders.*;

public class LevelTwoNine extends Level {

	public LevelTwoNine() {
		super();
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		immoveable.add(new Parent(2, 0, 2, 1));
		immoveable.add(new Children(2, 1, 2));
		immoveable.add(new Parent(2, 4, 3, 2));
		immoveable.add(new Children(2, 3, 3));
		immoveable.add(new Children(2, 5, 3));
		immoveable.add(new YoungAdult(1, 7));
		immoveable.add(new YoungAdult(3, 7));
		immoveable.add(new YoungAdult(2, 8));
		immoveable.add(new YoungAdult(0, 9));
		immoveable.add(new YoungAdult(4, 9));
		immoveable.add(new YoungAdult(2, 10));
		
		moveable.add(new Student(0, 0, 2, Color.RED));
		moveable.add(new Student(3, 0, 4, Color.ORANGE));
		moveable.add(new Student(0, 2, 2, Color.YELLOW));
		moveable.add(new Student(3, 2, 4, Color.GREEN));
		moveable.add(new Student(0, 4, 2, Color.CYAN));
		moveable.add(new Student(3, 4, 4, Color.BLUE));
		moveable.add(new Elderly(1, 6, Color.MAGENTA));
		moveable.add(new Elderly(3, 6, Color.PINK));
		
		fillGrid();
	}
}

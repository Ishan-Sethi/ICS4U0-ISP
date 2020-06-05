package puzzles.level;

import puzzles.Screen;
import riders.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import game.GamePuzzle;

public class LevelOneFive extends Screen{
	
	public LevelOneFive() {
		super();
		this.hasTutorial = true;
	}
	
	@Override
	public void showTutorial(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		int boxX = 0, boxY = 0, boxW = 0, boxH = 0;
		int contourX = 32, contourY = 32, contourW = 32, contourH = 32;
		boolean contour = false;
		String tutText = "";
		
		switch(tutorialPage) {
			case 0:
				boxX = 320;
				boxY = 290;
				boxW = 160;
				boxH = 60;
				tutText = "Its ramp time.";
				break;
			case 1:
				boxX = 90;
				boxY = 230;
				boxW = 350;
				boxH = 70;
				tutText = "The Disabled takes up two spots on the bus,\nonly one being the person themselves.";
				contour = true;
				contourX *= 14;
				contourY *= 7;
				contourW *= 3;
				contourH *= 3;
				g2d.setColor(new Color(255,0,0,140));
				g2d.fillRect(contourX, contourY+32, contourW, 32);
				g2d.fillRect(contourX+32, contourY, 32, 32);
				g2d.fillRect(contourX+32, contourY+64, 32, 32);
				break;
			case 2:
				boxX = 120;
				boxY = 140;
				boxW = 320;
				boxH = 60;
				tutText = "Like the Elderly, the Disabled can only\nuse the blue seats.";
				contour = true;
				contourX *= 8;
				contourY *= 8;
				contourW *= 5;
				contourH *= 4;
				g2d.setColor(new Color(0,255,0,140));
				g2d.fillRect(contourX, contourY, 32, contourH);
				g2d.fillRect(contourX+contourW-32, contourY, 32, contourH);
				break;
			case 3:
				boxX = 160;
				boxY = 330;
				boxW = 360;
				boxH = 60;
				tutText = "HEYYY! They aren't keeping distance?\nOr... are they?";
				contour = true;
				contourX *= 9;
				contourY *= 14;
				contourW *= 2;
				contourH *= 2;
				break;
			default:
				hasTutorial = false;
		}
		
		g2d.setColor(new Color(0,0,0,160));
		if(contour) {
			g2d.fillRect(0, 0, GamePuzzle.WIDTH, contourY);
			g2d.fillRect(0, contourY, contourX, contourH);
			g2d.fillRect(contourX+contourW, contourY, GamePuzzle.WIDTH-(contourX+contourW), contourH);
			g2d.fillRect(0, contourY+contourH, GamePuzzle.WIDTH, GamePuzzle.HEIGHT-(contourY+contourH));
		}
		else {
			g2d.fillRect(0, 0, GamePuzzle.WIDTH, GamePuzzle.HEIGHT);
		}
		showBox(g2d, boxX, boxY, boxW, boxH, tutText.split("\n"));
	}
	
	@Override
	public void resetGrid() {
		super.resetGrid();
		
		Passenger[] g1 = {null, new YoungAdult(0,0,null), new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		Passenger[] g2 = {new YoungAdult(0,0,null), new YoungAdult(0,0,null)};
		
		immoveable.add(new YoungAdult(3, 1));
		immoveable.add(new YoungAdult(1, 3));
		immoveable.add(new Parent(0, 5, 2, 1));
		immoveable.add(new Pregnant(4, 5));
		immoveable.add(new Children(0, 6, 2));
		immoveable.add(new Grouped(g1, 1, 6, 4));
		immoveable.add(new YoungAdult(4, 7));
		immoveable.add(new Student(4, 8, 1));
		immoveable.add(new Student(0, 9, 2));
		immoveable.add(new Grouped(g2, 2, 10, 5));
		
		moveable.add(new Elderly(0, 0, Color.RED));
		moveable.add(new Pregnant(2, 0, Color.BLUE));
		moveable.add(new Disabled(0, 2, 2, Color.MAGENTA));
		moveable.add(new Disabled(2, 2, 2, Color.YELLOW));
		moveable.add(new YoungAdult(0, 5, Color.GREEN));
		moveable.add(new YoungAdult(2, 5, Color.PINK));
		moveable.add(new YoungAdult(0, 7, Color.CYAN));
		moveable.add(new YoungAdult(2, 7, Color.ORANGE));
		
		fillGrid();
	}
}
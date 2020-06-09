package drive;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import game.Loader;


public abstract class World {
	protected Point startPos;
	protected BufferedImage map;
	protected ArrayList<Integer[]> boundary;
	protected Point busStop;
	private int entityDelay;
	private int spawnX;
	private int spawnYTop;
	private int spawnYBot;
	
	public World(int x, int y, int imageID, int spawnX, int spawnYTop, int spawnYBot) {
		this.startPos = new Point(x, y);
		this.boundary = new ArrayList<Integer[]>();
		this.map = getImage(imageID);
		this.entityDelay = 0;
		this.spawnX = spawnX;
		this.spawnYTop = spawnYTop;
		this.spawnYBot = spawnYBot;
	}
	
	public void update(ArrayList<Entity> entities) {
		entityDelay++;
		
		if (entityDelay == 2*60) {
			if (Math.random() >= 0.5) {
				entities.add(new Car(spawnX, spawnYTop+10, 0d, 5d));
			} else {
				entities.add(new Car(spawnX+155, spawnYTop+10, 0d, 5d));
			}

			if (Math.random() >= 0.5) {
				entities.add(new Car(spawnX+325, spawnYBot-10, 0d, -5d));
			} else {
				entities.add(new Car(spawnX+470, spawnYBot-10, 0d, -5d));
			}
			entityDelay = 0;
		}
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if ((e.getCenter().y >= spawnYBot && e.getYVel() > 0)
					|| (e.getCenter().y <= spawnYTop && e.getYVel() < 0)
					|| (e.getCenter().x <= spawnX-600) || (e.getCenter().x >= spawnX+1770)) {
				entities.remove(i);
			}
		}
	}
	
	public void render(Graphics2D g2d) {
		g2d.drawImage(map, startPos.x - BusState.c.getXPos(), startPos.y - BusState.c.getYPos(), null);
		
		if (BusState.debug) {
			int xOffset = -BusState.c.getXPos();
			int yOffset = -BusState.c.getYPos();
			g2d.setColor(Color.black);
			for (Integer[] r : boundary) {
				g2d.setColor(Color.darkGray);
				if(r[3] % 2 == 0)
					g2d.drawLine(r[0]+xOffset, r[1]+yOffset, r[0]+xOffset, r[2]+yOffset); //draws a line up and down
				else
					g2d.drawLine(r[0]+xOffset, r[1]+yOffset, r[2]+xOffset, r[1]+yOffset); //draws a line left to right
			}
			busStop.translate(xOffset, yOffset);
			g2d.drawRect((int)busStop.getX(), (int)busStop.getY(), 5, 5);
			busStop.translate(-xOffset, -yOffset);
		}
	}

	public Point getStartPos() {
		return startPos;
	}

	public ArrayList<Integer[]> getBoundary() {
		return boundary;
	}
	
	public Point getBusStop() {
		return busStop;
	}
	
	private BufferedImage getImage(int imageID) {
		if(imageID == 1) {
			return Loader.WORLD1;
		}
		return null;
	}
}

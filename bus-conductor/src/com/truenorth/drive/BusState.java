package com.truenorth.drive;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import com.truenorth.drive.world.*;
import com.truenorth.game.Camera;
import com.truenorth.game.Game;
import com.truenorth.game.Loader;
import com.truenorth.game.states.States;

public class BusState implements States{
	public static boolean debug;
	private final Camera c;
	private final int WORLDS_NUM = 2;
	private final World[] worlds = new World[WORLDS_NUM];
	private ArrayList<Entity> entities;
	private Bus b;
	private int currentWorld;
	private boolean onStop;
	
	private float alpha;
	
	public BusState() {
		this.c = new Camera();
		this.entities = new ArrayList<Entity>();
		this.b = new Bus();
		this.worlds[0] = new WorldOne();
		this.worlds[1] = new WorldTwo();
		this.currentWorld = 1;
		this.onStop = false;
		this.alpha = 0.5f;
	}
	
	public void update() {
		c.update(b.calculateCenter().x, b.calculateCenter().y);
		b.update();
    
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();

			if (entities.get(i).getCenter().distance(b.getCenter()) <= 120) {
				entities.get(i).setColor(Color.green);
				if (b.isColliding(entities.get(i))) {
					entities.get(i).setColor(Color.red);
					((Car) entities.get(i)).setCrashed(true);
					if (Math.abs(b.getXVel()) < 0.9)
						entities.get(i).setXVel(-entities.get(i).getXVel()*3/4);
					else
						entities.get(i).setXVel(b.getXVel() * 2);
					if (Math.abs(b.getYVel()) < 0.9)
						entities.get(i).setYVel(-entities.get(i).getYVel()*3/4);
					else
						entities.get(i).setYVel(b.getYVel() * 2);
					if (entities.get(i).getXVel() != 0 || entities.get(i).getYVel() != 0)
						entities.get(i).setAngleVel(Math.round(Math.random()) * 4 - 2);
				}
			} else {
				if (!entities.get(i).getColor().equals(Color.blue)) {
					entities.get(i).setColor(Color.blue);
				}
			}
		}

		worlds[currentWorld].update(entities);
		
		for (int i = 0; i < worlds[currentWorld].getBoundary().size(); i++) {
			Integer[] boundP = worlds[currentWorld].getBoundary().get(i);
			boolean ahead = (boundP[3] == 1 && b.getCenter().getY() <= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 2 && b.getCenter().getX() >= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])))
					|| (boundP[3] == 3 && b.getCenter().getY() >= boundP[1] && (b.getCenter().getX() <= Math.max(boundP[0], boundP[2]) && b.getCenter().getX() >= Math.min(boundP[0], boundP[2])))
					|| (boundP[3] == 4 && b.getCenter().getX() <= boundP[0] && (b.getCenter().getY() <= Math.max(boundP[1], boundP[2]) && b.getCenter().getY() >= Math.min(boundP[1], boundP[2])));
			b.setOutside(ahead);
			if(ahead) {
				break;
			}
		}
		
		
	}
	
	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(29, 174, 5));
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		worlds[currentWorld].render(g2d, c.getXPos(), c.getYPos());

		b.draw(g2d, c.getXPos(), c.getYPos());
		int crashedEntities = 0;
		int drawnEntities = 0;
		for (Entity e : entities) {
			if (e.crashed) {
				crashedEntities++;
			}
			if (Math.abs(e.getCenter().distance(b.getCenter())) <= 578) {
				if (e.crashed)
					g2d.setColor(Color.red);
				else
					g2d.setColor(Color.green);
				if (debug)
					g2d.drawLine(b.getCenter().x -c.getXPos(), b.getCenter().y - c.getYPos(),
							e.getCenter().x - c.getXPos(), e.getCenter().y - c.getYPos());
				e.draw(g2d, c.getXPos(), c.getYPos());
				drawnEntities++;
			}
		}
		
		if (b.isOutside()) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			g2d.drawImage(Loader.WARNING_IMAGE, 0, 0, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		if (debug) {
			g2d.setColor(Color.orange);
			g2d.drawLine(b.center.x - c.getXPos(), b.center.y - c.getYPos(), worlds[currentWorld].getBusStop().x - c.getXPos(), worlds[currentWorld].getBusStop().y - c.getYPos());
			g2d.setColor(Color.black);
			g2d.drawString("Entity Count: " + entities.size(), 10, 140);
			g2d.drawString("Entities drawn: " + drawnEntities, 10, 152);
			g2d.drawString("Entities crashed: " + crashedEntities, 10, 164);
		}
		if (b.center.distance(worlds[currentWorld].getBusStop()) >= 450) {
			AffineTransform temp = g2d.getTransform();
			double arrowAngle = Math.toDegrees(Math.atan((b.center.y-worlds[currentWorld].getBusStop().getY())/(b.center.x - worlds[currentWorld].getBusStop().getX())));
			if (b.center.getX() >= worlds[currentWorld].getBusStop().getX()) {
				arrowAngle += 270;
			} else {
				arrowAngle += 90;
			}
			g2d.rotate(Math.toRadians(arrowAngle), b.getCenter().getX() - c.getXPos(), b.getCenter().getY() - c.getYPos());
			g2d.translate(0, -150);
			g2d.setColor(Color.magenta);
			g2d.drawImage(Loader.ARROW, Game.WIDTH/2 - 30, Game.HEIGHT/2 - 40, null);
			g2d.setTransform(temp);
			System.out.println(arrowAngle);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			debug = !debug;
		}
		else if(b.getCenter().distance(worlds[currentWorld].getBusStop()) <= 100 && e.getKeyCode() == KeyEvent.VK_ENTER) {
			onStop = true;
		}
		b.processMovement(e);
	}

	public void keyReleased(KeyEvent e) {
		b.unholdKey(e);
	}
	
	public void resetHold() {
		b.resetHold();
	}
	
	public boolean isOnStop(){
		return onStop;
	}
	
	public void setOnStop(boolean onStop){
		this.onStop = onStop;
	}
	
	
	
	//here for testing
	public Bus getBus() {
		return b;
	}
}

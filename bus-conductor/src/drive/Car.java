package drive;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import game.Loader;

public class Car extends Entity {
	BufferedImage carSprite;

	public Car(int xPos, int yPos, double xVel, double yVel) {
		super(xPos, yPos, 64, 96);
		this.xVel = xVel;
		this.yVel = yVel;
		buildUp = 1;
		entityPoints[0] = new Point(center.x - WIDTH / 2, center.y - HEIGHT / 2);
		entityPoints[1] = new Point(center.x + WIDTH / 2, center.y - HEIGHT / 2);
		entityPoints[2] = new Point(center.x + WIDTH / 2, center.y + HEIGHT / 2);
		entityPoints[3] = new Point(center.x - WIDTH / 2, center.y + HEIGHT / 2);
		entityBody = createPolygon(entityPoints);
		this.carSprite = getImage((int) (Math.random() * 8 + 1));
		if (yVel < 0) {
			angle = 180;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		entityBody = createPolygon(entityPoints);
		entityBody.translate((int) (-BusLevel.c.getXPos()), (int) (-BusLevel.c.getYPos()));
		g2d.setColor(c);
		g2d.drawImage(carSprite, center.x - WIDTH / 2 - (int) (BusLevel.c.getXPos()), center.y - HEIGHT / 2 - (int) (BusLevel.c.getYPos()), null);
		if (BusLevel.debug)
			g2d.fill(entityBody);

	}

	@Override
	public void update() {
		center = calculateCenter();
		rotatePointMatrix(getOriginalPoints(), angle, entityPoints);
		calculateVel();
		for (int i = 0; i < 4; i++) {
			entityPoints[i].translate((int) xVel, (int) yVel);
		}
		entityBody = createPolygon(entityPoints);
	}

	@Override
	public void calculateVel() {
		xVel *= buildUp;
		yVel *= buildUp;
	}

	/**
	 * @param crashed the crashed to set
	 */
	public void setCrashed(boolean crashed) {
		this.crashed = crashed;
		buildUp = 0.99;
	}

	private BufferedImage getImage(int imageID) {
		System.out.println(imageID);
		if (imageID == 1) {
			return Loader.CAR_SPRITE1;
		} else if (imageID == 2) {
			return Loader.CAR_SPRITE2;
		} else if (imageID == 3) {
			return Loader.CAR_SPRITE3;
		} else if (imageID == 4) {
			return Loader.CAR_SPRITE4;
		} else if (imageID == 5) {
			return Loader.CAR_SPRITE5;
		} else if (imageID == 6) {
			return Loader.CAR_SPRITE6;
		} else if (imageID == 7) {
			return Loader.CAR_SPRITE7;
		} else if (imageID == 8) {
			return Loader.CAR_SPRITE8;
		}
		return null;
	}

}

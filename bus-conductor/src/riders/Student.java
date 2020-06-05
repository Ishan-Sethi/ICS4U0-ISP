package riders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Student extends Passenger{
	
	protected int rotation;
	protected int offX;
	protected int offY;
	protected int shiftX;
	protected int shiftY;
	
	public Student(int orderX, int orderY,  int rotation, Color cl) {
		super(4, rotation, 1, orderX, orderY, cl);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
	}
	
	public Student(int xPos, int yPos, int rotation) {
		super(4, rotation, 1, xPos, yPos);
		this.rotation = rotation;
		this.shiftX = (rotation == 4) ? 1 : 0;
		this.shiftY = (rotation == 3) ? 1 : 0;
		setRotationValue();
	}
	
	@Override
	public void render(Graphics g, Integer[][] grid) {
		int xPosNew; 
		int yPosNew;
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(inGrid) {
			int tempXPos = xPos+shiftX;
			int tempYPos = yPos+shiftY;
			if((tempXPos == 0 && tempYPos <= 6) 
					|| ((tempXPos == 0 || tempXPos == 4) && tempYPos == 8) 
					|| (tempXPos == 4 && tempYPos <= 5) 
					|| (tempYPos == 10)) {
				this.sprite = readImage(4, rotation+4);
			}
			else {
				this.sprite = readImage(4, rotation);
			}
			
			xPosNew = SPRITE_SIZE*xPos+OFFSET_X;
			yPosNew = SPRITE_SIZE*yPos+OFFSET_Y;
			if(selected) {
				floating += (floating >= 6.28) ? -6.28 : 0.02d;
				yPosNew += (int)(Math.sin(floating)*5);
			}
			highlight(g2d, xPosNew, yPosNew);
			g2d.drawImage(sprite, xPosNew, yPosNew, null);
		}
		else {
			xPosNew = SPRITE_SIZE*orderX+ORDERED_X;
			yPosNew = SPRITE_SIZE*orderY+ORDERED_Y;
			highlight(g2d, xPosNew, yPosNew);
			g2d.drawImage(sprite, xPosNew, yPosNew, null);
		}
		if((!inGrid || selected) || seperate)
			drawTag(g2d, xPosNew, yPosNew);
	}
	
	@Override
	public boolean move(Integer[][] grid, KeyEvent e) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		if (xPos > 0 && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)) {
			xPos -= (tempXPos == 0 || (rotation == 4 && tempXPos == 1)) ? 0 : 1;
		}
		else if (xPos < MAX_X && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			xPos += (tempXPos == MAX_X || (rotation == 2 && tempXPos == MAX_X-1)) ? 0 : 1;
		}
		else if (yPos > 0 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)) {
			yPos -= (tempYPos == 0 || (rotation == 3 && tempYPos == 1)) ? 0 : 1;
		}
		else if (yPos < MAX_Y && (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)) {
			yPos += (tempYPos == MAX_Y || (rotation == 1 && tempYPos == MAX_Y-1)) ? 0 : 1;
		}
		return false;
	}
	
	@Override
	public boolean isCorrect(Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		boolean surrounding = (grid[tempXPos][tempYPos] == 0 || ((!selected && grid[tempXPos][tempYPos] == id)))
							&& (tempXPos == 0 || grid[tempXPos-1][tempYPos] <= 0 || (inGroup && grid[tempXPos-1][tempYPos] == id))
							&& (tempXPos == MAX_X || grid[tempXPos+1][tempYPos] <= 0 || (inGroup && grid[tempXPos+1][tempYPos] == id))
							&& (tempYPos == 0 || belowWindow(tempXPos,tempYPos) || grid[tempXPos][tempYPos-1] <= 0 || (inGroup && grid[tempXPos][tempYPos-1] == id))
							&& (tempYPos == MAX_Y || aboveWindow(tempXPos,tempYPos) || grid[tempXPos][tempYPos+1] <= 0 || (inGroup && grid[tempXPos][tempYPos+1] == id));
		boolean noOverlap = !selected
							|| (rotation == 1 && tempYPos < MAX_Y && (grid[tempXPos][tempYPos+1] != BAGGAGE && grid[tempXPos][tempYPos+1] >= CHILD_SPACE))
							|| (rotation == 2 && tempXPos < MAX_X && (grid[tempXPos+1][tempYPos] != BAGGAGE && grid[tempXPos+1][tempYPos] >= CHILD_SPACE))
							|| (rotation == 3 && tempYPos > 0 && (grid[tempXPos][tempYPos-1] != BAGGAGE && grid[tempXPos][tempYPos-1] >= CHILD_SPACE))
							|| (rotation == 4 && tempXPos > 0 && (grid[tempXPos-1][tempYPos] != BAGGAGE && grid[tempXPos-1][tempYPos] >= CHILD_SPACE));
		boolean notOnWindow = (rotation != 1 || !aboveWindow(tempXPos,tempYPos)) 
							&& (rotation != 3 || !belowWindow(tempXPos,tempYPos));
		return surrounding && noOverlap && notOnWindow;
	}
	
	@Override
	public void fillDistance (Integer[][] grid) {
		int tempXPos = xPos+shiftX;
		int tempYPos = yPos+shiftY;
		
		grid[tempXPos][tempYPos] = id;
		if(!aboveWindow(tempXPos,tempYPos) && tempYPos < MAX_Y && grid[tempXPos][tempYPos+1] <= 0 && grid[tempXPos][tempYPos+1] != -2 && (!inGroup || grid[tempXPos][tempYPos+1] != id))
			grid[tempXPos][tempYPos+1] = (rotation == 1) ? BAGGAGE : EMPTY;
		if(tempXPos < MAX_X && grid[tempXPos+1][tempYPos] <= 0 && grid[tempXPos+1][tempYPos] != -2 && (!inGroup || grid[tempXPos+1][tempYPos] != id))
			grid[tempXPos+1][tempYPos] = (rotation == 2) ? BAGGAGE : EMPTY;
		if(!belowWindow(tempXPos,tempYPos) && tempYPos > 0 && grid[tempXPos][tempYPos-1] <= 0 && grid[tempXPos][tempYPos-1] != -2 && (!inGroup || grid[tempXPos][tempYPos-1] != id))
			grid[tempXPos][tempYPos-1] = (rotation == 3) ? BAGGAGE : EMPTY;	
		if(tempXPos > 0 && grid[tempXPos-1][tempYPos] <= 0 && grid[tempXPos-1][tempYPos] != -2 && (!inGroup || grid[tempXPos-1][tempYPos] != id))
			grid[tempXPos-1][tempYPos] = (rotation == 4) ? BAGGAGE : EMPTY;	
	}
	
	@Override
	protected void highlight(Graphics2D g, int xPosNew, int yPosNew) {
		if(selected) {
			if(inGrid) {
				if(placeable)
					g.setColor(new Color(25, 255, 25, 120));
				else
					g.setColor(new Color(255, 25, 25, 120));
			}
			else
				g.setColor(new Color(255, 127, 156, 120));
			g.fillRoundRect(xPosNew, yPosNew, SPRITE_SIZE*(Math.abs(offX)+1), SPRITE_SIZE*(Math.abs(offY)+1), 20, 20);
		}
	}
	
	@Override
	protected void drawTag(Graphics2D g, int xPos, int yPos) {
		g.setColor(cl);
		g.fillOval(xPos+(shiftX*32), yPos+(shiftY*32), 10, 10);
	}
	
	@Override
	protected double rotationVal(int x, int y) {
		return 0d;
	}
	
	protected void setRotationValue() {
		this.offX = 0;
		this.offY = 0;
		
		if(rotation == 1)
			this.offY = 1;
		else if (rotation == 2)
			this.offX = 1;
		else if (rotation == 3)
			this.offY = -1;
		else if (rotation == 4)
			this.offX = -1;
	}
}
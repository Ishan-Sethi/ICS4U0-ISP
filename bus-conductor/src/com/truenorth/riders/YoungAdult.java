package com.truenorth.riders;

import java.awt.Color;

public class YoungAdult extends Passenger{
	
	/**
	 * {@inheritDoc}
	 */
	public YoungAdult(int orderX, int orderY, Color cl) {
		super(1, 0, 1, orderX, orderY, cl);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public YoungAdult(int xPos, int yPos) {
		super(1, 0, 1, xPos, yPos);
	}
}

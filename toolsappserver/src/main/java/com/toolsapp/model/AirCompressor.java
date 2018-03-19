package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class AirCompressor extends PowerTool {

	private int tankSize;
	private double pressureRating;
	
	public AirCompressor () {
	}

	public AirCompressor (PowerTool t) {
		super(t);
	}

	public int getTankSize() {
		return tankSize;
	}

	public void setTankSize(int tankSize) {
		this.tankSize = tankSize;
	}

	public double getPressureRating() {
		return pressureRating;
	}

	public void setPressureRating(double pressureRating) {
		this.pressureRating = pressureRating;
	}
	
}

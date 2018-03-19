package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Mixer extends PowerTool {

	private int motorRating;
	private double drumSize;
	
	public Mixer () {
	}

	public Mixer (PowerTool t) {
		super(t);
	}

	public int getMotorRating() {
		return motorRating;
	}

	public void setMotorRating(int motorRating) {
		this.motorRating = motorRating;
	}

	public double getDrumSize() {
		return drumSize;
	}

	public void setDrumSize(double drumSize) {
		this.drumSize = drumSize;
	}
	

}

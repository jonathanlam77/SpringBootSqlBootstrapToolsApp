package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Generator extends PowerTool {

	private double powerRating;
	
	public Generator () {
	}

	public Generator (PowerTool t) {
		super(t);
	}

	public double getPowerRating() {
		return powerRating;
	}

	public void setPowerRating(double powerRating) {
		this.powerRating = powerRating;
	}
	

}

package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Gun extends Tool {

	private int capacity;
	private double gaugeRating;
	
	public Gun() {
	}
	
	public Gun (Tool t) {
		super(t);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getGaugeRating() {
		return gaugeRating;
	}

	public void setGaugeRating(double gaugeRating) {
		this.gaugeRating = gaugeRating;
	}
	

}

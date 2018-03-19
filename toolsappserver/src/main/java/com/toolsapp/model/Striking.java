package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Striking extends Garden {
	
	private double headWeight;
	
	public Striking() {
	}
	
	public Striking (Tool t) {
		super(t);
	}

	public double getHeadWeight() {
		return headWeight;
	}

	public void setHeadWeight(double headWeight) {
		this.headWeight = headWeight;
	}

}

package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Digger extends Garden {

	private double bladeWidth;
	private double bladeLength;
	
	public Digger() {
	}
	
	public Digger (Tool t) {
		super(t);
	}

	public double getBladeWidth() {
		return bladeWidth;
	}

	public void setBladeWidth(double bladeWidth) {
		this.bladeWidth = bladeWidth;
	}

	public double getBladeLength() {
		return bladeLength;
	}

	public void setBladeLength(double bladeLength) {
		this.bladeLength = bladeLength;
	}
	
	
}

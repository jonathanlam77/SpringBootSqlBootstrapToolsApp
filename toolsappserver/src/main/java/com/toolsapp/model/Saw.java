package com.toolsapp.model;

public class Saw extends PowerTool {
	
	private double bladeSize;

	public Saw () {
	}

	public Saw (PowerTool t) {
		super(t);
	}
	
	public double getBladeSize() {
		return bladeSize;
	}

	public void setBladeSize(double bladeSize) {
		this.bladeSize = bladeSize;
	}
		
}

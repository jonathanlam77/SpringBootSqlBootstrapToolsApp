package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Pruner extends Garden {
	
	private String bladeMaterial;
	private double bladeLength;
	
	public Pruner() {
	}
	
	public Pruner (Tool t) {
		super(t);
	}

	public String getBladeMaterial() {
		return bladeMaterial;
	}

	public void setBladeMaterial(String bladeMaterial) {
		this.bladeMaterial = bladeMaterial;
	}

	public double getBladeLength() {
		return bladeLength;
	}

	public void setBladeLength(double bladeLength) {
		this.bladeLength = bladeLength;
	}

}

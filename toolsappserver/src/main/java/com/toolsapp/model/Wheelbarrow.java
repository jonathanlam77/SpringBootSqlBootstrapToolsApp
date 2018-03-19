package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Wheelbarrow extends Garden {

	private double binVolume;
	private String binMaterial;
	private int wheelCount;
	
	public Wheelbarrow() {
	}
	
	public Wheelbarrow (Tool t) {
		super(t);
	}

	public double getBinVolume() {
		return binVolume;
	}

	public void setBinVolume(double binVolume) {
		this.binVolume = binVolume;
	}

	public String getBinMaterial() {
		return binMaterial;
	}

	public void setBinMaterial(String binMaterial) {
		this.binMaterial = binMaterial;
	}

	public int getWheelCount() {
		return wheelCount;
	}

	public void setWheelCount(int wheelCount) {
		this.wheelCount = wheelCount;
	}
	
	
}

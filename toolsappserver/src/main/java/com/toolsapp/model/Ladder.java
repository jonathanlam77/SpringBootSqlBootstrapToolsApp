package com.toolsapp.model;

public class Ladder extends Tool{

	private int stepCount;
	private int weightCapacity;	
	
	public Ladder() {
	}
	
	public Ladder (Tool t) {
		super(t);
	}
	
	public int getStepCount() {
		return stepCount;
	}
	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}
	public int getWeightCapacity() {
		return weightCapacity;
	}
	public void setWeightCapacity(int weightCapacity) {
		this.weightCapacity = weightCapacity;
	}
	
}

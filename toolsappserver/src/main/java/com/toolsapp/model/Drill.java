package com.toolsapp.model;

public class Drill extends PowerTool {

	private boolean adjustableClutch;
	private int minTorqueRating;
	private int maxTorqueRating;
	
	public Drill () {
	}

	public Drill (PowerTool t) {
		super(t);
	}

	public boolean isAdjustableClutch() {
		return adjustableClutch;
	}

	public void setAdjustableClutch(boolean adjustableClutch) {
		this.adjustableClutch = adjustableClutch;
	}

	public int getMinTorqueRating() {
		return minTorqueRating;
	}

	public void setMinTorqueRating(int minTorqueRating) {
		this.minTorqueRating = minTorqueRating;
	}

	public int getMaxTorqueRating() {
		return maxTorqueRating;
	}

	public void setMaxTorqueRating(int maxTorqueRating) {
		this.maxTorqueRating = maxTorqueRating;
	}
	
}

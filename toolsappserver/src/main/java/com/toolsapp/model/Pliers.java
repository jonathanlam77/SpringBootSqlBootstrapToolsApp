package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Pliers extends Tool {

	private boolean adjustable;
	
	public Pliers() {
	}
	
	public Pliers (Tool t) {
		super(t);
	}

	public boolean isAdjustable() {
		return adjustable;
	}

	public void setAdjustable(boolean adjustable) {
		this.adjustable = adjustable;
	}
	

}

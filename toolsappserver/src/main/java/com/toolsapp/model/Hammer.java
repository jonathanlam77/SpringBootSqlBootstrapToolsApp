package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Hammer extends Tool {

	private boolean antiVibration;
	
	public Hammer() {
	}
	
	public Hammer (Tool t) {
		super(t);
	}

	public boolean isAntiVibration() {
		return antiVibration;
	}

	public void setAntiVibration(boolean antiVibration) {
		this.antiVibration = antiVibration;
	}
	

}

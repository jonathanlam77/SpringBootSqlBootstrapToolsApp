package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Sander extends PowerTool {

	private boolean dustBag;
	
	public Sander () {
	}

	public Sander (PowerTool t) {
		super(t);
	}

	public boolean isDustBag() {
		return dustBag;
	}

	public void setDustBag(boolean dustBag) {
		this.dustBag = dustBag;
	}
	
	
}

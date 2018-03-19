package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class StepLadder extends Ladder {

	private boolean pailShelf;
	
	public StepLadder() {
	}
	
	public StepLadder (Tool t) {
		super(t);
	}
	public boolean isPailShelf() {
		return pailShelf;
	}
	public void setPailShelf(boolean pailShelf) {
		this.pailShelf = pailShelf;
	}
	
}

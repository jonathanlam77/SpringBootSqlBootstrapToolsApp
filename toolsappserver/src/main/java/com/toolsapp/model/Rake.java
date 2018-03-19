package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Rake extends Garden {

	private int tineCount;
	
	public Rake() {
	}
	
	public Rake (Tool t) {
		super(t);
	}

	public int getTineCount() {
		return tineCount;
	}

	public void setTineCount(int tineCount) {
		this.tineCount = tineCount;
	}

	
}

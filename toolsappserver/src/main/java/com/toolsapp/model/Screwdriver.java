package com.toolsapp.model;

public class Screwdriver extends Tool {

	private double screwSize;

	public Screwdriver() {
	}
	
	public Screwdriver (Tool t) {
		super(t);
	}
	
	public double getScrewSize() {
		return screwSize;
	}

	public void setScrewSize(double screwSize) {
		this.screwSize = screwSize;
	}
	
}

package com.toolsapp.model;

public class Garden extends Tool {

	private String handleMaterial;
	
	public Garden() {
	}
	
	public Garden (Tool t) {
		super(t);
	}

	public String getHandleMaterial() {
		return handleMaterial;
	}

	public void setHandleMaterial(String handleMaterial) {
		this.handleMaterial = handleMaterial;
	}
	
}

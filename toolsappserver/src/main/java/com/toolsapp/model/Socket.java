package com.toolsapp.model;

public class Socket extends Tool {

	private double driveSize;

	public Socket() {
	}
	
	public Socket (Tool t) {
		super(t);
	}
	
	public double getDriveSize() {
		return driveSize;
	}

	public void setDriveSize(double driveSize) {
		this.driveSize = driveSize;
	}
	
}

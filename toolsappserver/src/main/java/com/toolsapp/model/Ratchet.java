package com.toolsapp.model;

/*************************************************************************************************************
 * 
 ************************************************************************************************************/
public class Ratchet extends Tool{

	private double saeSize;
	private boolean deepSocket;
	private double driveSize;
	
	public Ratchet() {
	}
	
	public Ratchet (Tool t) {
		super(t);
	}
	
	public double getSaeSize() {
		return saeSize;
	}
	public void setSaeSize(double saeSize) {
		this.saeSize = saeSize;
	}
	public boolean isDeepSocket() {
		return deepSocket;
	}
	public void setDeepSocket(boolean deepSocket) {
		this.deepSocket = deepSocket;
	}
	public double getDriveSize() {
		return driveSize;
	}
	public void setDriveSize(double driveSize) {
		this.driveSize = driveSize;
	}

	
}

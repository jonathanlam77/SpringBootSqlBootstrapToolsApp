package com.toolsapp.model;

public class Battery {
	
	private int toolNumber;
	private String batteryType;
	private double voltRating;
	private int quantity;
	
	public Battery() {
	}

	public int getToolNumber() {
		return toolNumber;
	}

	public void setToolNumber(int toolNumber) {
		this.toolNumber = toolNumber;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public double getVoltRating() {
		return voltRating;
	}

	public void setVoltRating(double voltRating) {
		this.voltRating = voltRating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}



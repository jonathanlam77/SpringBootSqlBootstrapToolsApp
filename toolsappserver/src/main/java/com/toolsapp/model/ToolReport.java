package com.toolsapp.model;

public class ToolReport {

	private Tool tool;
	private double rentalProfit;
	private double totalCost;
	private double totalProfit;
	private boolean isAvailable;
	
	public ToolReport() {
	}
	
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	public double getRentalProfit() {
		return rentalProfit;
	}
	public void setRentalProfit(double rentalProfit) {
		this.rentalProfit = rentalProfit;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}

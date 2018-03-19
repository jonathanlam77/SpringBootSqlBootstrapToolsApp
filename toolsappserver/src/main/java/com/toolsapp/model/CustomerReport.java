package com.toolsapp.model;

public class CustomerReport {

	private Customer customer;
	private int totalReservations;
	private int totalToolsRented;
	
	public CustomerReport() {
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getTotalReservations() {
		return totalReservations;
	}
	public void setTotalReservations(int totalReservations) {
		this.totalReservations = totalReservations;
	}
	public int getTotalToolsRented() {
		return totalToolsRented;
	}
	public void setTotalToolsRented(int totalToolsRented) {
		this.totalToolsRented = totalToolsRented;
	}
	
}

package com.toolsapp.model;

public class CreditCard {

	private long number;
	private String name;
	private int month;
	private int year;
	private int cvc;
	
	public CreditCard() {
	}

	public CreditCard(long number, String name, int month, int year, int cvc) {
		this.number = number;
		this.name = name;
		this.month = month;
		this.year = year;
		this.cvc = cvc;
	}
	
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	
}

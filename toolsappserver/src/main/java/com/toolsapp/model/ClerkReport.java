package com.toolsapp.model;

public class ClerkReport {

	private Clerk clerk;
	private int numPickups;
	private int numDropoffs;
	private int total;
	
	public ClerkReport() {
	}
	
	public Clerk getClerk() {
		return clerk;
	}
	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}
	public int getNumPickups() {
		return numPickups;
	}
	public void setNumPickups(int numPickups) {
		this.numPickups = numPickups;
	}
	public int getNumDropoffs() {
		return numDropoffs;
	}
	public void setNumDropoffs(int numDropoffs) {
		this.numDropoffs = numDropoffs;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}

package com.toolsapp.model;

import java.util.List;

public class ToolReservation {

	private Reservation reservation;
	private List<Integer> toolIDs;
	private List<Tool> tools;
	
	public ToolReservation() {
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Integer> getToolIDs() {
		return toolIDs;
	}

	public void setToolIDs(List<Integer> toolIDs) {
		this.toolIDs = toolIDs;
	}

	public List<Tool> getTools() {
		return tools;
	}

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}
	
}

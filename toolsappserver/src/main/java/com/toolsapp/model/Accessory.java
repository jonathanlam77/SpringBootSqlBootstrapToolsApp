package com.toolsapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Accessory {

	private int toolNumber;
	private String description;
	private int quantity;
	
	public Accessory() {
		
	}
	
	public void setResultSet(ResultSet rs) throws SQLException {
		setToolNumber(rs.getInt("tool_number"));
		setDescription(rs.getString("description"));
		setQuantity(rs.getInt("quantity"));
	}
	
	public int getToolNumber() {
		return toolNumber;
	}
	
	public void setToolNumber(int toolNumber) {
		this.toolNumber = toolNumber;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}


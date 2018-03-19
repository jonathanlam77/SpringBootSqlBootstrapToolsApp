package com.toolsapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tool {
	
	private int toolNumber;
	private String type;
	private String subType;
	private String subOption;
	private double purchasePrice;
	private String powerSource;
	private double widthDiameter;
	private double length;
	private String manufacturer;
	private String material;
	
	public void setResultSet(ResultSet rs) throws SQLException {
		
		setToolNumber(rs.getInt("tool_number"));
		setType(rs.getString("type"));
		setSubType(rs.getString("sub_type"));
		setSubOption(rs.getString("sub_option"));
		setPurchasePrice(rs.getDouble("purchase_price"));
		setPowerSource(rs.getString("power_source"));
		setWidthDiameter(rs.getDouble("width_diameter"));
		setLength(rs.getDouble("length"));
		setManufacturer(rs.getString("manufacturer"));
		setMaterial(rs.getString("material"));
	}
	
	public Tool () {
	}
	
	public Tool (Tool t) {
		setToolNumber(t.getToolNumber());
		setType(t.getType());
		setSubType(t.getSubType());
		setSubOption(t.getSubOption());
		setPurchasePrice(t.getPurchasePrice());
		setPowerSource(t.getPowerSource());
		setWidthDiameter(t.getWidthDiameter());
		setLength(t.getLength());
		setManufacturer(t.getManufacturer());
		setMaterial(t.getMaterial());
	}

	public Tool (int toolNumber) {
		this.setToolNumber(toolNumber);
	}
	
	public int getToolNumber() {
		return toolNumber;
	}

	public void setToolNumber(int toolNumber) {
		this.toolNumber = toolNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubOption() {
		return subOption;
	}

	public void setSubOption(String subOption) {
		this.subOption = subOption;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPowerSource() {
		return powerSource;
	}

	public void setPowerSource(String powerSource) {
		this.powerSource = powerSource;
	}

	public double getWidthDiameter() {
		return widthDiameter;
	}

	public void setWidthDiameter(double widthDiameter) {
		this.widthDiameter = widthDiameter;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}

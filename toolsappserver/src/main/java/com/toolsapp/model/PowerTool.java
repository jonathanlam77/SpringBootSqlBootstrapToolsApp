package com.toolsapp.model;

import java.util.List;

public class PowerTool extends Tool {

	private double ampRating;
	private double voltRating;
	private int maxRpmRating;
	private int minRpmRating;
	private Battery battery;
	private List<Accessory> accessories;

	public PowerTool() {
	}

	public PowerTool(Tool t) {
		super(t);
	}

	public PowerTool(PowerTool p) {
		super(p);
		setBattery(p.getBattery());
		setAmpRating(p.getAmpRating());
		setVoltRating(p.getVoltRating());
		setMaxRpmRating(p.getMaxRpmRating());
		setMinRpmRating(p.getMinRpmRating());
	}

	/**
	 * @Override public void setResultSet(ResultSet rs) throws SQLException {
	 *           super.setResultSet(rs);
	 *           setAmpRating(rs.getDouble("amp_rating"));
	 *           setVoltRating(rs.getDouble("volt_rating"));
	 *           setMaxRpmRating(rs.getInt("max_rpm_rating"));
	 *           setMinRpmRating(rs.getInt("min_rpm_rating"));
	 * 
	 *           String batteryType = rs.getString("battery_type"); if
	 *           (batteryType != null) { Battery b = new Battery();
	 *           b.setBatteryType(batteryType);
	 *           b.setQuantity(rs.getInt("quantity"));
	 *           b.setVoltRating(rs.getDouble("b.volt_rating")); setBattery(b);
	 *           }
	 * 
	 *           }
	 */
	public double getAmpRating() {
		return ampRating;
	}

	public void setAmpRating(double ampRating) {
		this.ampRating = ampRating;
	}

	public double getVoltRating() {
		return voltRating;
	}

	public void setVoltRating(double voltRating) {
		this.voltRating = voltRating;
	}

	public int getMaxRpmRating() {
		return maxRpmRating;
	}

	public void setMaxRpmRating(int maxRpmRating) {
		this.maxRpmRating = maxRpmRating;
	}

	public int getMinRpmRating() {
		return minRpmRating;
	}

	public void setMinRpmRating(int minRpmRating) {
		this.minRpmRating = minRpmRating;
	}

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	public List<Accessory> getAccessories() {
		return accessories;
	}

	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}

}

package com.toolsapp.model;

public class PhoneNumber {

	private long phoneNumberId;
	private int areaCode;
	private int number;
	private int extension;
	private boolean isPrimary;
	
	public PhoneNumber() {
	}
	
	public PhoneNumber(long phoneNumberId, int areaCode, int number, int extension, boolean isPrimary) {
		this.phoneNumberId = phoneNumberId;
		this.areaCode = areaCode;
		this.number = number;
		this.extension = extension;
		this.isPrimary = isPrimary;
	}
	
	public long getPhoneNumberId() {
		return phoneNumberId;
	}
	public void setPhoneNumberId(long phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}
	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getExtension() {
		return extension;
	}
	public void setExtension(int extension) {
		this.extension = extension;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	
}

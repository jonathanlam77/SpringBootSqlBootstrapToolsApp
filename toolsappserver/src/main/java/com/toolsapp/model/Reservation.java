package com.toolsapp.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Reservation {

	private int reservationId;
	
	private String customer;
	private String pickupClerk;
	private String dropoffClerk;
	
    private long creditCardNumber;

    private String startDate;
    @JsonIgnore
    private Date startDateSql;
    
    private String endDate;
    @JsonIgnore
    private Date endDateSql;
    
    public Reservation() {
    }
    
    public Reservation(int reservationId) {
    	this.reservationId = reservationId;
    }
    
    public Reservation(	int reservationId,
    					String customer,
    					String pickupClerk,
    					String dropoffClerk,
    					long creditCardNumber,
    					Date startDateSql,
    					Date endDateSql) {
    	
    	this.reservationId = reservationId;
    	this.customer = customer;
    	this.pickupClerk = pickupClerk;
    	this.dropoffClerk = dropoffClerk;
    	this.creditCardNumber = creditCardNumber;
    	setStartDateSql(startDateSql);
    	setEndDateSql(endDateSql);
    }

    public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPickupClerk() {
		return pickupClerk;
	}
	public void setPickupClerk(String pickupClerk) {
		this.pickupClerk = pickupClerk;
	}
	public String getDropoffClerk() {
		return dropoffClerk;
	}
	public void setDropoffClerk(String dropoffClerk) {
		this.dropoffClerk = dropoffClerk;
	}
	public long getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Date getStartDateSql() {
		return startDateSql;
	}
	public void setStartDateSql(Date startDateSql) {
		this.startDateSql = startDateSql;
    	setStartDate(startDateSql.toString());
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Date getEndDateSql() {
		return endDateSql;
	}
	public void setEndDateSql(Date endDateSql) {
		this.endDateSql = endDateSql;
		setEndDate(endDateSql.toString());
	}
    
}

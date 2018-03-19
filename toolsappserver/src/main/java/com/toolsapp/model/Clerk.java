package com.toolsapp.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Clerk {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String midName;
    private String lastName;
	private String employeeNumber;

    private String dateOfHire;
    @JsonIgnore
    private Date dateOfHireSql;
    
    public Clerk () {
    }
    
    public Clerk (String username) {
    	this.username =  username;
    }
    
    public Clerk (	String username, 
    				String email,
    				String password,
    				String firstName,
    				String midName,
    				String lastName,
    				Date dateOfHireSql,
    				String employeeNumber
    				) {
    	
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.firstName = firstName;
    	this.midName = midName;
    	this.lastName = lastName;
    	this.employeeNumber = employeeNumber;
    	setDateOfHireSql(dateOfHireSql);
    }
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMidName() {
		return midName;
	}
	public void setMidName(String midName) {
		this.midName = midName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfHireSql() {
		return dateOfHireSql;
	}
	public void setDateOfHireSql(Date dateOfHireSql) {
		this.dateOfHireSql = dateOfHireSql;
    	setDateOfHire(dateOfHireSql.toString());
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getDateOfHire() {
		return dateOfHire;
	}

	public void setDateOfHire(String dateOfHire) {
		this.dateOfHire = dateOfHire;
	}
    
}

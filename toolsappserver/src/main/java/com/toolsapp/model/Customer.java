package com.toolsapp.model;

public class Customer {

    private String username;
    private String email;
    
	private String password;
    private String firstName;
    private String midName;
    private String lastName;
    
    private PhoneNumber cell;
    private PhoneNumber home;
    private PhoneNumber work;
    private CreditCard creditcard;
    
    private String street;
    private String city;
    private String state;
    private String zip;
    
    public Customer() {
    }
    
    public Customer(String username) {
        this.setUsername(username);
    }

    public Customer(String username,
    				String email,
    				String password,
    				String firstName,
    				String midName,
    				String lastName,
    				PhoneNumber cell,
    				PhoneNumber home,
    				PhoneNumber work,
    				CreditCard creditcard,
    				String street,
    				String city,
    				String state, 
    				String zip) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.firstName = firstName;
    	this.midName = midName;
    	this.lastName = lastName;
    	this.cell = cell;
    	this.home = home;
    	this.work = work;
    	this.creditcard = creditcard;
    	this.street = street;
    	this.city = city;
    	this.state = state;
    	this.zip = zip;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PhoneNumber getCell() {
		return cell;
	}

	public void setCell(PhoneNumber cell) {
		this.cell = cell;
	}

	public PhoneNumber getHome() {
		return home;
	}

	public void setHome(PhoneNumber home) {
		this.home = home;
	}

	public PhoneNumber getWork() {
		return work;
	}

	public void setWork(PhoneNumber work) {
		this.work = work;
	}

	public CreditCard getCreditCard() {
		return creditcard;
	}

	public void setCreditCard(CreditCard creditcard) {
		this.creditcard = creditcard;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}

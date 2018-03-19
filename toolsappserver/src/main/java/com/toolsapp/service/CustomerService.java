package com.toolsapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.CreditCard;
import com.toolsapp.model.Customer;
import com.toolsapp.model.CustomerReport;
import com.toolsapp.repository.CustomerRepository;

@Service
public class CustomerService extends BaseService {
	
    @Autowired
    CustomerRepository customer;
	
    public void add(Customer c) {
    	// TODO: check for existing customer before adding
    	customer.add(c);
    }
    
    public Customer get(String username) {
    	return customer.get(username);
    }
 
    public List<CustomerReport> report() {
    	return customer.report();
    }	

    public void updateCreditCard(String customerUsername, CreditCard cc) {
    	customer.updateCreditCard(customerUsername, cc);
    }
}

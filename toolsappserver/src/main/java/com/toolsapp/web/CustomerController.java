package com.toolsapp.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.toolsapp.model.ClerkReport;
import com.toolsapp.model.Customer;
import com.toolsapp.model.CustomerReport;
import com.toolsapp.service.CustomerService;


@Controller    
@RequestMapping(path="/customer") 
public class CustomerController extends BaseController {

    @Autowired
    CustomerService service;
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> add (
            @RequestParam(required = true, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		Customer c =s_mapper.readValue(data, Customer.class);
		service.add(c);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(@PathVariable("username") final String username) {
		Customer c = service.get(username);
		if (c == null) {
			return new ResponseEntity<Customer>(c, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<Customer>(c, HttpStatus.OK);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerReport>> report() {
		List<CustomerReport> c = service.report();
		if (c == null) {
			return new ResponseEntity<List<CustomerReport>>(c, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<List<CustomerReport>>(c, HttpStatus.OK);
		}
	}
	
}
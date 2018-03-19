package com.toolsapp.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toolsapp.model.CreditCard;
import com.toolsapp.service.CreditCardService;
import com.toolsapp.service.CustomerService;
import com.toolsapp.service.ReservationService;

@Controller    
@RequestMapping(path="/creditcard") 
public class CreditCardController extends BaseController {

    @Autowired
    CreditCardService service;
    
    @Autowired
    CustomerService customerService;

    @Autowired
    ReservationService reservationService;
   
    private static final Logger log = LoggerFactory.getLogger(PhoneNumberController.class);
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> add (
            @RequestParam(required = true, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		CreditCard cc =s_mapper.readValue(data, CreditCard.class);
		service.add(cc);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@ResponseBody
    @RequestMapping(value = "/{number}", method = RequestMethod.GET)
	public ResponseEntity<CreditCard> getCreditCard(@PathVariable("number") final String number) {
		CreditCard num = service.get(number);
		if (num == null) {
			return new ResponseEntity<CreditCard>(num, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<CreditCard>(num, HttpStatus.OK);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> update (
            @RequestParam(required = false, value = "customer") final String customer,
            @RequestParam(required = false, value = "reservationId") final String reservationId,
			@RequestParam(required = true, value = "creditcard") final String creditcard
            ) throws JsonParseException, JsonMappingException, IOException {
		
		CreditCard cc =s_mapper.readValue(creditcard, CreditCard.class);
		service.add(cc);
		
		if (customer != null) {
			customerService.updateCreditCard(customer, cc);
		}
		
		if (reservationId != null) {
			reservationService.updateCreditCard(Integer.parseInt(reservationId), cc);
		}
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
}

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
import com.toolsapp.model.PhoneNumber;
import com.toolsapp.service.PhoneNumberService;

@Controller    
@RequestMapping(path="/phonenumber") 
public class PhoneNumberController extends BaseController {

    @Autowired
    PhoneNumberService service;
    
    private static final Logger log = LoggerFactory.getLogger(PhoneNumberController.class);
   
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<PhoneNumber> add (
			@RequestParam(required = true, value = "number") final Integer number,
			@RequestParam(required = true, value = "areacode") final Integer areaCode,
            @RequestParam(required = false, value = "extension") final Integer extension,       
            @RequestParam(required = false, value = "isPrimary") final Boolean isPrimary,
            @RequestParam(required = false, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		PhoneNumber num;
		if (data != null) {
			num = s_mapper.readValue(data, PhoneNumber.class);
		} else {
			num = new PhoneNumber();
			num.setNumber(number);
			num.setAreaCode(areaCode);	
			num.setExtension((extension == null) ? 0 : extension);
			num.setPrimary((isPrimary == null) ? false : isPrimary);			
		}
		
		num = service.add(num);
		
		return new ResponseEntity<PhoneNumber>(num, HttpStatus.CREATED);
	}
	
	@ResponseBody
    @RequestMapping(value = "/{phone_number_id}", method = RequestMethod.GET)
	public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable("phone_number_id") final String phoneNumberId) {
		PhoneNumber num = service.get(phoneNumberId);
		if (num == null) {
			return new ResponseEntity<PhoneNumber>(num, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<PhoneNumber>(num, HttpStatus.OK);
		}
	}
			
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<PhoneNumber> getPhoneNumber (
			@RequestParam(required = true, value = "number") final Integer number,
			@RequestParam(required = true, value = "areacode") final Integer areaCode,
            @RequestParam(required = true, value = "extension") final Integer extension) {
		PhoneNumber num = service.get(areaCode, number, extension);
		if (num == null) {
			return new ResponseEntity<PhoneNumber>(num, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<PhoneNumber>(num, HttpStatus.OK);
		}
	}

	
	
}
package com.toolsapp.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.toolsapp.model.Clerk;
import com.toolsapp.model.ClerkReport;
import com.toolsapp.service.ClerkService;

@Controller    
@RequestMapping(path="/clerk") 
public class ClerkController extends BaseController{

    @Autowired
    ClerkService service;
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> add (
            @RequestParam(required = true, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		Clerk c =s_mapper.readValue(data, Clerk.class);
		
		// Convert date string to sql date
		String dateOfHire = c.getDateOfHire();
		if (dateOfHire != null) {
			// validate hire date
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_STRING);
			Date myDate = null;
			try {
				myDate = formatter.parse(dateOfHire);
			} catch (ParseException e) {
				return new ResponseEntity<String>("Date format must be in format " + DATE_FORMAT_STRING, HttpStatus.BAD_REQUEST);
			}
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			c.setDateOfHireSql(sqlDate);
		}
		
		service.add(c);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public ResponseEntity<Clerk> get(@PathVariable("username") final String username) {
		Clerk c = service.get(username);
		if (c == null) {
			return new ResponseEntity<Clerk>(c, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<Clerk>(c, HttpStatus.OK);
		}
	}
    
	@ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.GET)
	public ResponseEntity<List<ClerkReport>> report() {
		List<ClerkReport> c = service.report();
		if (c == null) {
			return new ResponseEntity<List<ClerkReport>>(c, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<List<ClerkReport>>(c, HttpStatus.OK);
		}
	}

}

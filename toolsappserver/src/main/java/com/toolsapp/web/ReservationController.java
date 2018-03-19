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
import com.toolsapp.model.Reservation;
import com.toolsapp.model.ToolReservation;
import com.toolsapp.service.ReservationService;

@Controller    
@RequestMapping(path="/reservation") 
public class ReservationController extends BaseController {

    @Autowired
    ReservationService service;
		
	@ResponseBody
    @RequestMapping(value = "/{reservation_id}", method = RequestMethod.GET)
	public ResponseEntity<Reservation> get(@PathVariable("reservation_id") final Integer reservation_id) {
		Reservation r = service.get(reservation_id);
		if (r == null) {
			return new ResponseEntity<Reservation>(r, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<Reservation>(r, HttpStatus.OK);
		}
	}	
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> add (
            @RequestParam(required = true, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		Reservation r =s_mapper.readValue(data, Reservation.class);
		
		try {
			r.setStartDateSql(getSqlDate(r.getStartDate()));
			r.setEndDateSql(getSqlDate(r.getEndDate()));
		} catch (ParseException e) {
			return new ResponseEntity<String>("Date format must be in format " + DATE_FORMAT_STRING, HttpStatus.BAD_REQUEST);
		}
				
		service.add(r);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/tool/", method = RequestMethod.POST)
	public ResponseEntity<String> reserveTool (
            @RequestParam(required = true, value = "data") final String data
			) throws JsonParseException, JsonMappingException, IOException {

		ToolReservation tr =s_mapper.readValue(data, ToolReservation.class);
		Reservation r = tr.getReservation();
		try {
			r.setStartDateSql(getSqlDate(r.getStartDate()));
			r.setEndDateSql(getSqlDate(r.getEndDate()));
			tr.setReservation(r);
		} catch (ParseException e) {
			return new ResponseEntity<String>("Date format must be in format " + DATE_FORMAT_STRING, HttpStatus.BAD_REQUEST);
		}
		
		service.add(tr);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@ResponseBody
	@RequestMapping(value = "/tool/", method = RequestMethod.GET)
	public ResponseEntity<List<ToolReservation>> getAllReservationsWithTools  (
		@RequestParam(required = false, value = "customer") final String customer,
		@RequestParam(required = false, value = "pickup") final Boolean pickup,
		@RequestParam(required = false, value = "dropoff") final Boolean dropoff) {
		
		boolean bPickup = false;
		if (pickup != null) {
			bPickup = pickup;
		}
		
		boolean bDropoff = false;
		if (dropoff != null) {
			bDropoff = dropoff;
		}
		
		List<ToolReservation> reservations = service.getAll(customer, bPickup, bDropoff);
		if (reservations == null) {
			return new ResponseEntity<List<ToolReservation>>(reservations, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<List<ToolReservation>>(reservations, HttpStatus.OK);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = "/{reservation_id}/pickupclerk/{clerk_user_name}", method = RequestMethod.POST)
	public ResponseEntity<String> pickup(	@PathVariable("reservation_id") final Integer reservation_id,
											@PathVariable("clerk_user_name") final String clerk ) {
		service.pickup(reservation_id, clerk); 
		return new ResponseEntity<String>(HttpStatus.OK);
	}	

	@ResponseBody
    @RequestMapping(value = "/{reservation_id}/dropoffclerk/{clerk_user_name}", method = RequestMethod.POST)
	public ResponseEntity<String> dropoff(	@PathVariable("reservation_id") final Integer reservation_id,
											@PathVariable("clerk_user_name") final String clerk ) {
		service.dropoff(reservation_id, clerk); 
		return new ResponseEntity<String>(HttpStatus.OK);
	}	
	
}

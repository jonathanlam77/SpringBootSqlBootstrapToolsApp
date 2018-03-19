package com.toolsapp.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.toolsapp.model.Sander;
import com.toolsapp.service.ToolService;

@Controller     
@RequestMapping(path="/tool/sander") 
public class SanderController extends BaseController{

    @Autowired
    ToolService service;
	
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Sander> add (
            @RequestParam(required = true, value = "data") final String data
            ) throws JsonParseException, JsonMappingException, IOException {
		
		Sander s =s_mapper.readValue(data, Sander.class);
		s = (Sander)service.add(s);
		
		return new ResponseEntity<Sander>(s, HttpStatus.CREATED);
	}
	
	

}

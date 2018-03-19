package com.toolsapp.web;

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

import com.toolsapp.model.ClerkReport;
import com.toolsapp.model.Tool;
import com.toolsapp.model.ToolReport;
import com.toolsapp.service.ToolService;

import java.text.ParseException;

@Controller    
@RequestMapping(path="/tool") 
public class ToolController extends BaseController {

	@Autowired
	ToolService service;
	
	@ResponseBody
    @RequestMapping(value = "/{tool_number}", method = RequestMethod.GET)
	public ResponseEntity<Tool> get(@PathVariable("tool_number") final Integer tool_number) {
		Tool t = service.get(tool_number);
		if (t == null) {
			return new ResponseEntity<Tool>(t, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<Tool>(t, HttpStatus.OK);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Tool>> searchToolsAvailableForRent (
			@RequestParam(required = false, value = "type") final String type,
			@RequestParam(required = false, value = "subType") final String subType,
			@RequestParam(required = false, value = "subOptionKeyword") final String subOptionKeyword,
            @RequestParam(required = false, value = "powerSource") final String powerSource,
			@RequestParam(required = false, value = "startDate") final String startDate,
			@RequestParam(required = false, value = "endDate") final String endDate) throws ParseException {
		
		List<Tool> results = service.searchToolsAvailableForRent(type, subType, subOptionKeyword, powerSource, getSqlDate(startDate), getSqlDate(endDate));

		if (results == null) {
			return new ResponseEntity<List<Tool>>(results, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<List<Tool>>(results, HttpStatus.OK);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.GET)
	public ResponseEntity<List<ToolReport>> report() {
		List<ToolReport> c = service.report();  
		if (c == null) {
			return new ResponseEntity<List<ToolReport>>(c, HttpStatus.NOT_FOUND);				
		} else {
			return new ResponseEntity<List<ToolReport>>(c, HttpStatus.OK);
		}
	}
	
}

package com.toolsapp.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {

	protected static ObjectMapper s_mapper = new ObjectMapper();	
	protected static String DATE_FORMAT_STRING = "yyyy-MM-dd";

	/**
	 * Convert date string to SQL date
	 */
	protected java.sql.Date getSqlDate(String date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_STRING);
		if (date != null && date.length() > 0) {
			Date myDate = null;
			myDate = formatter.parse(date);
			return new java.sql.Date(myDate.getTime());
		}
		return null;
	}

}

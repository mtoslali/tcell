package com.turkcell.bipai.saacweather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidation {

	public DateValidation() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isValidDate(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }

}

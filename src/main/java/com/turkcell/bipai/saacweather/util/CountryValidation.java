package com.turkcell.bipai.saacweather.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turkcell.bipai.saacweather.service.HelloWorld;

public class CountryValidation {

	//boolean isValidCountry = false;
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	// helloworldde cagir ve turkiye almanya denemesi

	public boolean run(String countryName) {

			String[] locales = Locale.getISOCountries();

			for (String countryCode : locales) {

				Locale obj = new Locale("", countryCode);
				if (countryName.equalsIgnoreCase(obj.getDisplayCountry())){
					
					return true;
				}
			}
			logger.info("Not Valid Country");
			return false;

			
		    }
}

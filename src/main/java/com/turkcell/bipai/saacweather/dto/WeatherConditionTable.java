package com.turkcell.bipai.saacweather.dto;

import java.util.Calendar;

public class WeatherConditionTable {
	
	public String Sender;
	public Calendar DateTime;
	public boolean IsActive;

	public WeatherConditionTable(String sender){
		this.Sender = sender;
	}
}

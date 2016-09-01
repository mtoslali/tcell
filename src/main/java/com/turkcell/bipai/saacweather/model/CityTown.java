package com.turkcell.bipai.saacweather.model;

public class CityTown {
	
	public String Name;
	public WeatherCondition weatherCondition;
	
	
	public CityTown(String name){
		this.Name = name;
	}
	
	public CityTown(String name, WeatherCondition weatherCondition){
		this.Name = name;
		this.weatherCondition =weatherCondition;
	}
	

}

package com.turkcell.bipai.saacweather.model;

public class CityDetail {

	public String CityName = "";
	public String TownName = "";
	
	public String toString(){
		return "City: " + this.CityName + "\nTown: " + this.TownName; 
	}
}

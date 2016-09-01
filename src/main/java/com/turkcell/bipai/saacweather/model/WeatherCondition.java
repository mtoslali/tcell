package com.turkcell.bipai.saacweather.model;

public class WeatherCondition {

	public double CurrentDegree;
	public double CurrentDegreeRealFeel;
	public double CurrentDegreeRealFeelShade;
	public String WeatherText = "";
	public boolean IsDayTime;
	public String DetailedUrl;
	public String DetailedUrlDescription;
	
	public String ImgUrl;
	
	public int RelativeHumidity;
	public double WindSpeed;
	
	public String toString(){
		return "Current Degree: " + CurrentDegree + "\nCurrentDegreeRealFeel: " + CurrentDegreeRealFeel+
				"\nCurrentDegreeRealFeelShade: " + CurrentDegreeRealFeelShade + "\nWeatherText: "
				+ WeatherText + "\nRelativeHumidity: " +  RelativeHumidity+ "\nWindSpeed: " + WindSpeed;
	}
	
}

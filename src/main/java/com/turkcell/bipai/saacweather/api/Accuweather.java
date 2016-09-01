package com.turkcell.bipai.saacweather.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.turkcell.bipai.saacweather.model.WeatherCondition;
import com.turkcell.bipai.saacweather.util.FtsUpload;

public class Accuweather {

	public static String ApiKey = "";
	private WeatherCondition weatherCondition;
	private static final Logger logger = LoggerFactory.getLogger(Accuweather.class);
	private FtsUpload ftsUpload;

	public Accuweather(String apiKey) {
		this.ApiKey = apiKey;
	}

	public WeatherCondition handleAccuweather(String town) {
		weatherCondition = new WeatherCondition();
		String locationKey = requestAccuweatherLocationKey(town);
		
		logger.info("locationKey is ready");
		logger.info(locationKey);
		
		String conditionStringForecast = requestAccuweatherConditionForeCast(locationKey);
		logger.info(conditionStringForecast);

		String conditionString = requestAccuweatherCondition(locationKey);
		

		logger.info("condition is string is ready in detail");
		try {
			setWeatherModel(weatherCondition,conditionString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// return conditionString;
		return weatherCondition;
	}

	private void setWeatherModel(WeatherCondition tempWeatherCondition,	String tempConditionString) throws IOException {
		
		JsonArray root = (JsonArray) new JsonParser().parse(tempConditionString);
		JsonObject objectLocalInfo = root.get(0).getAsJsonObject();
		
		tempWeatherCondition.IsDayTime = objectLocalInfo.get("IsDayTime").getAsBoolean();
		tempWeatherCondition.WeatherText = objectLocalInfo.get("WeatherText").getAsString();

		JsonObject temperatureInfo = objectLocalInfo.get("Temperature").getAsJsonObject();
		tempWeatherCondition.CurrentDegree = temperatureInfo.get("Metric").getAsJsonObject().get("Value").getAsDouble();
		
		JsonObject realTemperatureInfo = objectLocalInfo.get("RealFeelTemperature").getAsJsonObject();
		tempWeatherCondition.CurrentDegreeRealFeel = realTemperatureInfo.get("Metric").getAsJsonObject().get("Value").getAsDouble();
	
		JsonObject realTemperatureShadeInfo = objectLocalInfo.get("RealFeelTemperatureShade").getAsJsonObject();
		tempWeatherCondition.CurrentDegreeRealFeelShade = realTemperatureInfo.get("Metric").getAsJsonObject().get("Value").getAsDouble();
		
		JsonArray imageUrlObject = objectLocalInfo.get("Photos").getAsJsonArray();
		ftsUpload = new FtsUpload();
		// burda linkini aliyoruz imagin!!!
		tempWeatherCondition.ImgUrl = ftsUpload.uploadToFts(imageUrlObject.get(0).getAsJsonObject().get("LandscapeLink").getAsString());
		
		return;
	}

	// public String requestAccuweatherLocationKey(String apiKey, String town) {
	public String requestAccuweatherLocationKey(String town) {

		// "http://maps.google.com/maps/api/geocode/json?latlng=&sensor=false";
		// http://apidev.accuweather.com/currentconditions/v1/1-317040_1_AL.json?language=en&apikey=hoArfRosT1215

		String locationUrl = "http://apidev.accuweather.com/locations/v1/search?q="
				+ town + "&apikey=" + ApiKey;
		String result = "";
		try {
			result = sendRequest(locationUrl);
		} catch (Exception ex) {
			logger.info(ex.toString());
			return "Error requestAccuweatherLocationKey01";
		}
//		logger.info(result);
		JsonArray root = (JsonArray) new JsonParser().parse(result);
		JsonObject object = root.get(0).getAsJsonObject();

		String locationKey = object.get("Key").getAsString();
		
		logger.info("The related key entered city:");
		logger.info(locationKey);

		return locationKey;

	}

	public String requestAccuweatherCondition(String townKey) {
		// indices/{version}/daily/5day
		String urlConditionFiveDats = "http://apidev.accuweather.com/indices/v1/daily/5day"
				+ townKey + ".json?language=en&apikey=" + ApiKey;
		String urlCondition = "http://apidev.accuweather.com/currentconditions/v1/"
				+ townKey
				+ ".json?language=en&apikey="
				+ ApiKey
				+ "&getphotos=true&details=true";
		// http://apidev.accuweather.com/currentconditions/v1/1-317040_1_AL.json?language=en&apikey=hoArfRosT1215
		String result = "";
		try {
			result = sendRequest(urlCondition);
		} catch (Exception ex) {
			logger.info(ex.toString());
			return "Error requestAccuweatherCondition";
		}

		return result;

	}

	public String requestAccuweatherConditionForeCast(String townKey){
		String urlConditionFiveDats = "http://apidev.accuweather.com/forecasts/v1/daily/45day"
				+ townKey + ".json?apikey=" + ApiKey + "&language=en";// +"&details=true&metric&true&getphotos=true";
		String urlCondition = "http://apidev.accuweather.com/currentconditions/v1/"
				+ townKey
				+ ".json?language=en&apikey="
				+ ApiKey
				+ "&getphotos=true&details=true";
		// http://apidev.accuweather.com/currentconditions/v1/1-317040_1_AL.json?language=en&apikey=hoArfRosT1215
		String result = "";
		try {
			result = sendRequest(urlCondition);
		} catch (Exception ex) {
			logger.info(ex.toString());
			return "Error requestAccuweatherCondition";
		}

		return result;
	}
	
	public static String requestAccuweatherImagery(String townKey){
		
		String urlCondition = "http://apidev.accuweather.com/imagery/v1/maps/radsat/1024x1024/"
				+ townKey
				+ "?apikey"  + ApiKey  +
				"&language=en";
				
		// http://apidev.accuweather.com/currentconditions/v1/1-317040_1_AL.json?language=en&apikey=hoArfRosT1215
		String result = "";
		try {
			result = sendRequest(urlCondition);
		} catch (Exception ex) {
			logger.info(ex.toString());
			return "Error requestAccuweatherCondition";
		}

		return result;
	}
	private static String sendRequest(String url) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = new HttpGet(url);
		HttpEntity responseEntity = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			HttpResponse response = client.execute(request);
			responseEntity = response.getEntity();
			if (responseEntity != null) {

				InputStream stream = responseEntity.getContent();
				int b;
				while ((b = stream.read()) != -1) {
					stringBuilder.append((char) b);
				}
			}

		} catch (Exception ex) {
			logger.info(ex.toString());
			return "Error sendRequest";
		}

		return stringBuilder.toString();

	}

}

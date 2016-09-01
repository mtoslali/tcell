package com.turkcell.bipai.saacweather.api;

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
import com.turkcell.bipai.saacweather.model.CityDetail;

public class ReverseGeocoding {
	private static final Logger logger = LoggerFactory.getLogger(Accuweather.class);


	public static CityDetail getLocationInfo(double lat, double lng) {
		CityDetail cityDetail = new CityDetail();
		
		String locationQueryUrl = "http://maps.google.com/maps/api/geocode/json?latlng="
				+ lat + "," + lng + "&sensor=false";
		
		String locationInfo = "";
		try {
			locationInfo = sendRequest(locationQueryUrl);
		} catch (Exception ex) {
			logger.info("Error getLocationInfo01: " + ex.toString());
		}

		logger.info(locationInfo);
		JsonObject root = (JsonObject) new JsonParser().parse(locationInfo);
		
		JsonArray resultList = root.get("results").getAsJsonArray();
		JsonObject components = resultList.get(0).getAsJsonObject();
		JsonArray address_components = components.get("address_components").getAsJsonArray();
		
		JsonObject ilce = address_components.get(3).getAsJsonObject();
		JsonObject il = address_components.get(4).getAsJsonObject();
		
		String ilceIsmi = ilce.get("short_name").getAsString();
		String ilIsmi = il.get("short_name").getAsString();
		
		byte cityName[] = ilIsmi.getBytes();
		byte townName[] = ilceIsmi.getBytes();
		
		try{
			cityDetail.CityName = new String(cityName, "UTF-8");
			cityDetail.TownName = new String(townName, "UTF-8");
		}catch(Exception ex){
			logger.info("UTF-8 01" + ex.toString());
		}
		
		
		logger.info(cityDetail.TownName);

		return cityDetail;
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
			// logger.info(ex.toString());
			return "Error sendRequest";
		}

		return stringBuilder.toString();

	}
}

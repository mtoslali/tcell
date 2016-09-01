package com.turkcell.bipai.saacweather.util;

import com.turkcell.bipai.saacweather.model.Location;

public class HandleUserRequestInput {

	private static Location location;

	public static  Location handleLocationValues(String content) {
		
		location = new Location();

		location.lat = Double.parseDouble(content.substring(
				content.indexOf(":") + 1, content.indexOf(",")));

		location.lon = Double.parseDouble(content.substring(
				content.lastIndexOf(":") + 1, content.indexOf(")")));

		return location;

	}

}

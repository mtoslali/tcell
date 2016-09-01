package com.turkcell.bipai.saacweather.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpFileSizeUtil {

public static long getSize(String url) throws Throwable {
		
		HttpURLConnection conn = null;
		long size = 1;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			List<String> values = conn.getHeaderFields().get("Content-Length");
			if (values != null && !values.isEmpty()) {
			    String sLength = (String) values.get(0);
			    if (sLength != null) {
			    	size = Long.parseLong(sLength);
//			    	LoggerUtil.log(HttpFileSizeUtil.class, "<getSize> - filePath: " + url + " - FILE_SIZE: " + size);
			    }
			}
		} finally {
			try {
				if (null != conn) {
					conn.disconnect();
				}
			} catch (Throwable t) {
				//LoggerUtil.log(HttpFileSizeUtil.class, "<getSize> - ERROR: " + t.getMessage(), t);
			}
			
		}
		
		return size;
	}
}

package com.turkcell.bipai.saacweather.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.turkcell.bipai.saacweather.model.BiPTesPushMessageRequest;
import com.turkcell.bipai.saacweather.model.BiPTesPushMessageResponse;
import com.turkcell.bipai.saacweather.model.Receiver;
import com.turkcell.bipai.saacweather.model.SaacMessage;
import com.turkcell.bipai.saacweather.util.BasicAuthRestTemplate;

public class RespondTricky {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	
	public static BiPTesPushMessageRequest respondSaacMessage(String sender, SaacMessage saacMessage) {

		
		BiPTesPushMessageRequest request = new BiPTesPushMessageRequest();

		request.setTxnid(UUID.randomUUID().toString());

		request.setSaacMessage(saacMessage);
		request.setReceiver(new Receiver(0, sender)); //
//		BiPTesPushMessageResponse response = restTemplate.postForObject(
//				"https://prptims.turkcell.com.tr/tes/rest/spi/sendmsgserv",
//				request, BiPTesPushMessageResponse.class);

	return request;
	
//		logger.info("Result Code: " + response.getResultcode());
	}
}

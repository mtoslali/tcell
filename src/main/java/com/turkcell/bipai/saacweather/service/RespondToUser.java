package com.turkcell.bipai.saacweather.service;

import java.util.ArrayList;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.turkcell.bipai.saacweather.model.BiPTesPushMessageRequest;
import com.turkcell.bipai.saacweather.model.BiPTesPushMessageResponse;
import com.turkcell.bipai.saacweather.model.Receiver;
import com.turkcell.bipai.saacweather.model.ReceivercontentList;
import com.turkcell.bipai.saacweather.model.SaacMessage;
import com.turkcell.bipai.saacweather.util.BasicAuthRestTemplate;
import com.turkcell.bipai.saacweather.util.ConstantUtil;

public class RespondToUser {
	
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	private static  String UserName,Password;
	
	public static void respondSaacMessage(String sender, SaacMessage saacMessage) {
		
		UserName = ConstantUtil.getConf("TES_USERNAME"); 
		Password = ConstantUtil.getConf("TES_PASSWORD");
		
		RestTemplate restTemplate = new BasicAuthRestTemplate(UserName,	Password);
		BiPTesPushMessageRequest request = new BiPTesPushMessageRequest();

		request.setTxnid(UUID.randomUUID().toString());
		request.setSaacMessage(saacMessage);
		request.setReceiver(new Receiver(0, sender)); //
		BiPTesPushMessageResponse response = restTemplate.postForObject(
				"https://prptims.turkcell.com.tr/tes/rest/spi/sendmsgserv",
				request, BiPTesPushMessageResponse.class);

//		logger.info("Result Code: " + response.getResultcode());
	}
	
	public static void respondReceiverContentList(ArrayList<ReceivercontentList> receivercontentList){
		UserName = ConstantUtil.getConf("TES_USERNAME"); 
		Password = ConstantUtil.getConf("TES_PASSWORD");
		RestTemplate restTemplate = new BasicAuthRestTemplate(UserName,Password);
		
		BiPTesPushMessageRequest request = new BiPTesPushMessageRequest();
		
		request.setTxnid(UUID.randomUUID().toString());
		request.setReceivercontentList(receivercontentList);
		
		BiPTesPushMessageResponse response = restTemplate.postForObject(
				"https://prptims.turkcell.com.tr/tes/rest/spi/sendmsgserv",
				request, BiPTesPushMessageResponse.class);
		
	}
}

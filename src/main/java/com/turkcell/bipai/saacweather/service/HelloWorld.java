package com.turkcell.bipai.saacweather.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mongodb.DBObject;
import com.turkcell.bipai.saacweather.api.Accuweather;
import com.turkcell.bipai.saacweather.api.ReverseGeocoding;
import com.turkcell.bipai.saacweather.command.Command;
import com.turkcell.bipai.saacweather.command.FinishCommand;
import com.turkcell.bipai.saacweather.command.HelpCommand;
import com.turkcell.bipai.saacweather.command.StartCommand;
import com.turkcell.bipai.saacweather.dto.DataBaseHandler;
import com.turkcell.bipai.saacweather.dto.DbHandler;
import com.turkcell.bipai.saacweather.image.manipulation.ImageProcessing;
import com.turkcell.bipai.saacweather.model.BiPTesInput;
import com.turkcell.bipai.saacweather.model.BiPTesPushMessageRequest;
import com.turkcell.bipai.saacweather.model.BiPTesPushMessageResponse;
import com.turkcell.bipai.saacweather.model.CityDetail;
import com.turkcell.bipai.saacweather.model.CityTown;
import com.turkcell.bipai.saacweather.model.Ctype;
import com.turkcell.bipai.saacweather.model.Receiver;
import com.turkcell.bipai.saacweather.model.ReceivercontentList;
import com.turkcell.bipai.saacweather.model.SaacMessage;
import com.turkcell.bipai.saacweather.model.SurveyTypeEnum;
import com.turkcell.bipai.saacweather.model.WeatherCondition;
import com.turkcell.bipai.saacweather.util.BasicAuthRestTemplate;
import com.turkcell.bipai.saacweather.util.ConstantUtil;
import com.turkcell.bipai.saacweather.util.CountryValidation;
import com.turkcell.bipai.saacweather.util.DateValidation;
import com.turkcell.bipai.saacweather.util.FtsUpload;
import com.turkcell.bipai.saacweather.util.HandleUserRequestInput;
import com.turkcell.bipai.saacweather.util.SaacMessageProducer;

@RestController
public class HelloWorld {

	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	String ApiKey; // 500 calls per day
	SaacMessage saacMessage;

	private DataBaseHandler db;
	private Accuweather aw;
	private WeatherCondition result;
	private String town = "";
	private RespondToUser respondToUser;
	private String pollId;
	private DbHandler dbHandler;
	
	@RequestMapping(value = "/hello", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BiPTesPushMessageRequest translate(@RequestBody BiPTesInput biPTesInput) {
		//	public void translate(@RequestBody BiPTesInput biPTesInput) {
		ApiKey = ConstantUtil.getConf("API_KEY");
		
		String sender = biPTesInput.getSender();
		Integer msgid = biPTesInput.getMsgid();
		String sendtime = biPTesInput.getSendtime();
		String type = biPTesInput.getType();
		String ctypeVal = biPTesInput.getCtype();
		String content = biPTesInput.getContent();
		String optionid = biPTesInput.getOptionId();
		String pollid = biPTesInput.getPollid();

		Command command = null;

		logger.info("sender: " + sender + " - msgid: " + msgid + " - content: "
				+ content + " - type: " + type + " - ctype: " + ctypeVal
				+ " - sendtime: " + sendtime);

		Ctype ctype = Ctype.fromCode(ctypeVal);
		switch (ctype) {
		case Audio:
			logger.info("Audio input message");
			break;
		case Caps:
			logger.info("Caps input message");
			break;
		case Image:
			logger.info("Image input message");
			break;
		case Location:

			CityDetail cityDetails = ReverseGeocoding.getLocationInfo(HandleUserRequestInput.handleLocationValues(content).lat,
					HandleUserRequestInput.handleLocationValues(content).lon);
			
			logger.info("Sehir Bilgileri :" + cityDetails.toString());			
			
			if (!cityDetails.TownName.equals("")) {
				town = cityDetails.TownName;
			}
			else if (!cityDetails.CityName.equals("")) {
				town = cityDetails.CityName;
			} else {
				logger.info("Sehir ve ilce ismi bulunamadi");
				RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Sehir Bulunamadi"));
				break;
				//town yoksa return olmali
			}
			
			// aktif olmayan bir kayit yaratiyoruz seesssizcceeeee
			StartCommand startCommandForFirst = new StartCommand();
			if(startCommandForFirst.handle(sender,town)){ // false donerse kayitli zaten deemek
				// burda respond yapmicaz
				//respondToUser.respondSaacMessage(sender, new SaacMessage(0, "Basariyla kaydedildi"));
			}
		
			// sonra rmm gonderiyoruz anket
			SaacMessageProducer smp = new SaacMessageProducer();
			try {
				//RespondToUser.respondSaacMessage(sender, smp.produceSurvey(UUID.randomUUID().toString(),SurveyTypeEnum.BugunYarin));
				// ustteki olacak
				return RespondTricky.respondSaacMessage(sender, smp.produceSurvey(UUID.randomUUID().toString(),SurveyTypeEnum.BugunYarin));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info("hava durumu " + town + " :\n" + result);				
			
			break;
		case RMM:
			logger.info("RMM input message " + pollid);
			// anket cevabi gelicek 
			switch (optionid)
			{
			case "1": // evet cikiyorum
				dbHandler = new DbHandler();
				
				if(dbHandler.updateCollectionSurvey(sender,false)){
					 RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Elvada"));
				}
				else{
					RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Bir hata olustu"));
				}				
//				break;
			case "2": // hayir kaliyorum
				RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Tesekkurler vazgecmediginiz icin"));
//				break;
			case "3": // bugun mu				
				dbHandler = new DbHandler();
				aw = new Accuweather(ApiKey);			
				
				//townu db den cekiyoruz senderla eslesen // sonra getir degerleri
				result = aw.handleAccuweather(dbHandler.findCity(sender));
				
				// kullaniciya rmm gonder hava durumunu
				RespondToUser.respondSaacMessage(sender, new SaacMessageProducer().produceRMM(String.valueOf(result.CurrentDegree)));
				// wait 10 seconds may be
				
				
				// baslasin mi diye gonder 
				 try {
					RespondToUser.respondSaacMessage(sender, new SaacMessageProducer().produceSurvey(UUID.randomUUID().toString(),SurveyTypeEnum.BaslatBaslatma));
				} catch (Exception ex) {
					logger.info(ex.toString());
				}
				
				logger.info("hava durumu " + town + " :\n" + result);
				break;
			case "4": // ee baska gun  mu?
				RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Hava durumunu ogrenek istedeiginiz Tarih giriniz"));
				//break;
				 
			case "5": // bana da her gun gonderir misiniz acaba?
				// dbden guncelleyelim aktif mi yi bir yapalim
				dbHandler = new DbHandler();
				
				if(dbHandler.updateCollectionSurvey(sender,true)){
					RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Hallettik her gun gondericez"));
				}
				else{
					RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Bir hata olustu"));
				}	
				// burdada respond yok
				//RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Tabikide , aramiza hosgeldiniz:)))"));
			case "6": // istemiyor her gun
				RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Onyargiyla red!"));				
			}					
			break;
			
		case Sticker:
			logger.info("Sticker input message");
			break;

		case Text:
			ImageProcessing ip = new ImageProcessing();
			ip.draw(30,30);
			
//			dbHandler = new DbHandler();
			aw = new Accuweather(ApiKey);			
			result = aw.handleAccuweather("maltepe");
			
			// imagerey denemesi ama api key calismiyor
			String a = Accuweather.requestAccuweatherImagery("isstanbul icin denemelre");
			logger.info(a);
//			//townu db den cekiyoruz senderla eslesen
//							// sonra getir degerleri
//			result = aw.handleAccuweather(dbHandler.findCity(sender));
			// deneme bolgesiii
			
			
			
			// deneme bolgesiii		
			//-------------------------
					
			if ("yardım".equalsIgnoreCase(content)) {
				command = new HelpCommand();
				respondText(sender, saacMessage = new SaacMessage(0, "yardim dokumani!"));
			}
			else if ("basla".equalsIgnoreCase(content)) {
				StartCommand startCommand = new StartCommand();
			
				try {
					if(!startCommand.handle(sender)){ // false donerse kayitli zaten deemek
						 RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "kayitli zaten"));
					}
					else{
						RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "Basariyla kaydedildi"));
					}
											
				} catch (Exception ex) {
					logger.info("Basla01 :" + ex.toString());
				}// yoksa kaydedililir her gun gondermek uzere

			} else if ("bitir".equalsIgnoreCase(content)) {
				FinishCommand finishCommand = new FinishCommand();
				
				try{
					if(!finishCommand.handle(sender)){ // varsa ve aktifse
						RespondToUser.respondSaacMessage(sender, new SaacMessage(0, "kayitli degilsin zaten neyi bitireceksin?"));
					}
					else{
						// anket zamani!
						String pollId = UUID.randomUUID().toString();
//							
							 RespondToUser.respondSaacMessage(sender, new SaacMessageProducer().produceSurvey(pollId,SurveyTypeEnum.EvetHayir));
//						
//						respondToUser.respondSaacMessage(sender, new SaacMessage(0, "kayitli degilsin zaten neyi bitireceksin?"));
					}
				}catch(Exception ex){
					logger.info(ex.toString());
				}

			}else if (DateValidation.isValidDate(content)){
				// tarih girerse !!! 
				dbHandler = new DbHandler();
				town = dbHandler.findCity(sender);// sehrini getir elamanin
				
				aw = new Accuweather(ApiKey);
				result = aw.handleAccuweather(town); // tarih alan bir yapi kurucaz				
				
				// en son girilen tarihte db de kayitli sehrin, hava dumu getirilir
				RespondToUser.respondSaacMessage(sender, new SaacMessageProducer().produceRMM(String.valueOf(result.CurrentDegree)));
				
			}
			
			else {
				CountryValidation countryValidation = new CountryValidation();
				if (countryValidation.run(content)) {

					  respondText(sender, saacMessage = new SaacMessage(0,"type down city/town instead of country!"));
										
					 RespondToUser.respondSaacMessage(sender, new SaacMessage(2, "denemeler"));
					 //return
				}
				else{										
					town = content.toLowerCase();
					aw = new Accuweather(ApiKey);
					result = aw.handleAccuweather(town);
					SaacMessageProducer saacForWeatherCondition = new SaacMessageProducer();
					
					RespondToUser.respondSaacMessage(sender, saacForWeatherCondition.produceRMM(String.valueOf(result.CurrentDegree)));// rmm olusturduk;				
				
				}				
			}
			break;

		case Video:
			logger.info("Video input message");
			break;
		default:			
			break;			
	
		}
		return null;
	}

	private void respondText(String sender, SaacMessage saacMessage) {

		RestTemplate restTemplate = new BasicAuthRestTemplate("bu1405014671871422",
				"bu140503f53e235");
		BiPTesPushMessageRequest request = new BiPTesPushMessageRequest();

		request.setTxnid(UUID.randomUUID().toString());
	
		saacMessage.Ratio  = null;
		request.setSaacMessage(saacMessage);
		request.setReceiver(new Receiver(0, sender)); //
		BiPTesPushMessageResponse response = restTemplate.postForObject(
				"https://prptims.turkcell.com.tr/tes/rest/spi/sendmsgserv",
				request, BiPTesPushMessageResponse.class);
		
		//logger.info("Result Code: " + response.getResultcode());
	}
	
//	@RequestMapping(value = "/mert", method = RequestMethod.POST, produces = "application/json")
//	public void longlat(@RequestBody Location location) {
//
//	}

}

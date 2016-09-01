package com.turkcell.bipai.saacweather.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.turkcell.bipai.saacweather.api.Accuweather;
import com.turkcell.bipai.saacweather.dto.DbHandler;
import com.turkcell.bipai.saacweather.model.BiPTesPushMessageRequest;
import com.turkcell.bipai.saacweather.model.CityTown;
import com.turkcell.bipai.saacweather.model.Receiver;
import com.turkcell.bipai.saacweather.model.ReceivercontentList;
import com.turkcell.bipai.saacweather.model.WeatherCondition;
import com.turkcell.bipai.saacweather.service.HelloWorld;
import com.turkcell.bipai.saacweather.service.RespondToUser;
import com.turkcell.bipai.saacweather.util.SaacMessageProducer;

public class WeatherJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
	private Accuweather aw;
	private WeatherCondition weatherCondition;
	private String ApiKey = "hoArfRosT1215";
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

				System.out.println("Hello Quartz!");
				// Burda kullanilara mesaj gonderimi yapilacaktir!!
				
				Set<String> cities = new HashSet<String>();
				Map<String, String> hashCodeOfUserCity = new HashMap<String, String>();
				
				DbHandler mdb = new DbHandler();
				List<DBObject> activeUsers = 	mdb.findAllActiveUsers();
				
				for(DBObject dbObject :activeUsers){
					if(dbObject != null){
						cities.add(dbObject.get("city").toString()); // different city names for api
						hashCodeOfUserCity.put(dbObject.get("sender").toString(), dbObject.get("city").toString());
					}
					
				}
				
				Map<String,CityTown> cityTownMap = new HashMap<String,CityTown>();
				//cities iterate edilirken
				Iterator iterator = cities.iterator();
				CityTown ct;
				aw = new Accuweather(ApiKey);
				while(iterator.hasNext()){	
					weatherCondition = new WeatherCondition();
					String negeldi = iterator.next().toString();
					weatherCondition = aw.handleAccuweather(negeldi);
					ct = new CityTown(negeldi,weatherCondition);
					
					cityTownMap.put(negeldi,ct);
					//cityResults.add(new CityTown(iterator.next().toString(),aw.handleAccuweather(iterator.next().toString())));
				}
				
				ReceivercontentList receivercontentList;
				
				SaacMessageProducer saacMessageProducer = new SaacMessageProducer();
				ArrayList<ReceivercontentList> receivercontentListForOutput = new ArrayList<ReceivercontentList>();
				for (Map.Entry<String, String> entry : hashCodeOfUserCity.entrySet()) {
					// mesajgonderme islemi
					
					//kullanici hashi entryde key ve sehride entrynin valuesi
					
					// sehirhava durumu mapinden havadurumunu cekiyoruz ve artik rmm produce edebiliriz
					WeatherCondition wc = cityTownMap.get(entry.getValue()).weatherCondition; // sehirden 
					
					receivercontentList = new ReceivercontentList(); // toplu rmm icin her birinde receiver objesi
					receivercontentList.setReceiver(new Receiver(2,entry.getKey()));
					receivercontentList.setSaacMessage(saacMessageProducer.produceRMM(String.valueOf(wc.CurrentDegree)));
					receivercontentListForOutput.add(receivercontentList);				
					
				}
				BiPTesPushMessageRequest request = new BiPTesPushMessageRequest();

				request.setTxnid(UUID.randomUUID().toString());
				request.setReceivercontentList(receivercontentListForOutput);
				
				
				//coklu kullaniciya farkli mesaj gonderimi
				RespondToUser.respondReceiverContentList(receivercontentListForOutput);
//				if(request != null){
//					return request;	
//				}
				

			}

}

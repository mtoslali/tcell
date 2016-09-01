package com.turkcell.bipai.saacweather.command;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.turkcell.bipai.saacweather.dto.DataBaseHandler;
import com.turkcell.bipai.saacweather.dto.DbHandler;
import com.turkcell.bipai.saacweather.dto.WeatherConditionTable;
import com.turkcell.bipai.saacweather.model.WeatherCondition;
import com.turkcell.bipai.saacweather.service.HelloWorld;
import com.turkcell.bipai.saacweather.service.RespondToUser;

public class StartCommand implements Command {
	
	public static final String NAME		=	"basla";
	private DataBaseHandler db;
	private DbHandler mongoDb;
	private WeatherCondition result;
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);

	
	
	
	@Override
	public String handle(String sender, List<Object> params) {
		return	" HelloWorld Servisi sadece yazılan mesajları karşılar. \n" + 
				" Metin olarak mesaj gönderebilirsiniz.";
	}
	
	public boolean handle(String sender){
		
		mongoDb = new DbHandler();
		boolean checkForActive = false;
		
		if(mongoDb.findCollection(sender)){
			checkForActive = mongoDb.findCollectionIsActive(sender);
			if(checkForActive){
				return false;
			}else{
				// diyoruz sender var ve aktif degilse aktif yap
				mongoDb.updateCollectionSurvey(sender,true);
				return true;
			}

		}
		// sender yoksa zaten yapistir
		else{
			try {
				
				//db.handleDbActions();
				BasicDBObject document = new BasicDBObject();
				document.put("sender", sender);
				document.put("date", new Date());
				document.put("city", "bilemiyoruz efendim"); // ask to fulden
				document.put("aktifMi", 1);
				mongoDb.insertDoc(document);
				
			} catch (Exception ex) {
				logger.info("Basla01 :" + ex.toString());
			}
			return true;
		}
		
	}
	
	public boolean handle(String sender,String cityName){
		
		mongoDb = new DbHandler();
		if(mongoDb.findCollection(sender)){
			return false;
		}
		
		try {
			//db.handleDbActions();
			BasicDBObject document = new BasicDBObject();
			document.put("sender", sender);
			document.put("date", new Date());
			document.put("city", cityName); 
			document.put("aktifMi", 0);
			mongoDb.insertDoc(document);
			
		} catch (Exception ex) {
			logger.info("Basla01 :" + ex.toString());
		}
		return true;
	}

	
	@Override
	public String getName() {
		return NAME;
	}
	
}

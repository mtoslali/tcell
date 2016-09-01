package com.turkcell.bipai.saacweather.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turkcell.bipai.saacweather.dto.DataBaseHandler;
import com.turkcell.bipai.saacweather.dto.DbHandler;
import com.turkcell.bipai.saacweather.dto.WeatherConditionTable;
import com.turkcell.bipai.saacweather.model.WeatherCondition;
import com.turkcell.bipai.saacweather.service.HelloWorld;

public class FinishCommand implements  Command {
	public static final String NAME		=	"basla";
	private DataBaseHandler db;
	private WeatherCondition result;
	
	private DbHandler mongoDb;
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	
	@Override
	public String handle(String sender, List<Object> params) {
		return	" HelloWorld Servisi sadece yazılan mesajları karşılar. \n" + 
				" Metin olarak mesaj gönderebilirsiniz.";
	}
	
	public boolean handle(String sender){
		
		mongoDb = new DbHandler();
		boolean isActiveUser = false;
		
		try {
			if(mongoDb.findCollection(sender)){
				isActiveUser = mongoDb.findCollectionIsActive(sender);
				if(isActiveUser){
					return true;
				}
				else{					
					return false; // varsin ama aktif deilsin
				}				
			}
			
		} catch (Exception ex) {
			logger.info("Bitir01 :" + ex.toString());
		
		}
		return false; //yoksun 
		
	}

	
	@Override
	public String getName() {
		return NAME;
	}
}

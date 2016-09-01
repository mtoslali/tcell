package com.turkcell.bipai.saacweather.dto;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class SurveyDbManagement {
	
	private  DbHandler mongoDb;
	
	
	public  boolean insertSurvey(String pollId,String sender){
		
		mongoDb = new DbHandler();
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("sender", sender);
		dbObject.put("pollId", pollId);		
		dbObject.put("surveyType", 1); // survey type = bitir surveyi
		mongoDb.insertDoc(dbObject);
		return true;
	}
	
	public boolean updateForSurvey(String sender){
		
//		mongoDb = new DbHandler();
//		
//		
//		
//			
//		mongoDb.updateCollection(dbObject);
		
		return true;
	}

}

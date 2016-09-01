package com.turkcell.bipai.saacweather.dto;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.turkcell.bipai.saacweather.service.HelloWorld;

public class DbHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	public MongoClient mongo;
	public DB db;

	public DBCollection collection;
	
	public DbHandler(){
		try {
			connectToDb();
			 
		} catch (Exception ex) {
			logger.info(ex.toString());
		}
	}

	private void connectToDb() throws UnknownHostException {

		this.mongo = new MongoClient("localhost", 27017);
		//this.mongo = new MongoClient("moncol1.turkcell.tgc", 27017);
		logger.info("2");
		// Get database. If the database doesn’t exist, MongoDB will create it
		// for you.
		this.db = mongo.getDB("BipSaac");
		logger.info("3");
		this.collection = db.getCollection("BipSaacHavaDurumu");

		logger.info("4");

	}

	public boolean insertDoc(BasicDBObject dbObject) {
		logger.info("1");
		

		collection.insert(dbObject);

		return true;
	}

	public boolean updateCollection(BasicDBObject dbObject) {
		

		
//		dbObject.append("$set", new BasicDBObject().append("clients", 110)); // eklencek
//																				// deger
//
//		BasicDBObject searchQuery = new BasicDBObject().append("hosting",
//				"hostB"); // hangi satisa
//
//		collection.update(searchQuery, newDocument);
		return true;

	}

	public boolean findCollection(String sender) {
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("sender", sender);

		DBCursor cursor = collection.find(searchQuery);

		if (cursor.hasNext()) {
			
			return true;
		} else {
			return false;
		}

	}
	public boolean findCollectionIsActive(String sender){
		
	
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("sender", sender);
		
	
		DBCursor cursor = collection.find(searchQuery);
		
		if (cursor.hasNext()) {
			return cursor.next().get("aktifMi").toString().equalsIgnoreCase("1")? true:false;
		
		} else {
			return false;
		}
	}
	
	public List<DBObject> findAllActiveUsers(){
		
	
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("aktifMi", 1);

		
		List<DBObject> myList = null;
		DBCursor cursor = collection.find(searchQuery);
        myList = cursor.toArray();
		
return myList;
//		if (cursor.hasNext()) {
//			
//			
//			while (cursor.hasNext()) {
//				System.out.println(cursor.next());
//			}
//			return myList;
//		} else {
//			return null;
//		}
		
	}
	
	public boolean updateCollectionSurvey(String sender, boolean isActive){
		try {
			connectToDb();
		} catch (Exception ex) {

		}
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("aktifMi", isActive ? 1 : 0));

		BasicDBObject searchQuery = new BasicDBObject().append("sender", sender);

		collection.update(searchQuery, newDocument);
		
		return true;
	}
	public String findCity(String sender){
		
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("sender",sender));
		obj.add(new BasicDBObject("aktifMi", 1));
		andQuery.put("$and", obj);

		System.out.println(andQuery.toString());

		DBCursor cursor = collection.find(andQuery);
		DBObject dbObject = cursor.next();
		return dbObject.get("city").toString();
//		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
//		}
//		
//		return "";
		
	}

}

package com.turkcell.bipai.saacweather.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.turkcell.bipai.saacweather.service.HelloWorld;

public class DataBaseHandler {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
	private static final String DB_USER = "user";
	private static final String DB_PASSWORD = "password";
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	private static Connection dbConnection = null;
	private static Statement statement = null;

	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorld.class);
	
	private int count = 0;
	
	public String Command;
	public WeatherConditionTable WeatherConditionTable;
	
	public DataBaseHandler(WeatherConditionTable weatherConditionTable, String command){
		Command = command;
		WeatherConditionTable = weatherConditionTable;
		
	}
	
	public boolean handleDbActions() {
		if("basla".equalsIgnoreCase(Command)){
			if(checkForTheRecord(this.WeatherConditionTable)){
				return false; // kayit zaten var
			}
			else{
				
				try{
					insertRecord(this.WeatherConditionTable);
				}catch(Exception ex){
					logger.info(ex.toString());
				}
				return true; // kaydedildi
			}
		}
		else if ("bitir".equalsIgnoreCase(Command)){
			if(checkForTheRecord(this.WeatherConditionTable)){
				
				try{
					updateRecordIntoDbUserTable(this.WeatherConditionTable);
				}catch(Exception ex){
					logger.info(ex.toString());
				}
				return true;
				
			}
			else{
				return false; // boyle bir kayit yok
				
			}
		}
		else{
			return false; // command anlamsiz
		}
	}
	
	// returns true if record exists
	public static boolean checkForTheRecord(WeatherConditionTable weatherRecord) {
		String checkRecordQuery = "select * FROM table Where " + "UserId = "
				+ weatherRecord.Sender + " AND isActive = 1";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			// execute update SQL stetement
			statement.execute(checkRecordQuery);
			ResultSet rs = statement.executeQuery(checkRecordQuery);
			
			if (rs.next()) {
			   return true;
			}
			else
				return false;
			
		} catch (Exception ex) {
			logger.info("Check01 for " + weatherRecord.Sender + "\n"
					+ ex.toString());

		}
		
		return true;

	}

	public void insertRecord(WeatherConditionTable weatherRecord)
			throws SQLException {

		String insertTableSQL = "INSERT INTO table"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "(1,'mkyong','system', " + "to_date('"
				+ getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(insertTableSQL);

			// execute insert SQL stetement
			statement.executeUpdate(insertTableSQL);

			System.out.println("Record is inserted into table table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
	}

	private static void updateRecordIntoDbUserTable(
			WeatherConditionTable weatherRecord) throws SQLException {

		String updateTableSQL = "UPDATE table"
				+ " SET USERNAME = 'mkyong_new' " + " WHERE USER_ID = 1";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			// execute update SQL stetement
			statement.execute(updateTableSQL);

			System.out.println("Record is updated to table table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	private static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());

	}

}

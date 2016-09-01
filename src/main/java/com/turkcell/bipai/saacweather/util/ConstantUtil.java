package com.turkcell.bipai.saacweather.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;


public class ConstantUtil {

	public static final String	DEFAULT_TARGET_LANGUAGE	=	"en";
	public static final String	DEFAULT_SOURCE_LANGUAGE	=	"tr";
	
	private static final String SYSTEM_FOLDER			=	System.getProperty("TRANSLATOR_FOLDER");
	
	public static final Map<String, String>						CONF_MAP				=	new LinkedHashMap<>();
	public static final Map<String, Map<String, String>>		MESSAGES_MAP			=	new LinkedHashMap<>();
	public static final Map<String, Map<String, List<String>>>	COMMAND_MAP				=	new LinkedHashMap<>();
	public static final Map<String, List<String>>				LANGAUAGE_CODES_MAP		=	new LinkedHashMap<>();
	public static final Map<String, Map<String, String>>		LANGUAGE_NAMES_MAP		=	new LinkedHashMap<>();
	public static final Map<String, String>						NUANCE_LANGUAGES_MAP	=	new LinkedHashMap<>();
	public static final Map<String, String>						ABBYY_LANGUAGES_MAP		=	new LinkedHashMap<>();
	
	
	public static final String REQUEST_TYPE_RICH_MEDIA	=	"M";

	
	public static final int MESSAGE_TYPE_TEXT			=	0;
	public static final int MESSAGE_TYPE_HTML			=	1;
	public static final int MESSAGE_TYPE_IMAGE			=	2;
	public static final int MESSAGE_TYPE_AUDIO			=	3;
	public static final int MESSAGE_TYPE_VIDEO			=	4;
	public static final int MESSAGE_TYPE_STICKER		=	5;
	public static final int MESSAGE_TYPE_CAPS			=	6;
	public static final int MESSAGE_TYPE_LOCATION		=	7;
	public static final int MESSAGE_TYPE_RMM			=	8;
	
	
	public static final int RICH_MEDIA_TYPE_POLL		=	2;
	
	static {
		try {
			initConfMap();
//			initCommandsMap();
//			initLangCodesMap();
//			initLanguageNamesMap();
//			initMessagesMap();
//			initNuanceLangMap();
//			initAbbyyLangMap();
		} catch (Throwable t) {
			t.printStackTrace();
		
		}
	}
	
	
	public static String getConf(String confName) {
		return CONF_MAP.get(confName);
	}

	

	public static String getCommandName(String command) {
		return "";
////		if ( ! command.startsWith("/")) {
////			//return TranslateCommand.NAME;
////		}
//		
//		while (command.startsWith("/")) {
//			command = command.substring(1).toLowerCase();
//		}
//		
//		command = command.split(" ")[0];
//		
////		command = AsciiUtils.convertNonAsciiName(command);
//		
//		for (String commandKey : COMMAND_MAP.keySet()) {
//			Map<String, List<String>> map	=	COMMAND_MAP.get(commandKey);
//			for (String langCode : map.keySet()) {
//				List<String> commands = map.get(langCode);
//				//if (CollectionValidator.isValid(commands) && commands.contains(command)) {
//					return "";
//				//}
//			}
//		}
		
		//return TargetCommand.NAME;
	}
	
	
	
	
	
	public static String getMessage(String langCode, String messageType) {
		Map<String, String> map	=	MESSAGES_MAP.get(messageType);
		return map.get(langCode);
	}
	
	
	public static String getLanguageName(String langCode, String langKeyword) {
		Map<String, String> map	=	LANGUAGE_NAMES_MAP.get(langKeyword);
		
		String code = map.get(langCode);
		
		if (code == null) {
			return map.get(DEFAULT_SOURCE_LANGUAGE);
		}
		
		return code;
	}
	
	
	public static String getNuanceLang(String langCode) {
		return NUANCE_LANGUAGES_MAP.get(langCode);
	}
	
	
	public static String getAbbyyLang(String langCode) {
		return ABBYY_LANGUAGES_MAP.get(langCode);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void initConfMap() throws FileNotFoundException, IOException, ParseException {
	
		//todo
		String		filename	= "D:\\dev\\workspace\\saacworkspace\\biphavadurumu\\config\\constant.json";//	getFilepath("constants.json");
		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
		
		CONF_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
		
		
	}
	
//	
//	@SuppressWarnings("unchecked")
//	public static void initCommandsMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("commands.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		COMMAND_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//		
//		for (String commandKey : COMMAND_MAP.keySet()) {
//			Map<String, List<String>> map	=	COMMAND_MAP.get(commandKey);
//			for (String langCode : map.keySet()) {
//				List<String> commands = map.get(langCode);
//				
//			}
//		}
//		
//		
//	}
//	
//	
//	
//	@SuppressWarnings("unchecked")
//	public static void initLangCodesMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("languages.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		LANGAUAGE_CODES_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//		for (String langCode : LANGAUAGE_CODES_MAP.keySet()) {
//			List<String> codes = LANGAUAGE_CODES_MAP.get(langCode);
//			
//		}
//		
//		
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public static void initNuanceLangMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("nuance_languages.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		NUANCE_LANGUAGES_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//		
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public static void initAbbyyLangMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("abbyy_languages.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		ABBYY_LANGUAGES_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//		
//		 
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public static void initMessagesMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("messages.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		MESSAGES_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//		
//		for (String messageType : MESSAGES_MAP.keySet()) {
//			Map<String, String> map	=	MESSAGES_MAP.get(messageType);
//			for (String langCode: map.keySet()) {
//			//	LoggerUtil.log(ConstantUtil.class, "MESSAGE_TYPE: " + messageType + " - LANG_CODE: " + langCode + " - MESSAGE: " + map.get(langCode), LOG_TYPE.TRACE);
//			}
//		}
//		
//		//LoggerUtil.log(ConstantUtil.class, "MESSAGES_MAP_INITIALIZED",LOG_TYPE.TRACE);
//	}
	
	
	
//	@SuppressWarnings("unchecked")
//	public static void initLanguageNamesMap() throws FileNotFoundException, IOException, ParseException {
//		
//		String		filename	=	getFilepath("language_names.json");
//		JSONObject	jsonObject	=	getJsonObjectFromFile(filename);
//		
//		LANGUAGE_NAMES_MAP.putAll(new Gson().fromJson(jsonObject.toJSONString(), LinkedHashMap.class));
//	}
	
	
	public static JSONObject getJsonObjectFromFile(String filename) throws FileNotFoundException, IOException, ParseException {
		
		InputStreamReader	isr	=	null;
		
		try {
			
			FileInputStream		fis	=	new FileInputStream(filename);
								isr	=	new InputStreamReader(fis, "UTF8");
			
			JSONParser	parser		=	new JSONParser();
			Object		obj			=	parser.parse(isr);
			JSONObject	jsonObject	=	(JSONObject) obj;
			
			return jsonObject;
		
		} finally {
			
		}
	}
	
	
	private static String getFilepath(String filename) {
		String filepath = SYSTEM_FOLDER;
		if (! filepath.endsWith("/"))
			filepath	=	filepath + "/";
		return filepath + "config/" + filename;
	}
	
	
//	private static String getFilepath(String filename) {
//		return filename;
//	}
	
	
	
	
	
	
}

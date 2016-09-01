package com.turkcell.bipai.saacweather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.turkcell.bipai.saacweather.model.Option;
import com.turkcell.bipai.saacweather.model.RichMedia;
import com.turkcell.bipai.saacweather.model.SaacMessage;
import com.turkcell.bipai.saacweather.model.SurveyTypeEnum;

public class SaacMessageProducer  {
	
	public SaacMessage saacMessage;
	// type 8
	public  SaacMessage produceRMM(String temperature){
		
		saacMessage = new SaacMessage(8,null);
		saacMessage.Richmediatype = "2";
		
		RichMedia richMedia = new RichMedia();
		richMedia.Title = "Baslik alani";
		richMedia.Image = "fts url";
		richMedia.Description = "aciklama alani hava degerleri " + temperature+" derece"; 
		richMedia.Urltext = "Detayli Hava Bilgisi";
		richMedia.Url = "accuweather dan link cekeriz!! ";
	
		saacMessage.RichMediaList = new ArrayList<RichMedia>();
		saacMessage.RichMediaList.add(richMedia);
		
		
		return saacMessage;
	}
	
	public  SaacMessage produceSurvey(String pollId, SurveyTypeEnum surveyTypeEnum) throws ParseException{
	
		saacMessage = new SaacMessage(8,null);
		saacMessage.Richmediatype = "2";
		
		RichMedia richMedia = new RichMedia();
		richMedia.Title = "Baslik alani";
		richMedia.Image = null;// "fts url";
	
		
		richMedia.Pollid = pollId;
		
		//calendar a bakilacak
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		Date temp = new Date();				
//		Date date = sdf.parse(temp.toString());
		
		Calendar rightNow = Calendar.getInstance();
		richMedia.PollEndTime = ( new SimpleDateFormat( "dd.MM.yyyy'T'HH:mm:ss.SSS ZZZZ " ) ).format( Calendar.getInstance().getTime() );		
		 
		richMedia.Description = "aciklama alani hava degerleri 12 derece"; 
		richMedia.Urltext = "Detayli Hava Bilgisi"; // = ;
		richMedia.Url = null; //"accuweather dan link cekeriz!! ";
		
		richMedia.Options = new ArrayList<Option>();
		
		if(surveyTypeEnum == SurveyTypeEnum.EvetHayir){
			richMedia.Options.add(new Option(1,"Evet"));
			richMedia.Options.add(new Option(2,"Hayir"));
		}
		else if(surveyTypeEnum == SurveyTypeEnum.BugunYarin)
		{
			richMedia.Options.add(new Option(3,"Bugun"));
			richMedia.Options.add(new Option(4,"Baskatarih"));
		}
		
		else if (surveyTypeEnum == SurveyTypeEnum.BaslatBaslatma){
			richMedia.Options.add(new Option(3,"Baslat"));
			richMedia.Options.add(new Option(4,"Baslatma"));
		}
				
		saacMessage.RichMediaList = new ArrayList<RichMedia>();
		saacMessage.RichMediaList.add(richMedia);
		return saacMessage;
	}

}

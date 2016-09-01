package com.turkcell.bipai.saacweather.image.manipulation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.turkcell.bipai.saacweather.model.WeatherConditionTypeEnum;
import com.turkcell.bipai.saacweather.util.ConstantUtil;

public class ImageProcessing {
	
	public   String imgLocation;
	private   String ffmpegPath;
	private String finalOutput = "C:\\Users\\TCMTOSYALI\\Desktop\\templates\\output\\sonuc.jpg";
	private String inputTemplate ;
	public ImageProcessing() {
		// TODO Auto-generated constructor stub
		getTemplate(WeatherConditionTypeEnum.Showers);
	}

	// hava durumu sonucuuna gore gelecek template ve iconlar deigisklik gosterecek parametrik
	public void getTemplate(WeatherConditionTypeEnum weatherConditionTypeEnum) {
		imgLocation = ConstantUtil.getConf("TEMPLATE_DIR");
		
		WeatherConditionTypeEnum weatherCondition = weatherConditionTypeEnum;
		switch (weatherCondition) {
		case Sunny:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Sunny");
			break;
		case IntermittentClouds:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_IntermittentClouds");
			break;
		case Cloudy:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Cloudy");
			break;
		case Fog:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Fog");
			break;
		case Showers:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Showers");
			break;
		case Storms:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Storms");
			break;
		case Snow:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Snow");
			break;
		case Clear:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Clear");
			break;
		case Rainy:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_Rainy");
			break;
		case RainyNight:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_RainyNight");
			break;
		case CloudyNight:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_CloudyNight");
			break;
		case FoggyNight:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_FoggyNight");
			break;
		case StormyNight:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_StormyNight");
			break;
		case SnowyNight:
			inputTemplate = ConstantUtil.getConf("TEMPLATE_SnowyNight");
			break;

		}
	}
	
	
	// points will be provided by designers hopefully 
	public void draw(int posX, int posY){
		try
		{
		    BufferedImage source = ImageIO.read(new File(inputTemplate));
		    BufferedImage logo = ImageIO.read(new File("C:\\Users\\TCMTOSYALI\\Desktop\\templates\\3.png"));

		    Graphics g = source.getGraphics();
		    g.drawImage(logo, posX, posY, null); // put image on given positions
		    g.drawImage(logo, posX + 440 , 170, null); // put image on given positions
		    System.out.println("rick");		    
		    
		    g.dispose();
	 
		    try {
		        // retrieve image
		        BufferedImage bi = source;
		        File outputfile = new File("C:\\Users\\TCMTOSYALI\\Desktop\\templates\\output\\sonuc.jpg");
		        ImageIO.write(bi, "jpg", outputfile);
		    } catch (IOException e) {
		    	 e.printStackTrace();
		    }

		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
	

}

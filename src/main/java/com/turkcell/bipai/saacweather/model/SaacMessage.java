package com.turkcell.bipai.saacweather.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SaacMessage {

	@Expose
	@SerializedName("type")
	public int Type;

	@Expose
	@SerializedName("message")
	public String Message;

	@SerializedName("data")
	public Data Data;
	
	public SaacMessage(int type, String message) {
		super();
		this.Type = type;
		this.Message = message;
		
		}

	public void setMessage(String message){
		this.Message = message;
	}
	
	public void setData(Data data){
		this.Data= data;
	}
	
	public void setType(int type){
		this.Type= type;
	}
	
	
	
	@SerializedName("size")
	//public int Size;
	public String Size;

	@SerializedName("ratio")
	public String Ratio; // type i degiscek

	@SerializedName("lan")
	public String Lan;
	//public float Lan;

	@SerializedName("lon")
	//public float Lon;
	public String Lon;

	@SerializedName("richmediatype")
	//public int Richmediatype;
	public String Richmediatype;

	@SerializedName("list")
	public ArrayList<RichMedia> RichMediaList;

}

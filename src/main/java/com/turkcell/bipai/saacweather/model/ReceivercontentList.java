package com.turkcell.bipai.saacweather.model;

import com.google.gson.annotations.SerializedName;

public class ReceivercontentList {
	
	public Receiver receiver;
	
	@SerializedName("content")
	public SaacMessage saacMessage;
	
	public void setReceiver(Receiver receiver){
		this.receiver = receiver;
	}
	public void setSaacMessage(SaacMessage saacMessage){
		this.saacMessage = saacMessage;
	}
}

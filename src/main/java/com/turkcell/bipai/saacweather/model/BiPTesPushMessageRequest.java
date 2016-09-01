package com.turkcell.bipai.saacweather.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BiPTesPushMessageRequest {

	
	@SerializedName("txnid")
	@Expose
	private String		txnid;
	@SerializedName("receiver")

	private Receiver	receiver;
	
	@SerializedName("receivercontentlist")
	private ArrayList<ReceivercontentList> receivercontentList;
	

	@SerializedName("content")
	private	SaacMessage		saacMessage;
	
	public void setReceivercontentList (ArrayList<ReceivercontentList> receivercontentList){
		this.receivercontentList = receivercontentList;
	}
	public String getTxnid() {
		return txnid;
	}
	
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	
	public Receiver getReceiver() {
		return receiver;
	}
	
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
	public SaacMessage getSaacMessage() {
		return saacMessage;
	}
	
	public void setSaacMessage(SaacMessage saacMessage) {
		this.saacMessage = saacMessage;
	}
	
	
}

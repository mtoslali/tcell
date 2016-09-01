package com.turkcell.bipai.saacweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receiver {

	@SerializedName("type")
	@Expose
	private int		type;
	
	@SerializedName("address")
	@Expose
	private String	address;
	
	
	public Receiver() {
		super();
	}
	
	
	public Receiver(int type, String address) {
		super();
		this.type = type;
		this.address = address;
	}
	

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}

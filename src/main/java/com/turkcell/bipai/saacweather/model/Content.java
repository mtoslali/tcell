package com.turkcell.bipai.saacweather.model;

public class Content {

	private int		type;
	private String	message;
	
	public Content() {
		super();
	}

	public Content(int type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}

package com.turkcell.bipai.saacweather.model;

import com.google.gson.annotations.SerializedName;

public class Option {

	@SerializedName("optionId")
	public int OptionId;
	@SerializedName("text")
	public String Text;

	public Option(int optionId, String text){
		this.OptionId = optionId;
		this.Text = text;
	}
	
	public void setOptionId(int optionId){
		this.OptionId = optionId;
	}
	
	public int getOptionId( ){
		return this.OptionId;
	}
	
	public void getText(String text){
		Text = text;
	}
	
	public String getText( ){
		return this.Text;
	}

}

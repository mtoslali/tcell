package com.turkcell.bipai.saacweather.model;

import java.util.Calendar;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RichMedia {

	@SerializedName("title")
	public String Title;

	@SerializedName("image")
	public String Image;

	@SerializedName("ratio")
	public float Ratio;
	//public float Ratio;
	@SerializedName("description")
	public String Description;

	@SerializedName("url")
	public String Url;

	@SerializedName("urltext")
	public String Urltext;

	@SerializedName("pollid")
	public String Pollid;
	
	@SerializedName("pollendtime")
	//public Calendar PollEndTime ;
	public String PollEndTime ; 

	@SerializedName("options")
	public List<Option> Options;

	public List<Option> getOptions(){
		return this.Options;
	}

	public void setOptions(List<Option> options){
		this.Options = options;
	}

	//public Calendar getPollEndTime(){
		public String getPollEndTime(){
		return PollEndTime;
	}
	
	//public void setPollEndTime(Calendar pollEndTime){
		public void setPollEndTime(String pollEndTime){
		this.PollEndTime = pollEndTime;
	}

	public float getRatio(){
		return this.Ratio;
	}
	
	public void setRatio(float ratio){
		this.Ratio = ratio;
	}
	
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}
	
	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		this.Image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String desc) {
		this.Description = desc;
	}
	
	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}
	
	public String getUrlText() {
		return Urltext;
	}

	public void setUrlText(String urlText) {
		this.Urltext = urlText;
	}
	
	public String pollId() {
		return Pollid;
	}

	public void setPollId(String pollId) {
		this.Pollid = pollId;
	}
	
	
	
	// pollendtime (Calendar): Anketin en son geçerlilik süresi. Girilecek tarih
	// formatı dd.MM.yyyy HH:mm:ss.SSS ZZZZ şeklinde olmalıdır.

}


package com.turkcell.bipai.saacweather.model;

public class BiPTesInput {

	private String	sender;
	private Integer	msgid;
	private String	sendtime;
	private String	type;
	private String	ctype;
	private String	content;
	private String pollid;
	private String optionid;

	public String getPollid() {
		return pollid;
	}


	public void setPollid(String pollid) {
		this.pollid = pollid;
	}
	public String getOptionId() {
		return optionid;
	}


	public void setoptionid(String OptionId) {
		this.optionid = optionid;
	}
	
	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public Integer getMsgid() {
		return msgid;
	}


	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}


	public String getSendtime() {
		return sendtime;
	}


	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCtype() {
		return ctype;
	}


	public void setCtype(String ctype) {
		this.ctype = ctype;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

}

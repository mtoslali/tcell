package com.turkcell.bipai.saacweather.command;

import java.util.List;

public interface Command {

	public String handle(String sender, List<Object> params);
	
	public String getName();
	
	

}

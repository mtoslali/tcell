package com.turkcell.bipai.saacweather.command;

import java.util.List;

public class HelpCommand implements Command {
	
	public static final String NAME		=	"help";
	
	
	@Override
	public String handle(String sender, List<Object> params) {
		return	" HelloWorld Servisi sadece yazılan mesajları karşılar. \n" + 
				" Metin olarak mesaj gönderebilirsiniz.";
	}

	
	@Override
	public String getName() {
		return NAME;
	}
	
}

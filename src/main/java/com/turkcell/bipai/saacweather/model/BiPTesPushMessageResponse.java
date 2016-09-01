package com.turkcell.bipai.saacweather.model;

public class BiPTesPushMessageResponse {

	private String	txnid;
	private int		resultcode;
	
	public String getTxnid() {
		return txnid;
	}
	
	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}
	
	public int getResultcode() {
		return resultcode;
	}
	
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}
	
}

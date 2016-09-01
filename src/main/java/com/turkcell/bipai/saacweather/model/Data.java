package com.turkcell.bipai.saacweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

	
	@SerializedName("size")
	@Expose
	private Long size;

	public Data() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return The type
	 */
	public Long getSize() {
		return size;
	}


	/**
	 * 
	 * @param type
	 *            The type
	 */
	public void setSize(Long size) {
		this.size = size;
	}


	@Override
	public String toString() {
		return "Data [size=" + size + "]";
	}
}

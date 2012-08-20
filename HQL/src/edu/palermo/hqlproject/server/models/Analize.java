package edu.palermo.hqlproject.server.models;

import java.io.Serializable;

public class Analize implements Serializable{
// {"responseType":"text","responseData":{"simpleText":"El valor es de 120"},"id":12345678}

	private static final long serialVersionUID = 3822722018121854984L;
	private String responseType;
	private ResponseData responseData;
	private Long id;
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public ResponseData getResponseData() {
		return responseData;
	}
	public void setResponseData(String responseData) {
		this.responseData = new ResponseData();
		this.responseData.setSimpleText(responseData);
	}
	
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}

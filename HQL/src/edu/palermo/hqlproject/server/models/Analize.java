package edu.palermo.hqlproject.server.models;

import java.io.Serializable;

public class Analize implements Serializable{
// {"responseType":"text","responseData":{"simpleText":"El valor es de 120"},"id":12345678}
// {"responseType":"link","responseData":{"simpleText":"El valor es de 120", "url":"http://www.google.com"},"id":12345678}
	public enum ResponseType{
		text,
		link,
		unknown;
	}

	private static final long serialVersionUID = 3822722018121854984L;
	private ResponseType responseType;
	private ResponseData responseData;
	private Long id;
	
	public ResponseType getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		try{
			this.responseType = ResponseType.valueOf(responseType);
		}catch (Exception e) {
			this.responseType = ResponseType.unknown;
		}
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

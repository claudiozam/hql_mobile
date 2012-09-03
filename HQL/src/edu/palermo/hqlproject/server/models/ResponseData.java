package edu.palermo.hqlproject.server.models;

import java.io.Serializable;

public class ResponseData implements Serializable{
	//{"simpleText":"El valor es de 120"}
	private static final long serialVersionUID = 6051719770363937445L;
	private String simpleText;
	private String url;

	public String getSimpleText() {
		return simpleText;
	}

	public void setSimpleText(String simpleText) {
		this.simpleText = simpleText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

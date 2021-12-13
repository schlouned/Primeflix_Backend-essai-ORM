package com.BackPrimeflix.response;

import java.util.HashMap;

public class Response {
	//members
	private String status;
	private String message;
	private String authToken;
	private HashMap<String, String> map;
	AlertObject alertObject;

	//getter and setters
	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String aUTH_TOKEN) {
		this.authToken = aUTH_TOKEN;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlertObject getAlertObject() {return alertObject;}

	public void setAlertObject(AlertObject alertObject) {this.alertObject = alertObject;}
}

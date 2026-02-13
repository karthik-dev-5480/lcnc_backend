package com.lcncbe.dto;

import java.io.Serializable;

public class SignupResponse implements Serializable{
	
	private String message;
	public SignupResponse(String message) {
		super();
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

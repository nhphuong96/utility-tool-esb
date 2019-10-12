package com.nhphuong.utilitytool.esb.dto;

public class ResponseWrapper<T extends Object> {

	private T body;
	private boolean success;
	private String message;
	
	public ResponseWrapper(T body, boolean success, String message) {
		super();
		this.body = body;
		this.success = success;
		this.message = message;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

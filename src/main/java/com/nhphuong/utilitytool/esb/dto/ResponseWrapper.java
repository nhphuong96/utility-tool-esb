package com.nhphuong.utilitytool.esb.dto;

public class ResponseWrapper<T extends Object> {

	private T body;
	private String message;
	private boolean success;

	public ResponseWrapper() {
		super();
	}

	public ResponseWrapper(T body, String message, boolean success) {
		super();
		this.body = body;
		this.message = message;
		this.success = success;
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

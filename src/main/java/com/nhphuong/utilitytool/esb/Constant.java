package com.nhphuong.utilitytool.esb;

public final class Constant {
	private Constant() {
		
	}
	
	public static final String PROTOCOL = "http";
	public static final String HOSTNAME = "localhost";
	public static final String PORT = "8080";
	public static final String API_ENDPOINT = "/api";
	
	public static final String USER_SERVICE_CONTEXT_PATH = "/user-service";
	
	public static String getBaseUrl(String contextPath) {
		StringBuilder url = new StringBuilder();
		url.append(PROTOCOL).append("://")
		   .append(HOSTNAME).append(":")
		   .append(PORT)
		   .append(contextPath)
		   .append(API_ENDPOINT);
		return url.toString();
	}
			
}

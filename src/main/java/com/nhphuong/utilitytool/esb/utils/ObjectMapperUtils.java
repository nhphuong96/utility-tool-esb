package com.nhphuong.utilitytool.esb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperUtils {
	
	private static ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}

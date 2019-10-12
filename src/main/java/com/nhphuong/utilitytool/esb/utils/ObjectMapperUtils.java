package com.nhphuong.utilitytool.esb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperUtils {
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}

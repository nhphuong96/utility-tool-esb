package com.nhphuong.utilitytool.esb.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;

import com.nhphuong.utilitytool.esb.exception.ExecutionException;

public abstract class AbstractAdapter {

	public String get(URL url, String requestMethod, Map<String, String> requestProperties) throws ExecutionException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(requestMethod);
			connection.addRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
			connection.addRequestProperty("Accept", "application/json");
			if (MapUtils.isNotEmpty(requestProperties)) {
				for (Entry<String, String> entry : requestProperties.entrySet()) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			InputStream is = connection.getInputStream();
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new ExecutionException("Error occurred while calling API: " + url, e);
		}
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
}

package com.nhphuong.utilitytool.esb.adapter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nhphuong.utilitytool.esb.Constant;
import com.nhphuong.utilitytool.esb.dto.UserDTO;
import com.nhphuong.utilitytool.esb.exception.ExecutionException;
import com.nhphuong.utilitytool.esb.utils.ObjectMapperUtils;

@Component
public class UserServiceAdapter {
	
	private static final Logger log = Logger.getLogger(UserServiceAdapter.class);
	
	private static final String GET_USER_API = "/getUser";
	
	public UserDTO getUserByUsername(String username) {
		UserDTO user = null;
		if (StringUtils.isNotEmpty(username)) {
			try {
				String query = String.format("username=%s", URLEncoder.encode(username, StandardCharsets.UTF_8.name()));
				URL url = new URL(Constant.getBaseUrl() + GET_USER_API + "?" + query);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod(RequestMethod.GET.name());
				connection.addRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
				connection.addRequestProperty("Accept", "application/json");
				
				String response = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
				if (connection.getResponseCode() != 200) {
					throw new ExecutionException("HTTP Error: " + response);
				}
				user = ObjectMapperUtils.getObjectMapper().readValue(response, UserDTO.class);
				
			} catch (Exception e) {
				log.error("Error occurred while fetching user: " + e.getMessage(), e);
			}
		}
		return user;
	}
}

package com.nhphuong.utilitytool.esb.adapter;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nhphuong.utilitytool.esb.Constant;
import com.nhphuong.utilitytool.esb.dto.ResponseWrapper;
import com.nhphuong.utilitytool.esb.dto.RoleDTO;
import com.nhphuong.utilitytool.esb.dto.UserDTO;
import com.nhphuong.utilitytool.esb.exception.ExecutionException;
import com.nhphuong.utilitytool.esb.utils.ObjectMapperUtils;

@Component
public class UserServiceAdapter extends AbstractAdapter {
	
	private static final String GET_USER_API = "/user/getUser";
	private static final String GET_USER_ROLE_API = "/user/getRoles";
	
	public UserDTO getUserByUsername(String username) throws ExecutionException {
		UserDTO user = null;
		try {
			String query = String.format("username=%s", URLEncoder.encode(username, StandardCharsets.UTF_8.name()));
			URL url = new URL(Constant.getBaseUrl(Constant.USER_SERVICE_CONTEXT_PATH) + GET_USER_API + "?" + query);
			String result = super.get(url, RequestMethod.GET.name(), null);
			ResponseWrapper<UserDTO> value = ObjectMapperUtils.getObjectMapper().readValue(result, new TypeReference<ResponseWrapper<UserDTO>>() {});
			user = value.getBody();
		} catch (Exception e) {
			throw new ExecutionException("Error occurred while fetching user: " + e.getMessage(), e);
		}
		return user;
	}
	
	public List<RoleDTO> getUserRolesByUsername(String username) throws ExecutionException {
		List<RoleDTO> roles = null;
		try {
			String query = String.format("username=%s", URLEncoder.encode(username, StandardCharsets.UTF_8.name()));
			URL url = new URL(Constant.getBaseUrl(Constant.USER_SERVICE_CONTEXT_PATH) + GET_USER_ROLE_API + "?" + query);
			String result = super.get(url, RequestMethod.GET.name(), null);
			ResponseWrapper<RoleDTO[]> value = ObjectMapperUtils.getObjectMapper().readValue(result, new TypeReference<ResponseWrapper<RoleDTO[]>>() {
			});
			roles = Arrays.asList(value.getBody());
		} catch (Exception e) {
			throw new ExecutionException("Error occurred while fetching user role: " + e.getMessage(), e);
		}
		return roles;
	}

}

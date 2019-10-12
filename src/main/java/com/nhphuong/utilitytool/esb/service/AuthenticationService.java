package com.nhphuong.utilitytool.esb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
	void addAuthentication(HttpServletResponse response, String username);
	Authentication getAuthentication(HttpServletRequest request);
	String generateToken(String username);
}

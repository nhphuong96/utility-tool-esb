package com.nhphuong.utilitytool.esb.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.nhphuong.utilitytool.esb.service.AuthenticationService;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private static final Logger log = Logger.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	private AuthenticationService tokenAuthenticationService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("JWTAuthenticationFilter.doFilter");

		Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) request);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}

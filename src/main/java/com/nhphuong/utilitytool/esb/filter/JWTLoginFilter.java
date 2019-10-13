package com.nhphuong.utilitytool.esb.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsUtils;

import com.nhphuong.utilitytool.esb.dto.LoginDTO;
import com.nhphuong.utilitytool.esb.dto.ResponseWrapper;
import com.nhphuong.utilitytool.esb.service.AuthenticationService;
import com.nhphuong.utilitytool.esb.utils.ObjectMapperUtils;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = Logger.getLogger(JWTLoginFilter.class);
	
	@Autowired
	private AuthenticationService tokenAuthenticationService;

	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url, RequestMethod.POST.name()));
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (CorsUtils.isPreFlightRequest(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			return null;
		}

		String jsonLoginInfo = IOUtils.toString(request.getReader());
		LoginDTO loginDTO = ObjectMapperUtils.getObjectMapper().readValue(jsonLoginInfo, LoginDTO.class);
		
		String username = loginDTO.getUsername();
		String password = loginDTO.getPassword();
		
		log.info(String.format("JWTLoginFilter.attemptAuthentication: username/password= %s,%s",
				username, password));

		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("JWTLoginFilter.successfulAuthentication: ");
		String jwtToken = tokenAuthenticationService.generateToken(authResult.getName());
		if (StringUtils.isNotEmpty(jwtToken)) {
			log.info("Authorization string: " + jwtToken);
			ResponseWrapper<String> tokenResponse = new ResponseWrapper<String>(jwtToken, "success", true);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().write(ObjectMapperUtils.getObjectMapper().writeValueAsString(tokenResponse).getBytes());
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		log.info("JWTLoginFilter.unsuccessfulAuthentication: ");
		ResponseWrapper<String> unauthorizedResponse = new ResponseWrapper<String>(null, "unauthorized", false);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().write(ObjectMapperUtils.getObjectMapper().writeValueAsString(unauthorizedResponse).getBytes());
		return;
	}

	
	
}

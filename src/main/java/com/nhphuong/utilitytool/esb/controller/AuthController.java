package com.nhphuong.utilitytool.esb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhphuong.utilitytool.esb.dto.LoginDTO;

@RestController(value = "/api/auth")
public class AuthController {
	
	@PostMapping(value = "/login")
	public void login(@RequestBody LoginDTO user, HttpServletRequest request) {
	}
	
}

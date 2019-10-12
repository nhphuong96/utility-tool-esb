package com.nhphuong.utilitytool.esb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nhphuong.utilitytool.esb.adapter.UserServiceAdapter;
import com.nhphuong.utilitytool.esb.dto.UserDTO;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserServiceAdapter adapter;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDTO user = findUser(username);

		UserBuilder userBuilder = null;
		if (user != null) {
			userBuilder = User.withUsername(username);
			userBuilder.password(encoder.encode(user.getPassword()));
			userBuilder.roles(user.getRoles());
		} else {
			throw new UsernameNotFoundException("User not found");
		}
		
		return userBuilder.build();
	}

	private UserDTO findUser(String username) {
		UserDTO user = adapter.getUserByUsername(username);
		return user;
	}

}

package com.nhphuong.utilitytool.esb.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nhphuong.utilitytool.esb.adapter.UserServiceAdapter;
import com.nhphuong.utilitytool.esb.dto.RoleDTO;
import com.nhphuong.utilitytool.esb.dto.UserDTO;
import com.nhphuong.utilitytool.esb.exception.ExecutionException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private static final Logger log = Logger.getLogger(UserDetailServiceImpl.class);

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserServiceAdapter adapter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Username is empty.");
		}
		
		UserDetails userDetails = null;
		try {
			UserDTO user = findUser(username);
			List<RoleDTO> userRole = getUserRoles(username);
			userDetails = User.withUsername(username).password(encoder.encode(user.getPassword())).authorities(userRole).build();
		} catch (ExecutionException e) {
			log.error("Error occurred: " + e.getMessage(), e);
		}
		return userDetails;
	}

	private List<RoleDTO> getUserRoles(String username) throws ExecutionException {
		List<RoleDTO> userRoles = adapter.getUserRolesByUsername(username);
		if (CollectionUtils.isEmpty(userRoles)) {
			throw new ExecutionException("User roles is empty. User must has at least one role.");
		}
		return userRoles;
	}

	private UserDTO findUser(String username) throws ExecutionException {
		UserDTO user = adapter.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found.");
		}
		return user;
	}

}

package com.nhphuong.utilitytool.esb.adapter;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nhphuong.utilitytool.esb.dto.RoleDTO;
import com.nhphuong.utilitytool.esb.dto.UserDTO;
import com.nhphuong.utilitytool.esb.exception.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceAdapterTest {

	@Autowired
	private UserServiceAdapter adapter;
	
	@Test
	public void test_GetUserByUsername_WhenUserExists_ShouldReturnUser() throws ExecutionException {
		UserDTO user = adapter.getUserByUsername("admin");
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getFirstName(), "Phuong");
		Assert.assertEquals(user.getLastName(), "Nguyen");
		Assert.assertEquals(user.getUsername(), "admin");
	}
	
	@Test(expected = ExecutionException.class)
	public void test_GetUserByUsername_WhenUsernameEmpty_ShouldReturnNull() throws ExecutionException {
		adapter.getUserByUsername(null);
	}
	
	@Test
	public void test_GetUserByUsername_WhenUserNotExists_ShouldReturnNull() throws ExecutionException {
		UserDTO user = adapter.getUserByUsername("abcdef");
		Assert.assertNull(user);
	}
	
	@Test
	public void test_GetUserRolesByUsername_WhenUserExists_ShouldReturnListOfRole() throws ExecutionException {
		List<RoleDTO> roles = adapter.getUserRolesByUsername("admin");
		Assert.assertNotNull(roles);
		Assert.assertEquals(roles.size(), 1);
		Assert.assertEquals(roles.get(0).getRoleId(), "ADMIN");
	}
	
	@Test(expected = ExecutionException.class)
	public void test_GetUserRolesByUsername_WhenUserNotExists_ShouldReturnNull() throws ExecutionException {
		adapter.getUserRolesByUsername(null);
	}
	
	@Test
	public void test_GetUserRolesByUsername_WhenUsernameEmpty_ShouldReturnNull() throws ExecutionException {
		List<RoleDTO> roles = adapter.getUserRolesByUsername("abcdef");
		Assert.assertNotNull(roles);
		Assert.assertEquals(roles.size(), 0);
	}
	
}

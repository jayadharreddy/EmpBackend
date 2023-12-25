package com.employee.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.controller.UserController;
import com.employee.model.User;
import com.employee.repository.UserRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserController.class})
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private UserController userController;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void testCreateUser() {
		User newUser= new User();
		newUser.setUsername("testUser");
		newUser.setPassword("testPwd");
		
		when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null);
		when(userRepository.save(newUser)).thenReturn(newUser);
		
		ResponseEntity<String> response= userController.addUser(newUser);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
}

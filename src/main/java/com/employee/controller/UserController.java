package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.User;
import com.employee.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		User existingUser=userRepository.findByUsername(user.getUsername());
		if(existingUser!= null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Username Already Exists");
		}else {
			userRepository.save(user);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("User Created Successfully");
		}
		//save the user to DataBase
		//return userRepository.save(user);
	}
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		if(!user.getUsername().equals(updatedUser.getUsername())) {
			User existingUser= userRepository.findByUsername(updatedUser.getUsername());
			if(existingUser!= null) {
				throw new RuntimeException("Username Already Exists");
			}
		}
		user.setUsername(updatedUser.getUsername());
		user.setPassword(updatedUser.getPassword());
		
		return userRepository.save(user);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/validateLogin")
	public String validateLoginData(@RequestBody User user){
		System.out.println("data:"+user);
		User response = userRepository.findByUsername(user.getUsername());
		System.out.println("user: "+response.getUsername()+","+response.getPassword());
		if( response.getUsername().equals(user.getUsername()) &&response.getPassword().equals(user.getPassword())) {
			return "0";
		}
		return "-1";
	}
	
	
	
}

package com.employee.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.employee.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	//User validateLoginData(String username);

}

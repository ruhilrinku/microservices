package com.rbs.cloud.gateway.service;

import java.util.List;
import java.util.Optional;

import com.rbs.cloud.gateway.model.User;
import com.rbs.cloud.gateway.model.UserDto;

public interface UserService {
	
	 public User findOne(String username);

	 public User save(UserDto user);

	 public List<User> findAll();

	 public Optional<User> findById(int id);

	 public User update(UserDto userDto);

	 public void delete(int id);
}

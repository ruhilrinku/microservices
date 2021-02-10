package com.rbs.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbs.user.VO.ResponseTemplate;
import com.rbs.user.entity.User;
import com.rbs.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public User saveUser(@RequestBody User user) {
		log.info("inside saveUser method of UserController");
		return userService.saveUser(user);
	}
	
	@GetMapping("/{id}")
	public ResponseTemplate getUserByUserId(@PathVariable("id") Long userId) {
		log.info("inside getUserbyId method of UserController");
		return userService.getUserbyId(userId);
	}
}

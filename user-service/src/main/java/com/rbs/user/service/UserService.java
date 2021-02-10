package com.rbs.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rbs.user.VO.Department;
import com.rbs.user.VO.ResponseTemplate;
import com.rbs.user.entity.User;
import com.rbs.user.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;

	public User saveUser(User user) {
		log.info("inside saveUser method of UserService");
		return userRepository.save(user);
	}

	public ResponseTemplate getUserbyId(Long userId) {
		log.info("inside getUserbyId method of UserService");
		ResponseTemplate responseTemplate = new ResponseTemplate();
		User user = userRepository.findUserByUserId(userId);
		
		Department department = 
				restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(), 
						Department.class);
		
		responseTemplate.setUser(user);
		responseTemplate.setDepartment(department);
		
		return responseTemplate;
	}
}

package com.rbs.cloud.gateway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rbs.cloud.gateway.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
}

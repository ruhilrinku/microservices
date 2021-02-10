package com.rbs.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rbs.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByUserId(Long userId);

}

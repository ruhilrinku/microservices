package com.rbs.cloud.gateway.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rbs.cloud.gateway.dao.UserDao;
import com.rbs.cloud.gateway.model.User;
import com.rbs.cloud.gateway.model.UserDto;

@Service
public class UserServiceImpl implements UserDetailsService, UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
		
	}
	
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
    public User save(UserDto user) {
	    User newUser = new User();
	    newUser.setUsername(user.getUsername());
	    newUser.setFirstname(user.getFirstName());
	    newUser.setLastname(user.getLastName());
	    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setAge(user.getAge());
		newUser.setSalary(user.getSalary());
        return userDao.save(newUser);
    }

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Optional<User> findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public User update(UserDto userDto) {
		Optional<User> userOpt = findById(userDto.getId());
		User user = userOpt.get();
		user.setUsername(userDto.getUsername());
		user.setFirstname(userDto.getFirstName());
		user.setLastname(userDto.getLastName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAge(userDto.getAge());
		user.setSalary(userDto.getSalary());
		return userDao.save(user);
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);
	}

}

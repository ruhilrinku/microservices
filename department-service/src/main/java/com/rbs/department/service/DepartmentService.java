package com.rbs.department.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbs.department.entity.Department;
import com.rbs.department.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
	
	@Autowired
	DepartmentRepository departmentRepository;

	public Department saveDepartment(Department department) {
		log.info("inside saveDepartment method of DepartmentService");
		return departmentRepository.save(department);
	}

	public Department getDepartmentById(Long id) {
		log.info("inside getDepartmentById method of DepartmentService");
		return departmentRepository.findDepartmentByDepartmentId(id);
	}
}

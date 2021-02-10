package com.rbs.department.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rbs.department.entity.Department;
import com.rbs.department.service.DepartmentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/departments")
public class DepartmentController {
	
	private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	DepartmentService departmentService;
	
	@RequestMapping(value="/", method = RequestMethod.POST ,  consumes = "application/json", produces = "application/json")
	public Department saveDepartment(@RequestBody Department department) {
		log.info("inside saveDepartment method of DepartmentController");
		return departmentService.saveDepartment(department);
	}
	
	@GetMapping(value="/{id}", produces = "application/json")
	public Department getDepartmentById(@PathVariable("id") Long id) {
		log.info("inside getDepartmentById method of DepartmentController");
		return departmentService.getDepartmentById(id);
	}
}

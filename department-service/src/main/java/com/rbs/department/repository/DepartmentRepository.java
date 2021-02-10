package com.rbs.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rbs.department.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	public Department findDepartmentByDepartmentId(Long id);

}

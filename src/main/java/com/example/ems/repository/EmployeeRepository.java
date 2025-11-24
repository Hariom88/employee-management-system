package com.example.ems.repository;

import com.example.ems.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByDepartment(String department, Pageable pageable);

    Page<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);

    boolean existsByEmail(String email);
}

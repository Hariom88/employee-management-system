package com.example.ems.service;

import com.example.ems.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id);

    Page<EmployeeDTO> getAllEmployees(int page, int size, String sortBy, String sortDir, String department,
            String name);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);
}

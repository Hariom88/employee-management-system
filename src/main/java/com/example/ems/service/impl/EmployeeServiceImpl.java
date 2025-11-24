package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.entity.Employee;
import com.example.ems.exception.EmailAlreadyExistsException;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists for employee");
        }

        Employee employee = mapToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDTO(employee);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(int page, int size, String sortBy, String sortDir, String department,
            String name) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees;

        if (department != null && !department.isEmpty()) {
            employees = employeeRepository.findByDepartment(department, pageable);
        } else if (name != null && !name.isEmpty()) {
            employees = employeeRepository.findByFirstNameContainingOrLastNameContaining(name, name, pageable);
        } else {
            employees = employeeRepository.findAll(pageable);
        }

        return employees.map(this::mapToDTO);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (!employee.getEmail().equals(employeeDTO.getEmail())
                && employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists for employee");
        }

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setDepartment(employeeDTO.getDepartment());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setJoiningDate(employee.getJoiningDate());
        employeeDTO.setDepartment(employee.getDepartment());
        return employeeDTO;
    }

    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setJoiningDate(employeeDTO.getJoiningDate());
        employee.setDepartment(employeeDTO.getDepartment());
        return employee;
    }
}

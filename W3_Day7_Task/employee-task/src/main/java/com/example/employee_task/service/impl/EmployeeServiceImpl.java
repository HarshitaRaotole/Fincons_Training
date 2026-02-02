package com.example.employee_task.service.impl;

import com.example.employee_task.entity.Employee;
import com.example.employee_task.repository.EmployeeRepository;
import com.example.employee_task.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    @Override
    public Employee createEmployee(Employee employee){
        if(employeeRepository.findByEmail(employee.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");

        }
        return employeeRepository.save(employee);

    }

    @Override
    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
    }
    @Override
    public List<Object[]> getEmployeeTaskCount(){
        return employeeRepository.fetchEmployeeTaskCount();
    }

}

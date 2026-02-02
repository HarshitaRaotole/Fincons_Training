package com.example.employee_task.service;

import com.example.employee_task.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    List<Object[]> getEmployeeTaskCount();
}

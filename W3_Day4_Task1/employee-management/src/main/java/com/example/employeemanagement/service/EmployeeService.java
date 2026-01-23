package com.example.employeemanagement.service;

import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.InvalidSalaryException;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {


        @Autowired
        private EmployeeRepository repository;

        public Employee getEmployeeById(int id) {
            return repository.findById(id)
                    .orElseThrow(() ->
                            new EmployeeNotFoundException("Employee not found with id: " + id));
        }

        public Employee addEmployee(Employee employee) {

            if (employee.getSalary() <= 0) {
                throw new InvalidSalaryException("Salary must be greater than zero");
            }

            return repository.save(employee);
        }
    }

}

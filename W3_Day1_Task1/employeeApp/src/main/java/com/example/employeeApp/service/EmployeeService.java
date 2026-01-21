package com.example.employeeApp.service;

import com.example.employeeApp.model.Employee;
import com.example.employeeApp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public Employee createEmployee(Employee employee){

        if (employee.getName()== null || employee.getName().isEmpty()){
            throw new IllegalArgumentException("Name must not be empty!");
        }
        if(employee.getSalary() <= 0){
            throw new IllegalArgumentException("Salary must be positive");
        }

        employee.setId(idCounter.getAndIncrement());
        return repository.save(employee);

    }
    public Employee getEmployeeById(int id){
        return repository.findById(id);
    }
}

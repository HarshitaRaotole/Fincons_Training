package com.example.employeeApp.repository;

import com.example.employeeApp.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public Employee save(Employee employee){
        employees.add(employee);
        return employee;
    }

    public Employee findById(int id){
        for (Employee emp:employees){
            if(emp.getId()==id){
                return emp;
            }
        }
        return null;
    }

}

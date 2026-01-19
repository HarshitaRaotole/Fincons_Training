package com.example.employee.controller;

import com.example.employee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private List<Employee> employeeList = new ArrayList<>();
    public EmployeeController(){
        employeeList.add(new Employee(101,"Harshita","IT"));
        employeeList.add(new Employee(102,"Steve","HR"));
        employeeList.add(new Employee(103,"Mike","Finance"));
        employeeList.add(new Employee(104,"Nancy","CS"));
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        for(Employee emp : employeeList){
            if(emp.getId()==id){
                return emp;
            }
        }
        return null;
    }

}

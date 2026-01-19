package com.example.employeeSearch.controller;

import com.example.employeeSearch.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private List<Employee> employeeList = new ArrayList<>();
    public EmployeeController(){
        employeeList.add(new Employee(101,"Harshita","IT",60000));
        employeeList.add(new Employee(102,"Nancy","CS",40000));
        employeeList.add(new Employee(103,"Dustin","CS",30000));
        employeeList.add(new Employee(104,"Mike","IT",50000));
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minSalary
    ) {
        return employeeList.stream()
                .filter(emp -> department == null || emp.getDepartment().equalsIgnoreCase(department))
                .filter(emp -> minSalary == null || emp.getSalary() >= minSalary)
                .collect(Collectors.toList());



    }


}

package com.example.employee.controller;

import com.example.employee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeCOntroller {
    private List<Employee> employeeList = new ArrayList<>();
    public EmployeeCOntroller(){
        employeeList.add(new Employee(101,"Harshita","IT",60000));
        employeeList.add(new Employee(102,"Erica","HR",40000));
        employeeList.add(new Employee(103,"Nancy","CS",250000));
        employeeList.add(new Employee(104,"Mike","Finance",50000));
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        for (Employee emp: employeeList){
            if(emp.getId()==id){
                return emp;
            }
        }
        return null;
    }
    @PutMapping("/{id}/salary")
    public Employee updateSalary(
            @PathVariable int id,
            @RequestBody Map<String,Double> requestBody )
    {
        double newSalary = requestBody.get("salary");
        for (Employee emp : employeeList) {
            if (emp.getId()==id){
                emp.setSalary(newSalary);
                return emp;
            }
        }
        return null;
    }
}

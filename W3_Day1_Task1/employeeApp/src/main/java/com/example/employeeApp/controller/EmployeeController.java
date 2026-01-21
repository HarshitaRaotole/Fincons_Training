package com.example.employeeApp.controller;

import com.example.employeeApp.model.Employee;
import com.example.employeeApp.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){
        this.service=service;
    }
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee){
        return service.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id){
        return service.getEmployeeById(id);
    }
}

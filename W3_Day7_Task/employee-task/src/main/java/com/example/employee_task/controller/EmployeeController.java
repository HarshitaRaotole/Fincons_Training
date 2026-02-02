package com.example.employee_task.controller;

import com.example.employee_task.entity.Employee;
import com.example.employee_task.repository.EmployeeRepository;
import com.example.employee_task.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/employees")

public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody @Valid Employee employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/task-count")
    public List<Object[]> getEmployeeTaskCount(){
        return employeeService.getEmployeeTaskCount();
    }


}

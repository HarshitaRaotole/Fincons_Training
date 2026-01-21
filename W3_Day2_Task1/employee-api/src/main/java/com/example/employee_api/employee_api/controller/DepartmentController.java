package com.example.employee_api.employee_api.controller;

import com.example.employee_api.employee_api.entity.Department;
import com.example.employee_api.employee_api.entity.Employee;
import com.example.employee_api.employee_api.repository.DepartmentRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        if(department.getEmployee() != null){
            for (Employee emp : department.getEmployee()){
                emp.setDepartment(department);
            }
        }
        return departmentRepository.save(department);
    }

    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
}

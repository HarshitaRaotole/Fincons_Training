package com.example.employee.controller;

import com.example.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private List<Employee> employeeList=new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(100);

    @PostMapping
    public Employee createEmployee(@RequestBody Map<String,Object> requestBody){
        String name = (String) requestBody.get("name");
        String department = (String) requestBody.get("department");
        Double salary = ((Number) requestBody.get("salary")).doubleValue();

        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name must not be empty");
        }
        if(salary<=0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Salary must be positive"
            );
        }

        int id = idCounter.incrementAndGet();
        Employee employee = new Employee(id,name,department,salary);
        employeeList.add(employee);

        return employee;

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

    @GetMapping
    public List<Employee> getEmployeeByDepartment(
            @RequestParam(required = false) String department){
        if(department == null){
            return employeeList;
        }
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employeeList){
            if(emp.getDepartment().equalsIgnoreCase(department)){
                result.add(emp);
            }
        }
        return result;
    }

}

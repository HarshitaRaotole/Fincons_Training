package com.example.employee.controlller;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Task1 and Task 2 : Create Employee and Validate Salary
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        if (employee.getSalary() <= 0) {
            return ResponseEntity.badRequest().body("Error salary must be positive");
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    //Task3 :  Update Salary

    @PutMapping("/{id}/salary")
    public ResponseEntity<?> updateSalary(@PathVariable Long id, @RequestBody Double newSalary){
        if(newSalary== null || newSalary<0){
            return ResponseEntity.badRequest().body("Error : Salary cannot be negative");
        }

        Optional<Employee> employeeOpt = employeeRepository.findById(id);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            //Updating salary
            employee.setSalary(newSalary);

            //saving changes
            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);

        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with id : "+id);
        }
    }

    //Task 4 : Get all employees and Get employee by id

    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeBYId(@PathVariable Long id){
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            return ResponseEntity.ok(employee.get());

        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");

        }
    }

    //Query method : get by Dept name
    @GetMapping("/filter/dept/{deptName}")
    public List<Employee> getBySalaryGt(@PathVariable String deptName){
        return employeeRepository.findByDepartmentName(deptName);
    }

    //Query method : get by salary > x
    @GetMapping("/filter/salary/{amount}")
    public List<Employee> getBySalaryGt(@PathVariable double amount){
        return employeeRepository.findBySalaryGreaterThan(amount);
    }


}


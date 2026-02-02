package com.example.employee_task.repository;

import com.example.employee_task.entity.Employee;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query(
    value = "Select e.name, Count(t.id) from employee e Left Join task t On e.id = t.employee_id Group By e.id",
            nativeQuery = true)
    List<Object[]> fetchEmployeeTaskCount();


}

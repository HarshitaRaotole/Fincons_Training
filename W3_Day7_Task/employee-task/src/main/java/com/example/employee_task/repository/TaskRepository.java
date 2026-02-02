package com.example.employee_task.repository;
import com.example.employee_task.entity.Task;
import com.example.employee_task.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByEmployeeId(Long employeeId);
    List<Task> findByStatus(TaskStatus status);

}

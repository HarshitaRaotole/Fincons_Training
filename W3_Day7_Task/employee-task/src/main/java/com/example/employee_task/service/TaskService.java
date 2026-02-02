package com.example.employee_task.service;

import com.example.employee_task.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Long employeeId, Task task);
    List<Task> getTasksByEmployee(Long employeeId);
}

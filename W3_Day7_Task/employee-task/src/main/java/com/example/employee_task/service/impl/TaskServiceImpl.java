package com.example.employee_task.service.impl;

import com.example.employee_task.entity.Employee;
import com.example.employee_task.entity.Task;
import com.example.employee_task.repository.EmployeeRepository;
import com.example.employee_task.repository.TaskRepository;
import com.example.employee_task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           EmployeeRepository employeeRepository){
        this.taskRepository=taskRepository;
        this.employeeRepository=employeeRepository;
    }

    @Override
    public Task createTask(Long employeeId, Task task){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        task.setEmployee(employee);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByEmployee(Long employeeId){
        return taskRepository.findByEmployeeId(employeeId);
    }
}

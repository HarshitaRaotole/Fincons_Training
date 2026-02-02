package com.example.employee_task.controller;

import com.example.employee_task.entity.Task;
import com.example.employee_task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")


public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping("/employee/{employeeId}")
    public Task createTask(@PathVariable Long employeeId,
                           @RequestBody @Valid Task task){
        return taskService.createTask(employeeId,task);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Task> getTasks(@PathVariable Long employeeId){
        return taskService.getTasksByEmployee(employeeId);
    }
}

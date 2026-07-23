package com.alikaracor.learning.springrestbasics.controller;

import com.alikaracor.learning.springrestbasics.dto.TaskRequest;
import com.alikaracor.learning.springrestbasics.dto.TaskResponse;
import com.alikaracor.learning.springrestbasics.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskResponse);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(
            @PathVariable("taskId") Long taskId
    ) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(
            @PathVariable("taskId") Long taskId,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        return taskService.updateTask(taskId, taskRequest);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("taskId") Long taskId
    ) {
        taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }
}

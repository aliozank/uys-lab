package com.alikaracor.learning.springrestbasics.service;

import com.alikaracor.learning.springrestbasics.dto.TaskRequest;
import com.alikaracor.learning.springrestbasics.dto.TaskResponse;
import com.alikaracor.learning.springrestbasics.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(1);


    public TaskResponse createTask(TaskRequest taskRequest) {

        Task task = new Task();

        task.setId(idGenerator.getAndIncrement());
        task.setDescription(taskRequest.getDescription());
        task.setTitle(taskRequest.getTitle());
        task.setCompleted(taskRequest.isCompleted());

        tasks.add(task);

        return toResponse(task);
    }

    public List<TaskResponse> getAllTasks() {
        return tasks.stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long taskId) {
        Task task = findTaskById(taskId);

        return toResponse(task);
    }

    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = findTaskById(taskId);

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());

        return toResponse(task);
    }

    public void deleteTask(Long taskId) {
        Task task = findTaskById(taskId);

        tasks.remove(task);
    }

    private Task findTaskById(Long taskId) {
        return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile eşleşen görev bulunamadı"
                ));
    }

    private TaskResponse toResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setCompleted(task.isCompleted());

        return taskResponse;
    }
}

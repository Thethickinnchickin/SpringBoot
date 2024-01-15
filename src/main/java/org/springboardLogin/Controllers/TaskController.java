package org.springboardLogin.Controllers;


import org.springboardLogin.DTOs.TaskDTO;
import org.springboardLogin.Entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springboardLogin.Services.TaskService;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            System.out.print("ID:  --> " + task.getId());
            TaskDTO taskDTO = new TaskDTO(
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority()
            );
            taskDTOs.add(taskDTO);
        }

        return ResponseEntity.ok(taskDTOs);
    }


    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task);
            return ResponseEntity.ok("Task Created");
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        // Fetch the existing task by id
        Optional<Task> existingTaskOptional = taskService.getTaskById(id);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Update the existing task properties
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setPriority(taskDTO.getPriority());

            // Save the updated task
            Task updatedTask = taskService.updateTask(existingTask);

            // Convert the updated task to TaskDTO
            TaskDTO updatedTaskDTO = new TaskDTO(
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    updatedTask.getDueDate(),
                    updatedTask.getPriority()
            );

            return ResponseEntity.ok(updatedTaskDTO);
        } else {
            // Task with the given id not found
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Deleted Thing");
    }
}

package org.springboardLogin.Controllers;

import org.springboardLogin.DTOs.TaskDTO;
import org.springboardLogin.Entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springboardLogin.Services.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Retrieve all tasks and convert them to TaskDTOs
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task task : tasks) {
            // Convert each Task to TaskDTO
            TaskDTO taskDTO = new TaskDTO(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority()
            );
            taskDTOs.add(taskDTO);
        }

        return ResponseEntity.ok(taskDTOs);
    }

    // Retrieve a specific task by ID
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id).orElse(null);
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            // Try creating the task
            System.out.println("Creating Task");
            taskService.createTask(task);
            return ResponseEntity.ok("Task Created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable String id, @RequestBody TaskDTO taskDTO) {
        // Fetch the existing task by ID
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
                    updatedTask.getId(),
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    updatedTask.getDueDate(),
                    updatedTask.getPriority()
            );

            return ResponseEntity.ok(updatedTaskDTO);
        } else {
            // Task with the given ID not found
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task Deleted");
    }
}

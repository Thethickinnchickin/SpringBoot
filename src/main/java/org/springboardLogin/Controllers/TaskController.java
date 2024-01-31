package org.springboardLogin.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springboardLogin.DTOs.TaskDTO;
import org.springboardLogin.Entities.Task;
import org.springboardLogin.Security.JwtTokenProvider;
import org.springboardLogin.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Retrieve all tasks and convert them to TaskDTOs
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(HttpServletRequest request) {
        // Log information about retrieving all tasks
        System.out.println("Retrieving all tasks");

        // Extract JWT token from the request
        String authToken = jwtTokenProvider.resolveToken(request);

        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Your existing code for getting tasks...
        List<Task> tasks = taskService.getAllTasks(authToken);
        List<TaskDTO> taskDTOs = new ArrayList<>();

        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {
                // Convert each Task to TaskDTO
                TaskDTO taskDTO = new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getDueDate(),
                        task.getPriority(),
                        task.getIsCompleted()
                );
                taskDTOs.add(taskDTO);
            }

            // Return tasks along with Bearer token in the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
            return ResponseEntity.ok().headers(headers).body(taskDTOs);
        }

        return ResponseEntity.ok(taskDTOs);
    }

    // Retrieve a specific task by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id, HttpServletRequest request) {
        // Log information about retrieving a specific task
        System.out.println("Retrieving task by ID");

        // Extract JWT token from the request
        String authToken = jwtTokenProvider.resolveToken(request);

        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        Optional<Task> taskOptional = taskService.getTaskById(id, authToken);

        if (taskOptional.isPresent()) {
            // Task found, return it
            Task task = taskOptional.get();
            return ResponseEntity.ok(task);
        } else {
            // Task not found
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, HttpServletRequest request) {
        // Log information about creating a new task
        System.out.println("Creating a new task");

        try {
            // Try creating the task
            String authToken = jwtTokenProvider.resolveToken(request);

            if (authToken == null) {
                // Handle the case where the token is null (e.g., unauthorized access)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }

            // Set default value for 'isCompleted' property
            taskDTO.setIsCompleted(false);

            // Call the service to create the task
            taskService.createTask(taskDTO, authToken);

            // Return the created task
            return ResponseEntity.ok(taskDTO);
        } catch (Exception e) {
            // Handle any exceptions during task creation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id,
                                        @RequestBody TaskDTO taskDTO,
                                        HttpServletRequest request) {
        // Log information about updating an existing task
        System.out.println("Updating an existing task");

        // Extract JWT token from the request
        String authToken = jwtTokenProvider.resolveToken(request);

        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // Fetch the existing task by ID
        Optional<Task> existingTaskOptional = taskService.getTaskById(id, authToken);

        if (existingTaskOptional.isPresent()) {
            // Task found, update its properties
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setDescription(taskDTO.getDescription());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setPriority(taskDTO.getPriority());
            existingTask.setIsCompleted(taskDTO.getIsCompleted());

            // Save the updated task
            Task updatedTask = taskService.updateTask(existingTask, authToken);

            // Convert the updated task to TaskDTO
            TaskDTO updatedTaskDTO = new TaskDTO(
                    updatedTask.getId(),
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    updatedTask.getDueDate(),
                    updatedTask.getPriority(),
                    updatedTask.getIsCompleted()
            );

            // Return the updated task
            return ResponseEntity.ok(updatedTaskDTO);
        } else {
            // Task with the given ID not found
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id, HttpServletRequest request) {
        // Log information about deleting a task
        System.out.println("Deleting a task");

        // Extract JWT token from the request
        String authToken = jwtTokenProvider.resolveToken(request);

        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // Call the service to delete the task
        taskService.deleteTask(id, authToken);

        // Return a success message
        return ResponseEntity.ok("Task Deleted");
    }
}

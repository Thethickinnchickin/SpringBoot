package org.springboardLogin.Controllers;

import org.springboardLogin.DTOs.TaskDTO;
import org.springboardLogin.Entities.Task;
import org.springboardLogin.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springboardLogin.Services.TaskService;
import org.springframework.http.HttpHeaders;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") // Adjust the origin based on your frontend URL
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
        try {
            // Try creating the task
            String authToken = jwtTokenProvider.resolveToken(request);

            if (authToken == null) {
                // Handle the case where the token is null (e.g., unauthorized access)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }
            taskDTO.setIsCompleted(false);
            taskService.createTask(taskDTO, authToken);
            return ResponseEntity.ok(taskDTO);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id,
                                              @RequestBody TaskDTO taskDTO,
                                              HttpServletRequest request) {
        // Fetch the existing task by ID
        String authToken = jwtTokenProvider.resolveToken(request);



        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }



        Optional<Task> existingTaskOptional = taskService.getTaskById(id, authToken);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Update the existing task properties
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


            return ResponseEntity.ok(updatedTaskDTO);
        } else {
            // Task with the given ID not found
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id,
                                        HttpServletRequest request) {
        String authToken = jwtTokenProvider.resolveToken(request);
        if (authToken == null) {
            // Handle the case where the token is null (e.g., unauthorized access)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        taskService.deleteTask(id, authToken);
        return ResponseEntity.ok("Task Deleted");
    }
}

package org.springboardLogin.Services;

import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Entities.Task;
import org.springboardLogin.Repositories.TaskRepository;
import org.springboardLogin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all tasks for the authenticated user
    public List<Task> getAllTasks() {
        // Get the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            // Fetch tasks for the authenticated user
            return taskRepository.findAll();
        }
        return null;
    }

    // Get a task by ID for the authenticated user
    public Optional<Task> getTaskById(String id) {
        // Check if the task belongs to the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            AppUser user = authenticatedUser.get();

            // Fetch the task by ID
            Optional<Task> taskOptional = taskRepository.findById(id);

            // Check if the task exists and belongs to the authenticated user
            return taskOptional.filter(task -> task.getUser().equals(user));
        }

        return Optional.empty();
    }

    // Create a task for the authenticated user
    public void createTask(Task task) {
        // Set the authenticated user as the owner of the task
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);
        System.out.println(username);
        if (authenticatedUser.isPresent()) {
            AppUser user = userRepository
                    .findById(authenticatedUser.get().getId()).orElse(null);
            assert user != null;
            System.out.println(user.getId());
            task.setUser(user);
            System.out.println(task.getTitle());
            List<Task> tasks;
            try {
                if (user.getTasks() != null) {
                    tasks = user.getTasks();
                    tasks.add(task);
                    System.out.println("Task Added");
                } else {
                    tasks = new ArrayList<>();
                    tasks.add(task);
                    System.out.println("Tasks Added");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                // Save the task to the database
                taskRepository.save(task);
                userRepository.save(user);
            } catch (DataAccessException e) {
                // Handle the specific exception, e.g., DuplicateKeyException or other database-related errors
                System.out.print("Database error: " + e.getMessage());
            } catch (Exception e) {
                // Handle other exceptions or print the stack trace for debugging
                System.out.print("Error: " + e.getMessage());
            }

        }
    }

    // Update a task for the authenticated user
    public Task updateTask(Task task) {
        // Check if the task belongs to the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            if (!task.getUser().getId().equals(authenticatedUser.get().getId())) {
                // Task doesn't belong to the authenticated user
                throw new IllegalStateException("Unauthorized access to task");
            }
        }

        // Fetch the existing task by ID
        Optional<Task> existingTaskOptional = taskRepository.findById(task.getId());

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Update the existing task properties
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setPriority(task.getPriority());

            // Save the updated task
            return taskRepository.save(existingTask);
        } else {
            // Task with the given ID not found
            throw new IllegalArgumentException("Task not found");
        }
    }

    // Delete a task for the authenticated user
    public void deleteTask(String id) {
        // Check if the task belongs to the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            Task taskToDelete = taskRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found"));

            if (!taskToDelete.getUser().getId().equals(authenticatedUser.get().getId())) {
                // Task doesn't belong to the authenticated user
                throw new IllegalStateException("Unauthorized access to task");
            }

            // Delete the task
            taskRepository.deleteById(id);
        }
    }
}

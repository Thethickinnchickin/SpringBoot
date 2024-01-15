package org.springboardLogin.Services;


import org.springboardLogin.Entities.AppUser;
import org.springboardLogin.Entities.Task;
import org.springboardLogin.Repositories.TaskRepository;
import org.springboardLogin.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        // Get the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

       if (authenticatedUser.isPresent()) {
            AppUser user = authenticatedUser.get();
            return taskRepository.findAll();
        }
        return null;

    }

    public Optional<Task> getTaskById(Long id) {
        // Check if the task belongs to the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            AppUser user = authenticatedUser.get();

            // Fetch the task by id
            Optional<Task> taskOptional = taskRepository.findById(id);

            // Check if the task exists and belongs to the authenticated user
            return taskOptional.filter(task -> task.getUser().equals(user));
        }

        return Optional.empty();
    }
    public void createTask(Task task) {
        // Set the authenticated user as the owner of the task
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AppUser> authenticatedUser = userRepository.findByUsername(username);

        if (authenticatedUser.isPresent()) {
            AppUser user = authenticatedUser.get();
            task.setUser(user);

            try {
                // Save the task to the database and return the saved task
                taskRepository.save(task);
            } catch (Exception e) {
                // Handle the exception or print the stack trace for debugging
                System.out.print("ERRORRRR");
                //e.printStackTrace();
                // Or throw a custom exception if needed
            }
        }


    }


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

        // Fetch the existing task by id
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
            // Task with the given id not found
            throw new IllegalArgumentException("Task not found");
        }
    }


    public void deleteTask(Long id) {
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
            taskRepository.deleteById(id);
        }

    }
}

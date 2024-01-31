package org.springboardLogin.DTOs;

import java.util.Date;

/**
 * Data Transfer Object (DTO) representing task data for external communication.
 * This class is used to transfer task information between different layers of the application.
 */
public class TaskDTO {

    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private Boolean isCompleted = false;

    // Constructors

    /**
     * Default constructor for TaskDTO.
     */
    public TaskDTO() {
        // Default constructor
    }

    /**
     * Constructor for TaskDTO with task ID, title, description, due date, priority, and completion status.
     * @param id          The task ID.
     * @param title       The task title.
     * @param description The task description.
     * @param dueDate     The task due date.
     * @param priority    The task priority.
     * @param isCompleted The task completion status.
     */
    public TaskDTO(String id, String title, String description, Date dueDate, String priority, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters

    /**
     * Get the ID of the task.
     *
     * @return The task ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID of the task.
     *
     * @param id The task ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the title of the task.
     *
     * @return The task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the task.
     *
     * @param title The task title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task.
     *
     * @param description The task description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the due date of the task.
     *
     * @return The task due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of the task.
     *
     * @param dueDate The task due date to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the priority of the task.
     *
     * @return The task priority.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Set the priority of the task.
     *
     * @param priority The task priority to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Get the completion status of the task.
     *
     * @return The task completion status.
     */
    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    /**
     * Set the completion status of the task.
     *
     * @param isCompleted The task completion status to set.
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}

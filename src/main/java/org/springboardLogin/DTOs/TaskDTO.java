package org.springboardLogin.DTOs;

import java.util.Date;

public class TaskDTO {

    private String title;
    private String description;
    private Date dueDate;
    private String priority;

    // Constructors
    public TaskDTO() {
        // Default constructor
    }

    public TaskDTO(String title, String description, Date dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and Setters

    /**
     * Get the title of the task.
     * @return The task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the task.
     * @param title The task title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the task.
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task.
     * @param description The task description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the due date of the task.
     * @return The task due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of the task.
     * @param dueDate The task due date to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the priority of the task.
     * @return The task priority.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Set the priority of the task.
     * @param priority The task priority to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }
}

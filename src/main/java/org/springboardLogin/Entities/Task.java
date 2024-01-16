package org.springboardLogin.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private String description;
    private Date dueDate;
    private String priority;


    private AppUser user;

    // Constructors

    /**
     * Default constructor for Task.
     */
    public Task() {
        // Default constructor
    }

    /**
     * Constructor for Task with title, description, due date, and priority.
     * @param title       The title of the task.
     * @param description The description of the task.
     * @param dueDate     The due date of the task.
     * @param priority    The priority of the task.
     */
    public Task(String title, String description, Date dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and Setters

    /**
     * Get the ID of the task.
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID of the task.
     * @param id The ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the title of the task.
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the task.
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the task.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task.
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the due date of the task.
     * @return The due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of the task.
     * @param dueDate The due date to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the priority of the task.
     * @return The priority.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Set the priority of the task.
     * @param priority The priority to set.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Get the user associated with the task.
     * @return The user.
     */
    public AppUser getUser() {
        return user;
    }

    /**
     * Set the user associated with the task.
     * @param user The user to set.
     */
    public void setUser(AppUser user) {
        this.user = user;
    }
}
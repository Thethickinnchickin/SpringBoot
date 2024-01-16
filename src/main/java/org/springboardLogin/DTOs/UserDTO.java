package org.springboardLogin.DTOs;

public class UserDTO {

    private String id;
    private String username;

    // Constructors
    public UserDTO() {
        // Default constructor
    }

    public UserDTO(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and Setters

    /**
     * Get the user ID.
     * @return The user ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the user ID.
     * @param id The user ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}

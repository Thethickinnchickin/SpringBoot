package org.springboardLogin.DTOs;

/**
 * Data Transfer Object (DTO) representing a response message.
 * This class is used to transfer response information between different layers of the application.
 */
public class ResponseDTO {
    private String message;

    /**
     * Constructor for ResponseDTO with a message.
     *
     * @param message The response message.
     */
    public ResponseDTO(String message) {
        this.message = message;
    }

    /**
     * Get the response message.
     *
     * @return The response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the response message.
     *
     * @param message The response message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

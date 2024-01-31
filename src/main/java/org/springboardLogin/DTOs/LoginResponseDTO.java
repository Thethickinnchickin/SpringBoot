package org.springboardLogin.DTOs;

/**
 * Data Transfer Object (DTO) representing a response for login with an authentication token.
 * This class is used to transfer login response information between different layers of the application.
 */
public class LoginResponseDTO {
    private String token;

    /**
     * Constructor for LoginResponseDTO with a token.
     *
     * @param token The authentication token.
     */
    public LoginResponseDTO(String token) {
        this.token = token;
    }

    /**
     * Get the authentication token.
     *
     * @return The authentication token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the authentication token.
     *
     * @param token The authentication token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}

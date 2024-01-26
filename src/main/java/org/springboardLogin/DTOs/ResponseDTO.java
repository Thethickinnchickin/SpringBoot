package org.springboardLogin.DTOs;

public class ResponseDTO {
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public String getToken() {
        return message;
    }

    public void setToken(String message) {
        this.message = message;
    }
}

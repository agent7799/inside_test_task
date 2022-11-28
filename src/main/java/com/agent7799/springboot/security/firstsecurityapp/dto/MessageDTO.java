package com.agent7799.springboot.security.firstsecurityapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageDTO {

    @NotEmpty
    private String message;

    @NotNull
    private String username;

    public MessageDTO(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

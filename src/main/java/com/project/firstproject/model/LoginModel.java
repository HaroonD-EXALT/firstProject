package com.project.firstproject.model;

import org.springframework.context.annotation.ComponentScan;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ComponentScan
public class LoginModel {

    @NotNull(message = "username is required, must not be null")
    @NotEmpty(message = "username is required, must not be empty")
    @NotBlank(message = "username is required, must not be blank")
    private String username;
    @NotNull(message = "password is required, must not be null")
    @NotEmpty(message = "password is required, must not be empty")
    @NotBlank(message = "password is required, must not be blank")
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}

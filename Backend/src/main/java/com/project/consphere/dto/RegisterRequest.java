package com.project.consphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String mobileNumber;
    private String email;
    private String password;
    private String confirmPassword;
}

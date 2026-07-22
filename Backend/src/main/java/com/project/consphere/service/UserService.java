package com.project.consphere.service;

import com.project.consphere.dto.RegisterRequest;
import com.project.consphere.dto.UpdateUserRequest;
import com.project.consphere.model.User;

public interface UserService {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User updateUser(UpdateUserRequest request);
    User deleteUser();
    User register(RegisterRequest request);
}

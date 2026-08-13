package com.project.consphere.service;

import com.project.consphere.dto.*;
import com.project.consphere.model.User;

public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getCurrentUserProfile();

    UserResponse updateUserProfile(UpdateUserRequest request);

    UserProfileResponse getUserProfileByUsername(String username);

    User getCurrentUser();
}

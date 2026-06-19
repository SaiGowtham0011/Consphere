package com.project.consphere.service;

import java.util.List;
import com.project.consphere.model.User;

public interface UserService {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User updateUser(String username, User user);
    User addUser(User user);
    User deleteUser(User user);
}

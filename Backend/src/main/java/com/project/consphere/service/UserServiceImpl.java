package com.project.consphere.service;

import com.project.consphere.dto.RegisterRequest;
import com.project.consphere.dto.UpdateUserRequest;
import com.project.consphere.model.User;
import com.project.consphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username) {
        User existing = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Username not found with "+username));
        return existing;
    }

    @Override
    public User findUserByEmail(String email) {
        User existing = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Username not found with "+email));
        return existing;
    }


    @Override
    public User updateUser(UpdateUserRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User existing = userRepository.findByUsername(currentUsername)
                .orElseThrow(()->new RuntimeException("Username not found with "+currentUsername));
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username is already in use");
        }

        existing.setUsername(request.getUsername());
        existing.setEmail(request.getEmail());
        existing.setMobileNumber(request.getMobileNumber());
        existing.setProfilePicURL(request.getProfilePicURL());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());

        return userRepository.save(existing);
    }

    @Override
    public User deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User existing = userRepository.findByUsername(currentUsername)
                .orElseThrow(()->new RuntimeException("Username not found with "+currentUsername));
        userRepository.delete(existing);
        return existing;
    }

    @Override
    public User register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        if(request.getPassword()==null || !request.getPassword().equals(request.getConfirmPassword())){
            throw new RuntimeException("Passwords do not match");
        }
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setMobileNumber(request.getMobileNumber());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
}

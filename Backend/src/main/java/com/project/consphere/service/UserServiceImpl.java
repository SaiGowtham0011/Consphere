package com.project.consphere.service;

import com.project.consphere.model.User;
import com.project.consphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User addUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String username, User user){
        User existing = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("Username not found with "+username));
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setMobileNumber(user.getMobileNumber());
        existing.setProfilePicURL(user.getProfilePicURL());
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());

        return userRepository.save(existing);
    }

    @Override
    public User deleteUser(User user){
        User existing = userRepository.findByUsername(user.getUsername())
                .orElseThrow(()->new RuntimeException("Username not found with "+user.getUsername()));
        userRepository.delete(existing);
        return existing;
    }

    @Override
    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

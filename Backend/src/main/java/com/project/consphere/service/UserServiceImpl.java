package com.project.consphere.service;

import com.project.consphere.config.JwtService;
import com.project.consphere.dto.*;
import com.project.consphere.exception.BadRequestException;
import com.project.consphere.exception.ResourceNotFoundException;
import com.project.consphere.model.User;
import com.project.consphere.repository.FollowRepository;
import com.project.consphere.repository.PostRepository;
import com.project.consphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @org.springframework.context.annotation.Lazy
    private PostService postService;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (request.getPassword() == null || !request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().trim())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobileNumber(request.getMobileNumber())
                .profilePicURL(null)
                .build();

        User savedUser = userRepository.save(user);

        return convertToUserResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(authentication.getName());

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .userId(user.getId())
                .profilePicURL(user.getProfilePicURL())
                .build();
    }

    @Override
    public UserResponse getCurrentUserProfile() {
        User currentUser = getCurrentUser();
        return convertToUserResponse(currentUser);
    }

    @Override
    public UserResponse updateUserProfile(UpdateUserRequest request) {
        User currentUser = getCurrentUser();

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            String newUsername = request.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(currentUser.getUsername())) {
                if (userRepository.existsByUsername(newUsername)) {
                    throw new BadRequestException("Username is already taken");
                }
                currentUser.setUsername(newUsername);
            }
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            String newEmail = request.getEmail().trim();
            if (!newEmail.equalsIgnoreCase(currentUser.getEmail())) {
                if (userRepository.existsByEmail(newEmail)) {
                    throw new BadRequestException("Email is already registered");
                }
                currentUser.setEmail(newEmail);
            }
        }

        if (request.getFirstName() != null) {
            currentUser.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            currentUser.setLastName(request.getLastName());
        }

        if (request.getMobileNumber() != null) {
            currentUser.setMobileNumber(request.getMobileNumber());
        }

        if (request.getProfilePicURL() != null) {
            currentUser.setProfilePicURL(request.getProfilePicURL());
        }

        User updatedUser = userRepository.save(currentUser);
        return convertToUserResponse(updatedUser);
    }

    @Override
    public UserProfileResponse getUserProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        User currentUser = null;
        try {
            currentUser = getCurrentUser();
        } catch (Exception e) {
            // Unauthenticated view
        }

        long followersCount = followRepository.countByFollowing(user);
        long followingCount = followRepository.countByFollower(user);
        long postsCount = postRepository.findAllByUserUsernameOrderByCreatedAtDesc(username).size();

        boolean isFollowing = false;
        if (currentUser != null && !currentUser.getId().equals(user.getId())) {
            isFollowing = followRepository.existsByFollowerAndFollowing(currentUser, user);
        }

        List<PostResponse> posts = postService.getUserPosts(username);

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicURL(user.getProfilePicURL())
                .followersCount(followersCount)
                .followingCount(followingCount)
                .postsCount(postsCount)
                .isFollowing(isFollowing)
                .posts(posts)
                .build();
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new BadRequestException("User is not authenticated");
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .profilePicURL(user.getProfilePicURL())
                .build();
    }
}

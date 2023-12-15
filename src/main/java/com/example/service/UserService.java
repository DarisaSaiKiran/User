package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Repository.UserRepository;
import com.example.model.User;
@Service
public class UserService {
	
	@Autowired
	private UserRepository repoObj;


	private static final String EMAIL_FORMAT = "[a-zA-Z0-9]+@[a-z]+\\.[a-z]{2,3}";
	private static final String PASSWORD_FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
	private static final String USERNAME_FORMAT = "^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
	private static final String DEFAULT_PROFILE_IMAGE_URL = "https://static.thenounproject.com/png/5034901-200.png";

	public String addUser(User user) {
	    if (!user.getEmail().matches(EMAIL_FORMAT)) {
	        return "Invalid email format.";
	    }

	    if (!user.getPassword().matches(PASSWORD_FORMAT)) {
	        return "Invalid password format.";
	    }

	    if (!user.getUsername().matches(USERNAME_FORMAT)) {
	        return "Invalid username format.";
	    }

	    User anotherUser = repoObj.findByUsername(user.getUsername());

	    if (anotherUser != null) {
	        return "Username already exists. Please try again.";
	    }

	    // Set default profile image URL if the user doesn't provide a URL
	    if (user.getProfileImageUrl() == null || user.getProfileImageUrl().isEmpty()) {
	        user.setProfileImageUrl(DEFAULT_PROFILE_IMAGE_URL);
	    }

	    repoObj.save(user);

	    return "User Added Successfully!!\nYour Email:\t " + user.getEmail() + "\nPassword:\t " + user.getPassword();
	}

	 public List<User> getAllUsers() {
	        try {
	            return repoObj.findAll();
	        } catch (Exception ex) {
	            // You can log the exception or handle it differently based on your application's requirements
	            throw new RuntimeException("Failed to fetch users. Please try again later.");
	        }
	    }
	 public User updateUser(int userId, User updatedUser) {
	        User existingUser = repoObj.findById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

	        existingUser.setUsername(updatedUser.getUsername());
	        existingUser.setEmail(updatedUser.getEmail());
	        existingUser.setLoggedIn(updatedUser.isLoggedIn());
	        existingUser.setProfileImageUrl(updatedUser.getProfileImageUrl());

	        return repoObj.save(existingUser);
	    }

	    public User getUserById(int userId) {
	        return repoObj.findById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
	    }
	    public User getUserByEmail(String email) {
	        return repoObj.findByEmail(email);
	    }
	    public User updateUsers(User user) {
	        return repoObj.save(user);
	      }
}

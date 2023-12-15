package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.UserRepository;
import com.example.model.User;
import com.example.service.UserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository u;
	
	@PostMapping("/register")
	public String addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@ResponseBody
	@PostMapping("/login")
	public Integer loginUser(@Valid @RequestBody User user) {
		List<User> users = u.findAll();
		for (User other : users) {
			if (other.equals(user)) {
				user.setLoggedIn(true);
				System.out.println("Successful login");
//				u.save(user);
				return(other.getId());
			}
		}

		return 0;
	}
	 @GetMapping("/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }
	 @PutMapping("/{userId}")
	    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
	        User user = userService.updateUser(userId, updatedUser);
	        return ResponseEntity.ok(user);
	    }

	    @GetMapping("/{userId}")
	    public ResponseEntity<User> getUserById(@PathVariable int userId) {
	        User user = userService.getUserById(userId);
	        return ResponseEntity.ok(user);
	    }
	    
	    @GetMapping("/email/{email}")
	    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
	        User user = userService.getUserByEmail(email);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    @PutMapping("/updateByEmail/{email}")
	    public ResponseEntity<User> updateUserByEmail(@PathVariable String email, @RequestBody User updatedUser) {
	      User user = userService.getUserByEmail(email);
	      if (user != null) {
	        user.setUsername(updatedUser.getUsername());
	        user.setPassword(updatedUser.getPassword());
	        user.setProfileImageUrl(updatedUser.getProfileImageUrl());
	        	        
	        User updatedqUser = userService.updateUsers(user);
	        return ResponseEntity.ok(updatedqUser);
	      } else {
	        return ResponseEntity.notFound().build();
	      }
	    }
}

package com.breudes.gastrohub.controller;

import com.breudes.gastrohub.dto.UserDTO;
import com.breudes.gastrohub.model.User;
import com.breudes.gastrohub.repository.UserRepository;
import com.breudes.gastrohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO user) {
        try{
            // Check if the email already exists in the database
            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
            if (existingUserByEmail.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Email is already registered.");
            }
            // Check if the username (login) already exists in the database
            Optional<User> existingUserByUsername = userRepository.findByEmail(user.getUsername());
            if (existingUserByUsername.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Username is already registered.");
            }
            // Save the new user
            User newUser = new User(user);
            User savedUser = userRepository.save(newUser);

            // Return response message based on the request status
            if (savedUser.getId() != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("User created successfully.");
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: invalid data.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error while creating the user.");
        }
        return null;
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get user by email
    @GetMapping("/find-by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get user by username (login)
    @GetMapping("/find-by-username")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/id")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.updateUser(id, userDTO);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return userService.changePassword(id, oldPassword, newPassword);
    }
}

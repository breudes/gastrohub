package com.breudes.gastrohub.service;

import com.breudes.gastrohub.dto.UserDTO;
import com.breudes.gastrohub.model.User;
import com.breudes.gastrohub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository usuarioRepository, BCryptPasswordEncoder criptEncoder) {
        this.userRepository = usuarioRepository;
        this.passwordEncoder = criptEncoder;
    }

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(newUser);
    }

    public ResponseEntity<String> updateUser(Long id, UserDTO updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        // Password is not updated here
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setUserRole(updatedUser.getUserRole());
            user.setAddress(updatedUser.getAddress());
            user.setActive(true);
            user.setLastUpdateDate(new Date());
            userRepository.save(user);
            return ResponseEntity.status(200).body("User successfully updated.");
        }else{
            return ResponseEntity.status(404).body("User not found.");
        }
    }

    public User deactivateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            if (user.get().isActive()) {
                user.get().setActive(false);
                return userRepository.save(user.get());
            } else {
                throw new IllegalStateException("User is already inactive.");
            }
        }
        return null;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by ID."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found by E-mail."));
    }

    public ResponseEntity<String> changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastUpdateDate(new Date());
        User updatedUser = userRepository.save(user);
        if(updatedUser.isActive()){
            return ResponseEntity.status(200).body("Password successfully updated.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }

    public ResponseEntity<String> deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setActive(false);
            userRepository.save(foundUser);
            if(!foundUser.isActive()){
                return ResponseEntity.status(200).body("User successfully deleted.");
            }else{
                return ResponseEntity.status(400).body("Could not delete the user.");
            }
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}

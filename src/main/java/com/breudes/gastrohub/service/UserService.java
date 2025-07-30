package com.breudes.gastrohub.service;

import com.breudes.gastrohub.dto.UserDTO;
import com.breudes.gastrohub.model.User;
import com.breudes.gastrohub.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder criptEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = criptEncoder;
    }

    public Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = (Optional<User>) auth.getPrincipal();
        return user.map(User::getId).orElse(null);
    }

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        newUser.setLastUpdateDate(new Date());
        newUser.setActive(true);
        return userRepository.save(newUser);
    }

    public ResponseEntity<String> updateUser(Long id, UserDTO updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        // Password is not updated here
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
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

    public ResponseEntity<String> changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            if(!foundUser.isActive()){
                return ResponseEntity.status(400).body("User not active.");
            }
            if (!passwordEncoder.matches(oldPassword, foundUser.getPassword())) {
                throw new IllegalArgumentException("Old password is incorrect.");
            }
            foundUser.setPassword(passwordEncoder.encode(newPassword));
            foundUser.setLastUpdateDate(new Date());
            userRepository.save(foundUser);
            return ResponseEntity.status(200).body("Password successfully updated.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }

    public ResponseEntity<String> deleteUser(Long id){
        Long loggedUserId = getAuthenticatedUserId();
        if(Objects.equals(loggedUserId, id)){
            return ResponseEntity.status(403).body("You cannot delete yourself.");
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setActive(false);
            userRepository.save(foundUser);
            if(!foundUser.isActive()){
                return ResponseEntity.status(200).body("User successfully deleted.");
            }else{
                return ResponseEntity.status(400).body("User already deleted.");
            }
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}

package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    // Read (전체 조회)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Read (단일 조회)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Update
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        
        return userRepository.save(user);
    }
    
    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


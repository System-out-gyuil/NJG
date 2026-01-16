package com.example.demo.userRef.service;

import com.example.demo.food.entity.Food;
import com.example.demo.food.repository.FoodRepository;
import com.example.demo.userRef.entity.UserRef;
import com.example.demo.userRef.repository.UserRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRefService {
    
    @Autowired
    private UserRefRepository userRefRepository;
    
    @Autowired
    private FoodRepository foodRepository;
    
    // Create
    public UserRef createUserRef(UserRef userRef) {
        return userRefRepository.save(userRef);
    }
    
    // Read (전체 조회)
    public List<UserRef> getAllUserRefs() {
        return userRefRepository.findAll();
    }
    
    // Read (유저별 조회)
    public List<UserRef> getUserRefsByUserId(Long userId) {
        return userRefRepository.findByUserId(userId);
    }
    
    // Read (단일 조회)
    public Optional<UserRef> getUserRefById(Long id) {
        return userRefRepository.findById(id);
    }
    
    // Update
    public UserRef updateUserRef(Long id, UserRef userRefDetails) {
        UserRef userRef = userRefRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRef not found with id: " + id));
        
        userRef.setQuantity(userRefDetails.getQuantity());
        userRef.setUnit(userRefDetails.getUnit());
        userRef.setExp_date(userRefDetails.getExp_date());
        
        if (userRefDetails.getFood() != null && userRefDetails.getFood().getId() != null) {
            Food food = foodRepository.findById(userRefDetails.getFood().getId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));
            userRef.setFood(food);
        }
        
        return userRefRepository.save(userRef);
    }
    
    // Delete
    public void deleteUserRef(Long id) {
        userRefRepository.deleteById(id);
    }
}


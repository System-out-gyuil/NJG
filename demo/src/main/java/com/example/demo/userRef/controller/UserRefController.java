package com.example.demo.userRef.controller;

import com.example.demo.food.entity.Food;
import com.example.demo.food.repository.FoodRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.userRef.entity.UserRef;
import com.example.demo.userRef.service.UserRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/userRefs")
public class UserRefController {
    
    @Autowired
    private UserRefService userRefService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FoodRepository foodRepository;
    
    // Create
    @PostMapping
    public ResponseEntity<UserRef> createUserRef(@RequestBody Map<String, Object> request) {
        Long userId = Long.parseLong(request.get("userId").toString());
        Long foodId = Long.parseLong(request.get("foodId").toString());
        Integer quantity = Integer.parseInt(request.get("quantity").toString());
        String unit = request.get("unit").toString();
        String expDate = request.get("expDate").toString();
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        
        UserRef userRef = new UserRef();
        userRef.setUser(user);
        userRef.setFood(food);
        userRef.setQuantity(quantity);
        userRef.setUnit(unit);
        userRef.setExp_date(expDate);
        
        UserRef createdUserRef = userRefService.createUserRef(userRef);
        return new ResponseEntity<>(createdUserRef, HttpStatus.CREATED);
    }
    
    // Read (전체 조회)
    @GetMapping
    public ResponseEntity<List<UserRef>> getAllUserRefs() {
        List<UserRef> userRefs = userRefService.getAllUserRefs();
        return new ResponseEntity<>(userRefs, HttpStatus.OK);
    }
    
    // Read (유저별 조회)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRef>> getUserRefsByUserId(@PathVariable Long userId) {
        List<UserRef> userRefs = userRefService.getUserRefsByUserId(userId);
        return new ResponseEntity<>(userRefs, HttpStatus.OK);
    }
    
    // Read (단일 조회)
    @GetMapping("/{id}")
    public ResponseEntity<UserRef> getUserRefById(@PathVariable Long id) {
        Optional<UserRef> userRef = userRefService.getUserRefById(id);
        if (userRef.isPresent()) {
            return new ResponseEntity<>(userRef.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<UserRef> updateUserRef(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        try {
            UserRef userRef = userRefService.getUserRefById(id)
                    .orElseThrow(() -> new RuntimeException("UserRef not found"));
            
            if (request.containsKey("quantity")) {
                userRef.setQuantity(Integer.parseInt(request.get("quantity").toString()));
            }
            if (request.containsKey("unit")) {
                userRef.setUnit(request.get("unit").toString());
            }
            if (request.containsKey("expDate")) {
                userRef.setExp_date(request.get("expDate").toString());
            }
            if (request.containsKey("foodId")) {
                Long foodId = Long.parseLong(request.get("foodId").toString());
                Food food = foodRepository.findById(foodId)
                        .orElseThrow(() -> new RuntimeException("Food not found"));
                userRef.setFood(food);
            }
            
            UserRef updatedUserRef = userRefService.updateUserRef(id, userRef);
            return new ResponseEntity<>(updatedUserRef, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRef(@PathVariable Long id) {
        userRefService.deleteUserRef(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


package com.example.demo.food.service;

import com.example.demo.food.entity.Food;
import com.example.demo.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    // Create
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }
    
    // Read (전체 조회)
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
    
    // Read (타입별 조회)
    public List<Food> getFoodsByType(String foodType) {
        return foodRepository.findByFoodType(foodType);
    }
    
    // Read (단일 조회)
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }
    
    // Update
    public Food updateFood(Long id, Food foodDetails) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        
        food.setFoodName(foodDetails.getFoodName());
        food.setFoodType(foodDetails.getFoodType());
        
        return foodRepository.save(food);
    }
    
    // Delete
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}


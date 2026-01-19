package com.example.demo.food.controller;

import com.example.demo.file.service.FileUploadService;
import com.example.demo.food.entity.Food;
import com.example.demo.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private FileUploadService fileUploadService;
    
    // Create
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        Food createdFood = foodService.createFood(food);
        return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
    }
    
    // Read (전체 조회)
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    
    // Read (타입별 조회)
    @GetMapping("/type/{foodType}")
    public ResponseEntity<List<Food>> getFoodsByType(@PathVariable String foodType) {
        List<Food> foods = foodService.getFoodsByType(foodType);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
    
    // Read (단일 조회)
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Optional<Food> food = foodService.getFoodById(id);
        if (food.isPresent()) {
            return new ResponseEntity<>(food.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food foodDetails) {
        try {
            Food updatedFood = foodService.updateFood(id, foodDetails);
            return new ResponseEntity<>(updatedFood, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        // 이미지 파일도 함께 삭제
        Optional<Food> food = foodService.getFoodById(id);
        food.ifPresent(f -> {
            if (f.getFoodImageUrl() != null) {
                fileUploadService.deleteFile(f.getFoodImageUrl());
            }
        });
        
        foodService.deleteFood(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // 이미지 업로드
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = fileUploadService.uploadFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "파일 업로드에 실패했습니다: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}


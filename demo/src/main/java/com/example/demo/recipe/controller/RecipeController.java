package com.example.demo.recipe.controller;

import com.example.demo.recipe.entity.Recipe;
import com.example.demo.recipe.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {
    
    private final RecipeService recipeService;
    
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    
    /**
     * 초기 데이터 로딩 (API에서 DB로)
     * @param count 가져올 레시피 개수 (기본값: 1000)
     * @return 저장된 레시피 개수
     */
    @PostMapping("/load")
    public ResponseEntity<Map<String, Object>> loadRecipes(
            @RequestParam(defaultValue = "1000") int count) {
        try {
            long currentCount = recipeService.getRecipeCount();
            
            if (currentCount >= count) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "이미 충분한 레시피가 저장되어 있습니다.");
                response.put("currentCount", currentCount);
                response.put("requestedCount", count);
                return ResponseEntity.ok(response);
            }
            
            int savedCount = recipeService.loadRecipesFromApi(count);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "레시피 데이터 로딩 완료");
            response.put("savedCount", savedCount);
            response.put("totalCount", recipeService.getRecipeCount());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "레시피 로딩 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * 레시피 목록 조회 (유저 냉장고 재료 기반 정렬 지원)
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지 크기 (기본값: 20)
     * @param userId 유저 ID (선택, 제공시 냉장고 재료 기반 정렬)
     * @return 레시피 목록
     */
    @GetMapping
    public ResponseEntity<?> getRecipeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long userId) {
        try {
            // DB에 데이터가 있는지 확인
            long count = recipeService.getRecipeCount();
            if (count == 0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "레시피 데이터가 없습니다. POST /api/recipes/load 를 먼저 호출하세요.");
                errorResponse.put("message", "초기 데이터 로딩이 필요합니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // userId가 제공되면 유저의 냉장고 재료 기반으로 정렬
            if (userId != null) {
                List<Recipe> recipes = recipeService.getRecipeListByUserIngredients(userId, page, size);
                return ResponseEntity.ok(recipes);
            } else {
                Page<Recipe> recipes = recipeService.getRecipeList(page, size);
                return ResponseEntity.ok(recipes.getContent());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * 레시피 상세 조회
     * @param rcpSeq 레시피 일련번호
     * @return 레시피 상세 정보
     */
    @GetMapping("/{rcpSeq}")
    public ResponseEntity<Recipe> getRecipeDetail(@PathVariable String rcpSeq) {
        try {
            Recipe recipe = recipeService.getRecipeDetail(rcpSeq);
            return ResponseEntity.ok(recipe);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 레시피 검색
     * @param name 레시피 이름
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지 크기 (기본값: 20)
     * @return 검색된 레시피 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<Recipe> recipes = recipeService.searchRecipeByName(name, page, size);
            return ResponseEntity.ok(recipes.getContent());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * DB에 저장된 레시피 개수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getRecipeCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("count", recipeService.getRecipeCount());
        return ResponseEntity.ok(response);
    }
}

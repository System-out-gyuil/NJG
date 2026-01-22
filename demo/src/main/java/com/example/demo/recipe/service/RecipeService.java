package com.example.demo.recipe.service;

import com.example.demo.recipe.dto.ApiResponse;
import com.example.demo.recipe.dto.RecipeDetailResponse;
import com.example.demo.recipe.dto.RecipeListResponse;
import com.example.demo.recipe.entity.Recipe;
import com.example.demo.recipe.repository.RecipeRepository;
import com.example.demo.userRef.entity.UserRef;
import com.example.demo.userRef.service.UserRefService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RecipeRepository recipeRepository;
    
    @Autowired
    private UserRefService userRefService;
    
    @Value("${recipe.api.key:sample_key}")
    private String apiKey;
    
    private static final String BASE_URL = "http://openapi.foodsafetykorea.go.kr/api";
    private static final String SERVICE_NAME = "COOKRCP01";
    
    public RecipeService(RestTemplate restTemplate, ObjectMapper objectMapper, RecipeRepository recipeRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.recipeRepository = recipeRepository;
    }
    
    // ==================== API에서 데이터 가져오기 ====================
    
    /**
     * API에서 레시피 데이터를 가져와서 DB에 저장
     * @param count 가져올 레시피 개수
     * @return 저장된 레시피 개수
     */
    @Transactional
    public int loadRecipesFromApi(int count) {
        System.out.println("API에서 " + count + "개의 레시피 데이터를 가져오는 중...");
        
        int savedCount = 0;
        int batchSize = 100; // 한 번에 가져올 개수
        
        for (int start = 1; start <= count; start += batchSize) {
            int end = Math.min(start + batchSize - 1, count);
            
            try {
                List<RecipeDetailResponse> recipes = fetchRecipesFromApi(start, end);
                
                for (RecipeDetailResponse apiRecipe : recipes) {
                    // 이미 존재하는 레시피인지 확인
                    if (!recipeRepository.existsByRcpSeq(apiRecipe.getRcpSeq())) {
                        Recipe recipe = convertToEntity(apiRecipe);
                        recipeRepository.save(recipe);
                        savedCount++;
                    }
                }
                
                System.out.println(start + " ~ " + end + " 완료 (현재까지 저장: " + savedCount + "개)");
                
                // API 과부하 방지를 위한 짧은 대기
                Thread.sleep(500);
                
            } catch (Exception e) {
                System.err.println("레시피 로드 중 오류 발생 (" + start + "~" + end + "): " + e.getMessage());
            }
        }
        
        System.out.println("총 " + savedCount + "개의 레시피가 저장되었습니다.");
        return savedCount;
    }
    
    /**
     * API에서 레시피 상세 정보 조회
     */
    private List<RecipeDetailResponse> fetchRecipesFromApi(int startIdx, int endIdx) {
        String url = String.format("%s/%s/%s/json/%d/%d", 
            BASE_URL, apiKey, SERVICE_NAME, startIdx, endIdx);
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            ApiResponse<RecipeDetailResponse> apiResponse = objectMapper.readValue(
                response, 
                new TypeReference<ApiResponse<RecipeDetailResponse>>() {}
            );
            
            if (apiResponse != null && apiResponse.getCookRcpData() != null) {
                return apiResponse.getCookRcpData().getRow();
            }
            
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("API 레시피 조회 실패: " + e.getMessage(), e);
        }
    }
    
    /**
     * API 응답 DTO를 Entity로 변환
     */
    private Recipe convertToEntity(RecipeDetailResponse dto) {
        Recipe recipe = new Recipe();
        
        recipe.setRcpSeq(dto.getRcpSeq());
        recipe.setRcpNm(dto.getRcpNm());
        recipe.setRcpWay2(dto.getRcpWay2());
        recipe.setRcpPat2(dto.getRcpPat2());
        recipe.setInfoWgt(dto.getInfoWgt());
        recipe.setInfoEng(dto.getInfoEng());
        recipe.setInfoCar(dto.getInfoCar());
        recipe.setInfoPro(dto.getInfoPro());
        recipe.setInfoFat(dto.getInfoFat());
        recipe.setInfoNa(dto.getInfoNa());
        recipe.setHashTag(dto.getHashTag());
        recipe.setAttFileNoMain(dto.getAttFileNoMain());
        recipe.setAttFileNoMk(dto.getAttFileNoMk());
        recipe.setRcpPartsDtls(dto.getRcpPartsDtls());
        
        // 조리 순서 20개
        recipe.setManual01(dto.getManual01());
        recipe.setManualImg01(dto.getManualImg01());
        recipe.setManual02(dto.getManual02());
        recipe.setManualImg02(dto.getManualImg02());
        recipe.setManual03(dto.getManual03());
        recipe.setManualImg03(dto.getManualImg03());
        recipe.setManual04(dto.getManual04());
        recipe.setManualImg04(dto.getManualImg04());
        recipe.setManual05(dto.getManual05());
        recipe.setManualImg05(dto.getManualImg05());
        recipe.setManual06(dto.getManual06());
        recipe.setManualImg06(dto.getManualImg06());
        recipe.setManual07(dto.getManual07());
        recipe.setManualImg07(dto.getManualImg07());
        recipe.setManual08(dto.getManual08());
        recipe.setManualImg08(dto.getManualImg08());
        recipe.setManual09(dto.getManual09());
        recipe.setManualImg09(dto.getManualImg09());
        recipe.setManual10(dto.getManual10());
        recipe.setManualImg10(dto.getManualImg10());
        recipe.setManual11(dto.getManual11());
        recipe.setManualImg11(dto.getManualImg11());
        recipe.setManual12(dto.getManual12());
        recipe.setManualImg12(dto.getManualImg12());
        recipe.setManual13(dto.getManual13());
        recipe.setManualImg13(dto.getManualImg13());
        recipe.setManual14(dto.getManual14());
        recipe.setManualImg14(dto.getManualImg14());
        recipe.setManual15(dto.getManual15());
        recipe.setManualImg15(dto.getManualImg15());
        recipe.setManual16(dto.getManual16());
        recipe.setManualImg16(dto.getManualImg16());
        recipe.setManual17(dto.getManual17());
        recipe.setManualImg17(dto.getManualImg17());
        recipe.setManual18(dto.getManual18());
        recipe.setManualImg18(dto.getManualImg18());
        recipe.setManual19(dto.getManual19());
        recipe.setManualImg19(dto.getManualImg19());
        recipe.setManual20(dto.getManual20());
        recipe.setManualImg20(dto.getManualImg20());
        
        recipe.setRcpNaTip(dto.getRcpNaTip());
        
        return recipe;
    }
    
    // ==================== DB에서 데이터 조회 ====================
    
    /**
     * 레시피 목록 조회 (페이징)
     */
    public Page<Recipe> getRecipeList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recipeRepository.findAll(pageable);
    }
    
    /**
     * 유저 맞춤 레시피 목록 조회 (냉장고 재료 기반 정렬)
     * 전체 DB에서 재료가 일치하는 레시피만 필터링하여 반환
     */
    public List<Recipe> getRecipeListByUserIngredients(Long userId, int page, int size) {
        if (userId == null) {
            // userId가 없으면 일반 페이징 조회
            Pageable pageable = PageRequest.of(page - 1, size);
            return recipeRepository.findAll(pageable).getContent();
        }
        
        try {
            // 유저의 냉장고 재료 목록 가져오기
            List<UserRef> userRefs = userRefService.getUserRefsByUserId(userId);
            Set<String> userIngredients = userRefs.stream()
                .map(userRef -> userRef.getFood().getFoodName().toLowerCase().trim())
                .collect(Collectors.toSet());
            
            if (userIngredients.isEmpty()) {
                // 냉장고가 비어있으면 빈 리스트 반환
                System.out.println("냉장고가 비어있습니다. 빈 레시피 목록 반환");
                return List.of();
            }
            
            System.out.println("=== 사용자 재료 기반 레시피 검색 ===");
            System.out.println("사용자 재료: " + userIngredients);
            
            // 전체 레시피 조회
            List<Recipe> allRecipes = recipeRepository.findAllRecipes();
            System.out.println("전체 레시피 수: " + allRecipes.size());
            
            // 재료가 하나라도 일치하는 레시피만 필터링하고 점수 계산
            List<RecipeWithScore> recipesWithScores = allRecipes.stream()
                .map(recipe -> {
                    int score = calculateIngredientMatchScore(recipe.getRcpPartsDtls(), userIngredients);
                    return new RecipeWithScore(recipe, score);
                })
                .filter(rws -> rws.score > 0)  // 점수가 0보다 큰 것만 (재료가 하나라도 일치)
                .sorted((r1, r2) -> Integer.compare(r2.score, r1.score))  // 점수 내림차순 정렬
                .collect(Collectors.toList());
            
            System.out.println("재료 일치 레시피 수: " + recipesWithScores.size());
            
            // 페이징 적용
            int start = (page - 1) * size;
            int end = Math.min(start + size, recipesWithScores.size());
            
            if (start >= recipesWithScores.size()) {
                return List.of();
            }
            
            List<Recipe> result = recipesWithScores.subList(start, end).stream()
                .map(rws -> rws.recipe)
                .collect(Collectors.toList());
            
            System.out.println("페이지 " + page + " 반환 레시피 수: " + result.size());
            System.out.println("================================");
            
            return result;
                
        } catch (Exception e) {
            System.err.println("재료 매칭 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            // 에러 발생 시 빈 리스트 반환
            return List.of();
        }
    }
    
    /**
     * 레시피와 점수를 함께 저장하는 내부 클래스
     */
    private static class RecipeWithScore {
        Recipe recipe;
        int score;
        
        RecipeWithScore(Recipe recipe, int score) {
            this.recipe = recipe;
            this.score = score;
        }
    }
    
    /**
     * 레시피 상세 조회 (rcpSeq로)
     */
    public Recipe getRecipeDetail(String rcpSeq) {
        return recipeRepository.findByRcpSeq(rcpSeq)
            .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다: " + rcpSeq));
    }
    
    /**
     * 레시피 검색 (이름으로)
     */
    public Page<Recipe> searchRecipeByName(String recipeName, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recipeRepository.findByRcpNmContaining(recipeName, pageable);
    }
    
    /**
     * 레시피 총 개수 조회
     */
    public long getRecipeCount() {
        return recipeRepository.count();
    }
    
    // ==================== 재료 매칭 로직 ====================
    
    /**
     * 레시피 재료와 유저 냉장고 재료의 매칭 점수 계산
     */
    private int calculateIngredientMatchScore(String recipeIngredients, Set<String> userIngredients) {
        if (recipeIngredients == null || recipeIngredients.isEmpty()) {
            return 0;
        }
        
        String ingredientsLower = recipeIngredients.toLowerCase().trim();
        int matchCount = 0;
        
        for (String ingredient : userIngredients) {
            if (ingredientsLower.contains(ingredient)) {
                matchCount++;
            }
        }
        
        return matchCount;
    }
}

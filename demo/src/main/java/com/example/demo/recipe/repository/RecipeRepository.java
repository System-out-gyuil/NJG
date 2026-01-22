package com.example.demo.recipe.repository;

import com.example.demo.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    // rcpSeq로 조회
    Optional<Recipe> findByRcpSeq(String rcpSeq);
    
    // 레시피 이름으로 검색
    Page<Recipe> findByRcpNmContaining(String rcpNm, Pageable pageable);
    
    // 재료로 검색
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.rcpPartsDtls) LIKE LOWER(CONCAT('%', :ingredient, '%'))")
    Page<Recipe> findByIngredient(@Param("ingredient") String ingredient, Pageable pageable);
    
    // 여러 재료 중 하나라도 포함된 레시피 조회
    @Query("SELECT DISTINCT r FROM Recipe r WHERE " +
           "LOWER(r.rcpPartsDtls) LIKE LOWER(CONCAT('%', :ingredient1, '%')) " +
           "OR LOWER(r.rcpPartsDtls) LIKE LOWER(CONCAT('%', :ingredient2, '%'))")
    List<Recipe> findByIngredientsContaining(
        @Param("ingredient1") String ingredient1,
        @Param("ingredient2") String ingredient2
    );
    
    // 전체 레시피 조회 (재료 필터링 없음)
    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAllRecipes();
    
    // rcpSeq 존재 여부 확인
    boolean existsByRcpSeq(String rcpSeq);
}

